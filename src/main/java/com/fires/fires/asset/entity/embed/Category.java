package com.fires.fires.assets.asset.entity.embed;

import com.fires.fires.assets.contant.AssetCategory;
import com.fires.fires.assets.contant.Country;
import com.fires.fires.assets.contant.MarketType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 자산을 분류하기 위한 Embed 클래스
 */
@Embeddable
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
@Getter
public class Category {
    //국가
    @Enumerated(EnumType.STRING)
    private Country country;
    /*
        거래소 : KRX, KRX_KOSPI, KRX_KOSDAQ, KRX_KONEX, NYSE, AMEX, NASDAQ, UNKNOWN
        미국 etf의 경우 거래소별로 분류된 정보가 없어서 초기에는 UNKNOWN으로 지정된다.
        이후 한국투자증권 API검색 과정에서 거래소가 식별되면 업데이트 된다.
        한국 ETF의 경우 KRX로 설정된다.
     */
    @Enumerated(EnumType.STRING)
    private MarketType marketType;
    //자산의 유형
    @Enumerated(EnumType.STRING)
    private AssetCategory assetCategory;

    public Category(Country country, MarketType marketType, AssetCategory assetCategory) {
        this.country = country;
        this.marketType = marketType;
        this.assetCategory = assetCategory;
    }

    public void updateMarketType(MarketType marketType) {
        this.marketType = marketType;
    }
}
