package com.fires.fires.temp.domestic.etf.service;

import com.fires.fires.temp.domestic.etf.dto.KrxEtfDto;
import com.fires.fires.temp.domestic.etf.entity.DomesticEtf;
import com.fires.fires.temp.domestic.etf.repository.DomesticEtfRepository;
import com.fires.fires.asset.util.csv.CsvToEntity;
import com.fires.fires.asset.util.csv.CsvUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DomesticEtfCsvToDBService implements CsvToEntity {

    private final DomesticEtfRepository etfRepository;
    @Override
    public void csvToEntity() {
        List<KrxEtfDto> krxEtfDtos = csvToDto();
        List<DomesticEtf> etfs = krxEtfDtos.stream()
                                           .map(dto -> DomesticEtf.builder()
                                                              .name(dto.getName())
                                                              .englishName(dto.getEnglishName())
                                                              .standardCode(dto.getStandardCode())
                                                              .shortCode(dto.getShortCode())
                                                              .shortName(dto.getShortName())
                                                              .issuer(dto.getIssuer())
                                                              .build()
                                                      ).toList();
        etfRepository.saveAll(etfs);

    }

    public List<KrxEtfDto> csvToDto() {
        return CsvUtil.csvToObject("csv/domestic_etf_info.csv", KrxEtfDto.class);
    }
}
