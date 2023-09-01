package com.fires.fires.temp.overseas.stock.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@Table(name = "overseas_stocks")
@Entity
public class OverseasStock {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "overseas_stock_id")
    private Long id;
    private String symbol;
    private String name;
    private String sector;
    private String industry;
    private String country;

    @Builder
    private OverseasStock(String symbol, String name, String sector, String industry, String country) {
        this.symbol = symbol;
        this.name = name;
        this.sector = sector;
        this.industry = industry;
        this.country = country;
    }
}
