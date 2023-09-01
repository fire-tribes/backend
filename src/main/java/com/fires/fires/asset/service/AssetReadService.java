package com.fires.fires.asset.service;

import com.fires.fires.asset.dto.AssetDto;
import com.fires.fires.asset.entity.Asset;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface AssetReadService {
    Slice<AssetDto> getAssetsByNameOrSymbol(String query, Pageable pageable);
    List<Asset> getAssetsInAssetIds(List<Long> assetIds);
}
