package com.fires.fires.temp.domestic.etf.repository;

import com.fires.fires.temp.domestic.etf.entity.DomesticEtf;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * @deprecated (MarketType 재정의)
 */
@Deprecated(since = "1")
public interface DomesticEtfRepository extends JpaRepository<DomesticEtf, Long> {
}
