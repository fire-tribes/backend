package com.fires.fires.assets.overseas.stock.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class NasdaqStockDto {
    @CsvBindByName(column = "Symbol")
    String symbol;
    @CsvBindByName(column = "Name")
    String name;
    @CsvBindByName(column = "Country")
    String country;
    @CsvBindByName(column = "Sector")
    String sector;
    @CsvBindByName(column = "Industry")
    String industry;
}
