package com.fires.fires.assets.overseas.etf.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class NasdaqEtfDto {
    @CsvBindByName(column = "SYMBOL")
    String symbol;
    @CsvBindByName(column = "NAME")
    String name;
}
