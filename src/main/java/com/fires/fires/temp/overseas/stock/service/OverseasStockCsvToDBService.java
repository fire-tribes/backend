package com.fires.fires.temp.overseas.stock.service;

import com.fires.fires.temp.overseas.stock.dto.NasdaqStockDto;
import com.fires.fires.temp.overseas.stock.entity.OverseasStock;
import com.fires.fires.temp.overseas.stock.repository.OverseasStockRepository;
import com.fires.fires.asset.util.csv.CsvToEntity;
import com.fires.fires.asset.util.csv.CsvUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OverseasStockCsvToDBService implements CsvToEntity {
    private final OverseasStockRepository stockRepository;


    @Override
    public void csvToEntity() {
        saveUsaStockInfo("csv/nasdaq_stock_info.csv");
        saveUsaStockInfo("csv/nyse_stock_info.csv");
        saveUsaStockInfo("csv/amex_stock_info.csv");


    }

    private void saveUsaStockInfo(String fileName) {
        List<NasdaqStockDto> nasdaqStockDtos = csvToDto(fileName);
        List<OverseasStock> stockEntities = nasdaqStockDtos.stream()
                                                           .filter(dto -> StringUtils.hasLength(dto.getIndustry()))
                                                           .map(dto -> OverseasStock.builder()
                                                                                    .name(dto.getName())
                                                                                    .sector(dto.getSector())
                                                                                    .symbol(dto.getSymbol())
                                                                                    .industry(dto.getIndustry())
                                                                                    .country(dto.getCountry())
                                                                                    .build())
                                                           .toList();
        stockRepository.saveAll(stockEntities);
    }

    public List<NasdaqStockDto> csvToDto(String fileName) {
        return CsvUtil.csvToObject(fileName, NasdaqStockDto.class);
    }

}
