package com.fires.fires.asset.service;

import com.fires.fires.asset.dto.AssetDto;
import com.fires.fires.asset.entity.Asset;
import com.fires.fires.asset.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class AssetReadService {
    private final AssetRepository assetRepository;

    /**
     * 사용자 입력 쿼리를 이용해 자산을 검색한다. 사용자 입력을 이용해 name과 symbol column을 or조건으로 검색한다.
     * @param query 사용자가 입력한 검색 쿼리
     * @param pageable 페이징처리를 위한 pageable객체
     * @return 페이징 정보가 포함된 자산 리스트
     */
    public Slice<AssetDto> getAssetsByNameOrSymbol(String query, Pageable pageable) {
       return assetRepository.findBySymbolContainsIgnoreCaseOrNameContainsIgnoreCase(query, query, pageable).map(AssetDto::from);
    }

    /**
     *
     * @param assetIds 찾고자 하는 자산 Id 리스트
     * @return id리스트에 포함된 자산 정보 반환
     */
    public List<Asset> getAssetsInAssetIds(List<Long> assetIds) {
        return assetRepository.findByIdIn(assetIds);
    }
}
