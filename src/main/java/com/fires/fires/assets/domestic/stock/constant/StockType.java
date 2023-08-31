package com.fires.fires.assets.domestic.stock.constant;
/**
 * @deprecated (MarketType 재정의)
 */
@Deprecated(since = "1")
public enum StockType {
    COMMON_STOCK, NEW_PREFERRED_STOCK, OLD_PREFERRED_STOCK, PREFERRED_STOCK;

    public static StockType convertFromCSV(String stockType) {
        if (stockType.equals("보통주")) {
            return StockType.COMMON_STOCK;
        } else if (stockType.equals("구형우선주")) {
            return StockType.OLD_PREFERRED_STOCK;
        } else if (stockType.equals("신형우선주")) {
            return StockType.NEW_PREFERRED_STOCK;
        } else if (stockType.equals("종류주권")) {
            return StockType.PREFERRED_STOCK;
        } else {
            throw new IllegalArgumentException();
        }
    }
}
