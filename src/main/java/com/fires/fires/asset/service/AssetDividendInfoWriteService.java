package com.fires.fires.asset.service;

import com.fires.fires.asset.entity.AssetDividendInfo;

import java.util.Collection;

public interface AssetDividendInfoWriteService {
    void saveAllAssetDividendInfo(Collection<AssetDividendInfo> assetDividendInfos);
}
