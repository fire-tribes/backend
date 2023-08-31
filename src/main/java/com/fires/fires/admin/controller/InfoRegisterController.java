package com.fires.fires.admin.controller;

import com.fires.fires.assets.asset.service.CsvToAssetDBService;
import com.fires.fires.assets.domestic.etf.service.DomesticEtfCsvToDBService;
import com.fires.fires.assets.domestic.stock.service.DomesticStockCsvToDBService;
import com.fires.fires.assets.overseas.etf.service.OverseasEtfCsvToDBService;
import com.fires.fires.assets.overseas.stock.service.OverseasStockCsvToDBService;
import com.fires.fires.external.api.kis.KisClient;
import com.fires.fires.external.api.kis.dto.TokenRequest;
import com.fires.fires.external.api.kis.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class InfoRegisterController {
    private final OverseasEtfCsvToDBService etfService;
    private final OverseasStockCsvToDBService stockService;
    private final CsvToAssetDBService csvToAssetDB;
    private final DomesticEtfCsvToDBService domesticEtfCsvToDBService;
    private final DomesticStockCsvToDBService domesticStockCsvToDBService;
    private final KisClient koreaInvestmentClient;

    @Value("${external.api.korea-invest.access-key}")
    private String accessKey;
    @Value("${external.api.korea-invest.secret-key}")
    private String secretKey;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @PostMapping("/overseas-stock-info")
    public String stockSave() {
        stockService.csvToEntity();
        return "ok";
    }

    @PostMapping("/overseas-etf-info")
    public String etfSave() {
        etfService.csvToEntity();
        return "ok";
    }

    @PostMapping("/asset-info")
    public String assetSave() {
        csvToAssetDB.csvToEntity();
        return "ok";
    }

    @PostMapping("/domestic-etf-info")
    public String domesticEtfSave() {
        domesticEtfCsvToDBService.csvToEntity();
        return "ok";
    }

    @PostMapping("/domestic-stock-info")
    public String domesticStockSave() {
        domesticStockCsvToDBService.csvToEntity();
        return "ok";
    }

    @GetMapping("/get-token")
    public TokenResponse getToken() {
        TokenRequest clientCredentials = new TokenRequest("client_credentials", accessKey, secretKey);
        return koreaInvestmentClient.getAccessToken(clientCredentials);
    }
}
