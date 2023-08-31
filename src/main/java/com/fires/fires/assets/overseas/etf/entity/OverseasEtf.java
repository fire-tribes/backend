package com.fires.fires.assets.overseas.etf.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@Table(name = "overseas_etfs")
@Entity
public class OverseasEtf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "overseas_etf_id")
    private Long id;

    private String symbol;
    private String name;


    private OverseasEtf(String symbol, String name) {
        this.symbol = symbol;
        this.name = name;
    }

    public static OverseasEtf of(String symbol, String name) {
        return new OverseasEtf(symbol, name);
    }
}
