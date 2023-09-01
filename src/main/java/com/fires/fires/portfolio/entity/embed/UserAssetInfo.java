package com.fires.fires.portfolio.entity.embed;

import com.fires.fires.asset.entity.Asset;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
@AllArgsConstructor(staticName = "of")
@Getter
public class UserAssetInfo {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "asset_id")
    private Asset asset;
    private Long assetCount;
    private String assetPrice;


    public void updateAssetCountAndPrice(Long assetCount, String assetPrice) {
        this.assetCount = assetCount;
        this.assetPrice = assetPrice;
    }
}
