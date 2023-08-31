package com.fires.fires.assets.domestic.stock.constant;

/**
 * @deprecated (MarketType 재정의)
 */
@Deprecated(since = "1")
public enum MarketType {
    KOSPI, KOSDAQ, KONEX, KOSDAQ_GLOBAL;

    public static MarketType convertFromCSV(String marketType) {
        if (marketType.equals("KOSDAQ GLOBAL")) {
            return MarketType.KOSDAQ_GLOBAL;
        }else{
            return MarketType.valueOf(marketType);
        }

    }
}
