package com.fires.fires.search.controller.usecase;

import com.fires.fires.search.controller.dto.CurrentPriceResponse;

import java.util.List;

public interface SearchAssetCurrentPrice {
    List<CurrentPriceResponse> execute(List<Long> assetIds);
}
