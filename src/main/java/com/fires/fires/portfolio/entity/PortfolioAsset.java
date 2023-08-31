package com.fires.fires.portfolio.entity;

import com.fires.fires.common.entity.BaseTimeEntity;
import com.fires.fires.portfolio.entity.embed.UserAssetInfo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "portfolio_assets")
@Entity
public class PortfolioAsset extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_asset_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;
    @Embedded
    private UserAssetInfo userAssetInfo;

    private PortfolioAsset(Portfolio portfolio, UserAssetInfo userAssetInfo) {
        this.portfolio = portfolio;
        this.userAssetInfo = userAssetInfo;
    }

    public static PortfolioAsset of(Portfolio portfolio, UserAssetInfo userAssetInfo) {
        return new PortfolioAsset(portfolio, userAssetInfo);
    }
}
