package com.fires.fires.search.controller.usecase;

import com.fires.fires.assets.asset.dto.AssetDto;
import com.fires.fires.assets.asset.service.AssetReadService;
import com.fires.fires.external.service.AssetCurrentInfoService;
import com.fires.fires.external.service.AssetPriceDto;
import com.fires.fires.search.controller.dto.CurrentPriceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SearchAssetCurrentPriceBySymbol {
    private final AssetReadService assetReadService;
    private final AssetCurrentInfoService assetCurrentInfoService;
    public List<CurrentPriceResponse> execute(List<Long> assetIds) {
        List<AssetDto> assetDtos = assetReadService.getAssetsInAssetIds(assetIds).stream().map(AssetDto::from).toList();
        LocalDateTime accessTime = LocalDateTime.now();
        List<AssetPriceDto> assetsCurrentPrices = assetCurrentInfoService.getAssetsCurrentPrice(assetDtos);
        return assetsCurrentPrices.stream().map(dto -> new CurrentPriceResponse(dto.assetId(), dto.symbol(), dto.price(), dto.currency(), accessTime)).toList();
    }
}
