package com.fires.fires.temp.domestic.etf.dto;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;
import lombok.ToString;

/**
 * CSV -> BEAN으로 전환하기 위한 dto
 * OpenCsv가 record를 지원하지 않음
 * @see <a href="http://data.krx.co.kr/contents/MDC/MDI/mdiLoader/index.cmd?menuId=MDC0201020101">krx 증권상품 -  ETF - 전종목 기본정보</a>
 */
@Getter
@ToString
public class KrxEtfDto {

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
    @CsvBindByPosition(position = 13) //@CsvBindByName(column = "운용사")
    private String issuer;

}
