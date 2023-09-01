package com.fires.fires.asset.service;

import com.fires.fires.asset.entity.Asset;
import com.fires.fires.asset.repository.AssetRepository;
import com.fires.fires.asset.contant.MarketType;
import com.fires.fires.common.constant.ResponseCode;
import com.fires.fires.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssetWriteService {
    private final AssetRepository assetRepository;

    /**
     *
     * @param assetId 업데이트하고자 하는 자산 id
     * @param marketType 업데이트하고자 하는 marketType
     * @throws ResourceNotFoundException 해당 자산이 없을 경우
     */
    public void updateAssetMarketType(Long assetId, MarketType marketType) {
        Asset asset = assetRepository.findById(assetId).orElseThrow(() -> new ResourceNotFoundException(ResponseCode.RESOURCE_NOT_FOUND));
        asset.updateMarketType(marketType);
    }

}
