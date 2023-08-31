package com.fires.fires.assets.domestic.stock.repository;

import com.fires.fires.assets.domestic.stock.entity.DomesticStock;
import org.springframework.data.jpa.repository.JpaRepository;

@Deprecated
public interface DomesticStockRepository extends JpaRepository<DomesticStock, Long> {
}
