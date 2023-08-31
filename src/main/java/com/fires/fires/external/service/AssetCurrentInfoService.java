package com.fires.fires.external.service;

import com.fires.fires.assets.asset.dto.AssetDto;

import java.util.List;

public interface AssetCurrentInfoService {
    public List<AssetPriceDto> getAssetsCurrentPrice(List<AssetDto> assetDtos);
}
