package com.fires.fires.asset.util.csv;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public final class CsvUtil{

    private CsvUtil() {
        throw new IllegalArgumentException("Utility class");
    }

    /**
     * OpenCsv라이브러리를 이용해 csv파일을 객체로 변환해준다.
     * @param fileName db에 저장하고자 하는 정보가 포함된 csv 파일명
     * @param clazz 변환하고자 하는 Dto클래스
     * @return 변환된 dto 객체 리스트
     * @param <T> 변환하고자하는 Dto 클래스
     */
    public static <T> List<T> csvToObject(String fileName, Class<T> clazz) {
        List<T> dtos = new ArrayList<>();

        try (InputStream inputStream = new ClassPathResource(fileName).getInputStream();
             CSVReader reader = new CSVReader(new InputStreamReader(inputStream, Charset.forName("EUC-KR")))) {

            dtos = new CsvToBeanBuilder<T>(reader)
                    //.withSkipLines(1)
                    .withType(clazz)
                    .build()
                    .parse();
        } catch (IOException e) {
            log.error("csv read error", e);
        }
        return dtos;
    }
}
