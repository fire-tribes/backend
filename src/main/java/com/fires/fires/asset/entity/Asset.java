package com.fires.fires.assets.asset.entity;

import com.fires.fires.assets.asset.entity.embed.Category;
import com.fires.fires.assets.contant.MarketType;
import com.fires.fires.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * 애플리케이션 전반에서 자산을 식별하기 위해 사용되는 entity
 * @author galmegiz
 */
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@Table(name = "assets")
@Entity
public class Asset extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "asset_id")
    private Long id;
    private String symbol;
    private String name;
    @Embedded
    private Category category;

    private Asset(String symbol, String name, Category category) {
        this.symbol = symbol;
        this.name = name;
        this.category = category;
    }

    public static Asset of(String symbol, String name, Category category) {
        return new Asset(symbol, name, category);
    }

    public void updateMarketType(MarketType marketType) {
        this.category.updateMarketType(marketType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Asset asset = (Asset) o;
        return Objects.equals(id, asset.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
