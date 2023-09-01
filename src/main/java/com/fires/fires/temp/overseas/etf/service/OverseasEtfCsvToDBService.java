package com.fires.fires.temp.overseas.etf.service;

import com.fires.fires.temp.overseas.etf.dto.NasdaqEtfDto;
import com.fires.fires.temp.overseas.etf.entity.OverseasEtf;
import com.fires.fires.temp.overseas.etf.repository.OverseasEtfRepository;
import com.fires.fires.asset.util.csv.CsvToEntity;
import com.fires.fires.asset.util.csv.CsvUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OverseasEtfCsvToDBService implements CsvToEntity {

    private final OverseasEtfRepository etfRepository;
    @Override
    public void csvToEntity() {
        List<NasdaqEtfDto> nasdaqEtfDtos = csvToDto();
        List<OverseasEtf> etfs = nasdaqEtfDtos.stream()
                                              .map(dto -> OverseasEtf.of(dto.getSymbol(), dto.getName())).toList();
        etfRepository.saveAll(etfs);

    }

    public List<NasdaqEtfDto> csvToDto() {
        return CsvUtil.csvToObject("csv/overseas_etf_info.csv", NasdaqEtfDto.class);
    }
}
