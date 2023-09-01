package com.fires.fires.external.api.kis.constant;

import com.fires.fires.asset.contant.MarketType;

public enum ExchangeCode {
    NAS, NYS, AMS;

    public static MarketType convertToMarketType(ExchangeCode exchangeCode) {
        return switch (exchangeCode){
            case NAS -> MarketType.NASDAQ;
            case NYS -> MarketType.NYSE;
            case AMS -> MarketType.AMEX;
        };

    }
}
