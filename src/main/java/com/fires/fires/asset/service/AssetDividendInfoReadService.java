package com.fires.fires.asset.service;

import com.fires.fires.asset.entity.Asset;
import com.fires.fires.asset.entity.AssetDividendInfo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface AssetDividendInfoReadService {
    Map<Long, List<AssetDividendInfo>> getAnnualAssetDividendInfoFromToday(LocalDate today, List<Asset> assets);
}
