package com.fires.fires.external.service;

import com.fires.fires.assets.asset.dto.AssetDto;
import com.fires.fires.common.constant.Currency;
import com.fires.fires.external.api.kis.dto.DomesticPriceResponse;
import com.fires.fires.external.api.kis.dto.OverseasPriceResponse;

public record AssetPriceDto(Long assetId,
                            String symbol,
                            String price,
                            String priceChange,
                            String priceChangeCode,
                            Currency currency) {

    public static AssetPriceDto fromKoreaInvest(AssetDto assetDto, DomesticPriceResponse response) {

        return new AssetPriceDto(assetDto.assetId(),
                                    assetDto.symbol(),
                                    response.output().price(),
                                    response.output().priceChange(),
                                    response.output().priceChange(),
                                    Currency.KRW);
    }

    public static AssetPriceDto fromKoreaInvest(AssetDto assetDto, OverseasPriceResponse response) {

        return new AssetPriceDto(assetDto.assetId(),
                                    assetDto.symbol(),
                                    response.output().price(),
                                    response.output().priceChange(),
                                    response.output().priceChange(),
                                    Currency.USD);
    }

    public static AssetPriceDto priceError(AssetDto assetDto) {
        return new AssetPriceDto(assetDto.assetId(),
                assetDto.symbol(),
                "0",
                "0",
                "0",
                Currency.KRW);
    }
}
