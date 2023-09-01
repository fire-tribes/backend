package com.fires.fires.portfolio.dto;

import com.fires.fires.asset.entity.Asset;

public record AssetInfoDto(Asset asset,
                           Long assetNumber,
                           String assetPrice) {
}
