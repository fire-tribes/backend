package com.fires.fires.portfolio.dto;

import com.fires.fires.assets.asset.entity.Asset;

public record AssetInfoDto(Asset asset,
                           Long assetNumber,
                           String assetPrice) {
}
