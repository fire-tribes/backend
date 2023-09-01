package com.fires.fires.asset.service;

import com.fires.fires.asset.contant.MarketType;

public interface AssetWriteService {
    void updateAssetMarketType(Long assetId, MarketType marketType);
}
