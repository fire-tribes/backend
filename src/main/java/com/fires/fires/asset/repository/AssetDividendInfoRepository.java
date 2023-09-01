package com.fires.fires.assets.asset.repository;

import com.fires.fires.assets.asset.entity.Asset;
import com.fires.fires.assets.asset.entity.AssetDividendInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AssetDividendInfoRepository extends JpaRepository<AssetDividendInfo, Long> {
    /**
     *
     * @param assets 검색하고자 하는 자산 리스트
     * @param lastYear 작년 오늘 날짜
     * @param today 오늘 날짜
     * @return 작년부터 오늘까지의 배당정보 포함 자산 리스트
     */
    List<AssetDividendInfo> findByAssetInAndPayDateGreaterThanEqualAndPayDateLessThan(List<Asset> assets, LocalDate lastYear, LocalDate today);
}
