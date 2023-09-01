package com.fires.fires.search.controller.dto;

import com.fires.fires.portfolio.constant.Currency;

import java.time.LocalDateTime;

public record CurrentPriceResponse(Long assetId,
                                   String symbol,
                                   String currentPrice,
                                   Currency currency,
                                   LocalDateTime accessTime) {
}
