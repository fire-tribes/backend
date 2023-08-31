package com.fires.fires.assets.domestic.etf.entity;

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
@Table(name = "domestic_etfs")

public class DomesticEtf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "domestic_etf_id")
    private Long id;
    private String standardCode;
    private String shortCode;
    private String name;
    private String shortName;
    private String englishName;
    private String issuer;

    @Builder
    private DomesticEtf(String standardCode, String shortCode, String name, String shortName, String englishName, String issuer) {
        this.standardCode = standardCode;
        this.shortCode = shortCode;
        this.name = name;
        this.shortName = shortName;
        this.englishName = englishName;
        this.issuer = issuer;
    }
}
