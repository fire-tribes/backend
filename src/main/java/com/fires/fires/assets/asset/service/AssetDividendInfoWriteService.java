package com.fires.fires.assets.asset.service;

import com.fires.fires.assets.asset.entity.AssetDividendInfo;
import com.fires.fires.assets.asset.repository.AssetDividendInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@RequiredArgsConstructor
@Transactional
@Service
public class AssetDividendInfoWriteService {

    private final AssetDividendInfoRepository assetDividendInfoRepository;

    /**
     * 배당률 포함 자산 정보 리스트 저장
     * @param assetDividendInfos 배당률 포함 자산 정보 리스트
     */
    //todo: bulk insert 효율화하도록 리팩토링 필요
    public void saveAllAssetDividendInfo(Collection<AssetDividendInfo> assetDividendInfos) {
        assetDividendInfoRepository.saveAll(assetDividendInfos);
    }
}
