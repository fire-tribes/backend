package com.fires.fires.temp.domestic.stock.repository;

import com.fires.fires.temp.domestic.stock.entity.DomesticStock;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @deprecated (MarketType 재정의)
 */
@Deprecated(since = "1")
public interface DomesticStockRepository extends JpaRepository<DomesticStock, Long> {
}
