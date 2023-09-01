package com.fires.fires.asset.entity;

import com.fires.fires.asset.contant.Frequency;
import com.fires.fires.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 자산의 배당 정보를 관리하는 entity
 * @author galmegiz
 */
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@Table(name = "asset_info")
@Entity
public class AssetDividendInfo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "asset_dividend_info_id")
    private Long id;
    //배당주기
    @Enumerated(EnumType.STRING)
    private Frequency frequency;
    //배당금
    private String dividend;
    //배당락일
    private LocalDate exDividendDate;
    //배당일
    private LocalDate payDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id")
    private Asset asset;

    @Builder
    private AssetDividendInfo(Frequency frequency, String dividend, LocalDate exDividendDate, LocalDate payDate, Asset asset) {
        this.frequency = frequency;
        this.dividend = dividend;
        this.exDividendDate = exDividendDate;
        this.payDate = payDate;
        this.asset = asset;
    }
}
