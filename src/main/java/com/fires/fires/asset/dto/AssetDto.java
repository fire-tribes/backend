package com.fires.fires.assets.asset.dto;


import com.fires.fires.assets.asset.entity.Asset;
import com.fires.fires.assets.asset.entity.embed.Category;

public record AssetDto(Long assetId,
                       String symbol,
                       String name,
                       Category category) {

    public static AssetDto from(Asset asset) {
        return new AssetDto(asset.getId(), asset.getSymbol(), asset.getName(), asset.getCategory());
    }
}
