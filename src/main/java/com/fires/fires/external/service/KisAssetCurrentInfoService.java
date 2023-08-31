package com.fires.fires.external.service;

import com.fires.fires.assets.asset.dto.AssetDto;
import com.fires.fires.assets.asset.entity.embed.Category;
import com.fires.fires.assets.asset.service.AssetWriteService;
import com.fires.fires.assets.contant.Country;
import com.fires.fires.common.exception.KisException;
import com.fires.fires.external.api.kis.KisClient;
import com.fires.fires.external.api.kis.constant.ExchangeCode;
import com.fires.fires.external.api.kis.constant.MarketDivCode;
import com.fires.fires.external.api.kis.dto.OverseasPriceResponse;
import com.fires.fires.external.api.kis.dto.TokenRequest;
import com.fires.fires.external.api.kis.dto.TokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class KisAssetCurrentInfoService implements AssetCurrentInfoService{

    private final KisClient kisClient;
    //한국 투자증권 접근토큰 헤더
    private static final String HEADER_KEY_AUTHORIZATION = "authorization";
    //한국 투자증권 앱키 헤더
    private static final String HEADER_KEY_APP_KEY = "appkey";
    //한국 투자증권 앱시크릿키 헤더
    private static final String HEADER_KEY_SECRET_KEY = "appsecret";
    //한국 투자증권 거래 ID(고정값)
    private static final String HEADER_KEY_TR_ID = "tr_id";
    private final String accessKey;
    private final String secretKey;
    private final AssetWriteService assetWriteService;

    public KisAssetCurrentInfoService(AssetWriteService assetWriteService,
                                      KisClient kisClient,
                                      @Value("${external.api.korea-invest.access-key}")
                                      String accessKey,
                                      @Value("${external.api.korea-invest.secret-key}")
                                      String secretKey) {
        this.assetWriteService = assetWriteService;
        this.kisClient = kisClient;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public List<AssetPriceDto> getAssetsCurrentPrice(List<AssetDto> assetDtoList) {
        return assetDtoList.stream().map(this::getAssetCurrentPriceByExternalApi).toList();
    }

    //todo: 별도 클래스로 빼도 될 듯
    private AssetPriceDto getAssetCurrentPriceByExternalApi(AssetDto assetDto) {
        Category category = assetDto.category();

        try {
            return switch (category.getCountry()) {
                case KOR -> getKoreaAssetPrice(assetDto);
                case USA ->getUsaAssetPrice(assetDto);
            };
        } catch (KisException e) {
            log.error("price get error!");
            return AssetPriceDto.priceError(assetDto);
        }
    }

    private AssetPriceDto getKoreaAssetPrice(AssetDto assetDto) {
        Category category = assetDto.category();
        Map<String, String> headers = createHeaders(category);

        return switch (category.getAssetCategory()){
            case ETF -> AssetPriceDto.fromKoreaInvest(assetDto, kisClient.getDomesticAssetCurrentPrice(headers, MarketDivCode.ETF, assetDto.symbol()));
            case STOCK -> AssetPriceDto.fromKoreaInvest(assetDto, kisClient.getDomesticAssetCurrentPrice(headers, MarketDivCode.J, assetDto.symbol()));
            case ETN -> AssetPriceDto.fromKoreaInvest(assetDto, kisClient.getDomesticAssetCurrentPrice(headers, MarketDivCode.ETN, assetDto.symbol()));
        };
    }

    private AssetPriceDto getUsaAssetPrice(AssetDto assetDto) {
        Category category = assetDto.category();
        Map<String, String> headers = createHeaders(category);
        return switch (category.getMarketType()){
            case AMEX -> AssetPriceDto.fromKoreaInvest(assetDto, kisClient.getOverseasAssetCurrentPrice(headers, "", ExchangeCode.AMS, assetDto.symbol()));
            case NASDAQ -> AssetPriceDto.fromKoreaInvest(assetDto, kisClient.getOverseasAssetCurrentPrice(headers, "", ExchangeCode.NAS, assetDto.symbol()));
            case NYSE -> AssetPriceDto.fromKoreaInvest(assetDto, kisClient.getOverseasAssetCurrentPrice(headers, "", ExchangeCode.NYS, assetDto.symbol()));
            case UNKNOWN -> getUsaAssetPriceAndUpdateMarketInfo(assetDto, headers);
            case KRX, KRX_KONEX, KRX_KOSDAQ , KRX_KOSPI -> throw new IllegalArgumentException();
        };
    }

    private AssetPriceDto getUsaAssetPriceAndUpdateMarketInfo(AssetDto assetDto, Map<String, String> headers) {
        for (ExchangeCode value : ExchangeCode.values()) {
            OverseasPriceResponse currentPrice = kisClient.getOverseasAssetCurrentPrice(headers, "", value, assetDto.symbol());
            if (StringUtils.hasText(currentPrice.output().price())) {
                assetWriteService.updateAssetMarketType(assetDto.assetId(), ExchangeCode.convertToMarketType(value));
                return AssetPriceDto.fromKoreaInvest(assetDto, currentPrice);
            }
        }
        //todo : api 관련 exeption으로 변경
        throw new KisException(HttpStatus.INTERNAL_SERVER_ERROR, new IllegalArgumentException());
    }

    private Map<String, String> createHeaders(Category category) {
        TokenResponse accessToken = getAccessToken();

        Map<String, String> headers = new HashMap<>();
        headers.put(HEADER_KEY_AUTHORIZATION, "Bearer " + accessToken.accessToken());
        headers.put(HEADER_KEY_APP_KEY, accessKey);
        headers.put(HEADER_KEY_SECRET_KEY, secretKey);

        if(category.getCountry() == Country.KOR){
            headers.put(HEADER_KEY_TR_ID, "FHKST01010100");
        }else{
            headers.put(HEADER_KEY_TR_ID, "HHDFS00000300");
        }
        return headers;
    }

    private TokenResponse getAccessToken() {
        TokenRequest clientCredentials = new TokenRequest(accessKey, secretKey);
        return kisClient.getAccessToken(clientCredentials);
    }


}
