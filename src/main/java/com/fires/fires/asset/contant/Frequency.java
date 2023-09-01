package com.fires.fires.assets.contant;

/**
 * Polygon api에서 얻어온 배당주기
 * @See <a href="https://polygon.io/docs/stocks/get_v3_reference_dividends">관련 api 문서</a>
 */
public enum Frequency {
    ONE_TIME(0), ANNUALLY(1), BI_ANNUALLY(2), QUARTERLY(4), MONTHLY(12);
    final int number;

    Frequency(int number) {
        this.number = number;
    }
}
