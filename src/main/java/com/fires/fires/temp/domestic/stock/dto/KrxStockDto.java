package com.fires.fires.temp.domestic.stock.dto;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;
import lombok.ToString;

/**
 * CSV -> BEAN으로 전환하기 위한 dto
 * OpenCsv가 record를 지원하지 않음
 * @See <a href="http://data.krx.co.kr/contents/MDC/MDI/mdiLoader/index.cmd?menuId=MDC0201020101">krx 주식 - 종목정보 - 전종목 기본정보</a>
 */
@Getter
@ToString
public class KrxStockDto {
    @CsvBindByPosition(position = 0) //@CsvBindByName(column = "표준코드")
    private String standardCode;
    @CsvBindByPosition(position = 1) //@CsvBindByName(column = "단축코드")
    private String shortCode;
    @CsvBindByPosition(position = 2) //@CsvBindByName(column = "한글종목명")
    private String name;
    @CsvBindByPosition(position = 3) //@CsvBindByName(column = "한글종목약명")
    private String shortName;
    @CsvBindByPosition(position = 4) //@CsvBindByName(column = "영문종목명")
    private String englishName;
    @CsvBindByPosition(position = 6) //@CsvBindByName(column = "시장구분") ex) KOSDAQ, KOSPI
    private String marketType;
    @CsvBindByPosition(position = 9) //@CsvBindByName(column = "주식종류") ex) 보통주, 우선주 등
    private String stockType;
}
