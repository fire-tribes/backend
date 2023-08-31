package com.fires.fires.common.util.csv;

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

    @Deprecated
    public static List<NasdaqStockInfoDto> csvToObjectWithRecord() {
        List<NasdaqStockInfoDto> dtos = new ArrayList<>();
        try (FileReader reader = new FileReader(new ClassPathResource("csv/stock_info.csv").getFile(), Charset.forName("EUC-KR"))) {
            dtos = new CsvToBeanBuilder<NasdaqStockInfoDto>(reader)
                    //.withSkipLines(1)
                    .withType(NasdaqStockInfoDto.class)
                    .build()
                    .parse();
        } catch (IOException e) {
            log.error("csv read error", e);
        }

        return dtos;
    }
    @Deprecated
    public record NasdaqStockInfoDto(
            @CsvBindByName(column = "Symbol")
            String symbol,
            @CsvBindByName(column = "Name")
            String name,
            @CsvBindByName(column = "Symbol")
            String country,
            @CsvBindByName(column = "Country")
            String sector,
            @CsvBindByName(column = "Industry")
            String industry) {
    }
}
