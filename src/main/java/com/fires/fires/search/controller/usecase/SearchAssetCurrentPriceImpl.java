package com.fires.fires.search.controller.usecase;

import com.fires.fires.asset.dto.AssetDto;
import com.fires.fires.asset.service.AssetReadService;
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
public class SearchAssetCurrentPriceImpl implements SearchAssetCurrentPrice{
    private final AssetReadService assetReadService;
    private final AssetCurrentInfoService assetCurrentInfoService;
    public List<CurrentPriceResponse> execute(List<Long> assetIds) {
        List<AssetDto> assetDtos = assetReadService.getAssetsInAssetIds(assetIds).stream().map(AssetDto::from).toList();
        LocalDateTime accessTime = LocalDateTime.now();
        //외부 api로부터 자산의 현재가를 얻어옴
        List<AssetPriceDto> assetsCurrentPrices = assetCurrentInfoService.getAssetsCurrentPrice(assetDtos);

        //자산 현재가를 응답객체로 변환
        return assetsCurrentPrices.stream()
                                  .map(dto -> createNewDto(accessTime, dto)).toList();
    }

    private static CurrentPriceResponse createNewDto(LocalDateTime accessTime, AssetPriceDto dto) {
        return new CurrentPriceResponse(dto.assetId(),
                                        dto.symbol(),
                                        dto.price(),
                                        dto.currency(),
                                        accessTime);
    }
}
