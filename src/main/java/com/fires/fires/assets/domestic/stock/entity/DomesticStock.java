package com.fires.fires.assets.domestic.stock.entity;

import com.fires.fires.assets.domestic.stock.constant.MarketType;
import com.fires.fires.assets.domestic.stock.constant.StockType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
/**
 * @deprecated (MarketType 재정의)
 */
@Deprecated(since = "1")
@Entity
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@Table(name = "domestic_stocks")
public class DomesticStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "domestic_stock_id")
    private Long id;
    private String standardCode;
    private String shortCode;
    private String name;
    private String shortName;
    private String englishName;
    @Enumerated(EnumType.STRING)
    private MarketType marketType;
    @Enumerated(EnumType.STRING)
    private StockType stockType;


    @Builder
    private DomesticStock(String standardCode, String shortCode, String name, String shortName, String englishName, MarketType marketType, StockType stockType) {
        this.standardCode = standardCode;
        this.shortCode = shortCode;
        this.name = name;
        this.shortName = shortName;
        this.englishName = englishName;
        this.marketType = marketType;
        this.stockType = stockType;
    }
}
