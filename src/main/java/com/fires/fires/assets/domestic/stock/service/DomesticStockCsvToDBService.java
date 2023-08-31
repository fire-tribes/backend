package com.fires.fires.assets.domestic.stock.service;

import com.fires.fires.assets.domestic.stock.constant.MarketType;
import com.fires.fires.assets.domestic.stock.constant.StockType;
import com.fires.fires.assets.domestic.stock.dto.KrxStockDto;
import com.fires.fires.assets.domestic.stock.entity.DomesticStock;
import com.fires.fires.assets.domestic.stock.repository.DomesticStockRepository;
import com.fires.fires.common.util.csv.CsvToEntity;
import com.fires.fires.common.util.csv.CsvUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DomesticStockCsvToDBService implements CsvToEntity {

    private final DomesticStockRepository stockRepository;
    @Override
    public void csvToEntity() {
        List<KrxStockDto> krxStockDtos = csvToDto();
        List<DomesticStock> stocks = krxStockDtos.stream()
                                              .map(dto -> DomesticStock.builder()
                                                              .name(dto.getName())
                                                              .standardCode(dto.getStandardCode())
                                                              .shortCode(dto.getShortCode())
                                                              .shortName(dto.getShortName())
                                                              .englishName(dto.getEnglishName())
                                                              .stockType(StockType.convertFromCSV(dto.getStockType()))
                                                              .marketType(MarketType.convertFromCSV(dto.getMarketType()))
                                                              .build()
                                                      ).toList();
        stockRepository.saveAll(stocks);

    }

    public List<KrxStockDto> csvToDto() {
        return CsvUtil.csvToObject("csv/domestic_stock_info.csv", KrxStockDto.class);
    }




}
