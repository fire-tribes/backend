package com.fires.fires.asset.service;

import com.fires.fires.asset.entity.Asset;
import com.fires.fires.asset.entity.AssetDividendInfo;
import com.fires.fires.asset.repository.AssetDividendInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AssetDividendInfoReadServiceImpl implements AssetDividendInfoReadService{

    private final AssetDividendInfoRepository assetDividendInfoRepository;

    /**
     *
     * @param today 검색일
     * @param assets 검색하고자 하는 자산 리스트
     * @return 오늘을 기준으로 작년부터 현재까지의 연간 배당 정보 Map<자산ID, List<배당정보가 포함된 자산정보>> 형태로 반환한다.
     */
    public Map<Long, List<AssetDividendInfo>> getAnnualAssetDividendInfoFromToday(LocalDate today, List<Asset> assets) {
        return assetDividendInfoRepository.findByAssetInAndPayDateGreaterThanEqualAndPayDateLessThan(assets, today.minusYears(1L), today)
                                          .stream()
                                          .collect(Collectors.groupingBy(getAssetId()));
    }

    private static Function<AssetDividendInfo, Long> getAssetId() {
        return assetDividendInfo -> assetDividendInfo.getAsset().getId();
    }
}
