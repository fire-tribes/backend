package com.fires.fires.asset.repository;

import com.fires.fires.asset.entity.Asset;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    /**
     *
     * @param symbol 자산 symbol(티커, 코드)로 찾기 위한 파라미터
     * @param code 자산 이름으로 찾기 위한 파라미터
     * @param pageable 페이징처리를 위한 조건, 프로젝트에서는 size=30으로 고정
     * @return 페이징 처리를 위해 slice 객체 반환
     */
    Slice<Asset> findBySymbolContainsIgnoreCaseOrNameContainsIgnoreCase(String symbol, String code, Pageable pageable);

    /**
     *
     * @param assetIds 검색하고자 하는 자산 id(애플리케이션에서 부여한 id)
     * @return 자산 리스트 반환
     */
    List<Asset> findByIdIn(List<Long> assetIds);
}
