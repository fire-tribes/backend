package com.fires.fires.assets.asset.service;

import com.fires.fires.assets.asset.entity.Asset;
import com.fires.fires.assets.asset.entity.embed.Category;
import com.fires.fires.assets.asset.repository.AssetRepository;
import com.fires.fires.assets.contant.AssetCategory;
import com.fires.fires.assets.contant.Country;
import com.fires.fires.assets.contant.MarketType;
import com.fires.fires.assets.domestic.etf.dto.KrxEtfDto;
import com.fires.fires.assets.domestic.etf.service.DomesticEtfCsvToDBService;
import com.fires.fires.assets.domestic.stock.dto.KrxStockDto;
import com.fires.fires.assets.domestic.stock.service.DomesticStockCsvToDBService;
import com.fires.fires.assets.overseas.etf.dto.NasdaqEtfDto;
import com.fires.fires.assets.overseas.etf.service.OverseasEtfCsvToDBService;
import com.fires.fires.assets.overseas.stock.dto.NasdaqStockDto;
import com.fires.fires.assets.overseas.stock.service.OverseasStockCsvToDBService;
import com.fires.fires.common.util.csv.CsvToEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 현재 확보된 CSV파일을 db에 저장하기 위한 유틸성 클래스
 * 현재 최초 정보 세팅 후에는 사용될 일 없음
 * 리팩토링 필요
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class CsvToAssetDBService implements CsvToEntity {
    private final AssetRepository assetRepository;
    private final OverseasStockCsvToDBService overseasStockCsvToDBService;
    private final OverseasEtfCsvToDBService overseasEtfCsvToDBService;
    private final DomesticEtfCsvToDBService domesticEtfCsvToDBService;
    private final DomesticStockCsvToDBService domesticStockCsvToDBService;
    @Override
    public void csvToEntity() {
        Category nasdaqStock = new Category(Country.USA, MarketType.NASDAQ, AssetCategory.STOCK);
        saveUsaStockInfo("csv/nasdaq_stock_info.csv", nasdaqStock);

        Category nyseStock = new Category(Country.USA, MarketType.NYSE, AssetCategory.STOCK);
        saveUsaStockInfo("csv/nyse_stock_info.csv", nasdaqStock);

        Category amexStock = new Category(Country.USA, MarketType.AMEX, AssetCategory.STOCK);
        saveUsaStockInfo("csv/amex_stock_info.csv", nasdaqStock);


        Category usaETF = new Category(Country.USA, MarketType.UNKNOWN, AssetCategory.ETF);
        List<NasdaqEtfDto> usaEtfDtos = overseasEtfCsvToDBService.csvToDto();
        List<Asset> etfAssets = usaEtfDtos.stream().map(etf -> Asset.of(etf.getSymbol(), etf.getName(), usaETF)).toList();
        assetRepository.saveAll(etfAssets);


        List<KrxStockDto> krxStockDtos = domesticStockCsvToDBService.csvToDto();
        List<Asset> domesticStockAssets = krxStockDtos.stream().map(stock -> {
                    Category category;
                    if (stock.getMarketType().equals("KOSPI")) {
                        category = new Category(Country.KOR, MarketType.KRX_KOSPI, AssetCategory.STOCK);
                    } else if (stock.getMarketType().equals("KOSDAQ")) {
                        category = new Category(Country.KOR, MarketType.KRX_KOSDAQ, AssetCategory.STOCK);
                    } else if (stock.getMarketType().equals("KOSDAQ GLOBAL")) {
                        category = new Category(Country.KOR, MarketType.KRX_KOSDAQ, AssetCategory.STOCK);
                    }else if (stock.getMarketType().equals("KONEX")) {
                        category = new Category(Country.KOR, MarketType.KRX_KONEX, AssetCategory.STOCK);
                    }else{
                        throw new IllegalArgumentException();
                    }
                    return Asset.of(stock.getShortCode(), stock.getShortName(), category);
                }).toList();

        assetRepository.saveAll(domesticStockAssets);

        Category koreaEtf = new Category(Country.KOR, MarketType.KRX, AssetCategory.ETF);

        List<KrxEtfDto> krxEtfDtos = domesticEtfCsvToDBService.csvToDto();
        List<Asset> domesticEtfAssets = krxEtfDtos.stream().map(etf -> Asset.of(etf.getShortCode(), etf.getShortCode(), koreaEtf)).toList();
        assetRepository.saveAll(domesticEtfAssets);


    }

    private void saveUsaStockInfo(String fileName, Category category) {
        List<NasdaqStockDto> nasdaqStockDtos = overseasStockCsvToDBService.csvToDto("csv/nasdaq_stock_info.csv");

        List<Asset> stockAssets = nasdaqStockDtos.stream()
                                                 .filter(stock -> StringUtils.hasLength(stock.getIndustry()))
                                                 .map(stock -> Asset.of(stock.getSymbol(), stock.getName(), category)).toList();
        assetRepository.saveAll(stockAssets);
    }
}
