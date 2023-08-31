package com.fires.fires.search.controller;


import com.fires.fires.assets.asset.dto.AssetDto;
import com.fires.fires.assets.asset.service.AssetReadService;
import com.fires.fires.assets.contant.AssetCategory;
import com.fires.fires.assets.contant.AssetCategory_back;
import com.fires.fires.common.constant.ResponseCode;
import com.fires.fires.common.dto.Response;
import com.fires.fires.search.controller.dto.CurrentPriceResponse;
import com.fires.fires.search.controller.usecase.SearchAssetCurrentPriceBySymbol;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search/assets")
@Tag(name = "검색기능")
@Slf4j
public class SearchController {
    private static final String DEFAULT_CATEGORY = "ALL";
    private final AssetReadService assetReadService;
    private final SearchAssetCurrentPriceBySymbol searchAssetCurrentPriceBySymbol;
    @Operation(summary = "자산 목록 검색", description = "자산 정보 반환")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ok", useReturnTypeSchema = true)
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Response<Pageable, List<AssetDto>> searchAllCategory(@RequestParam(required = false, defaultValue = DEFAULT_CATEGORY) AssetCategory category,
                                                                @Parameter(description = "검색어 회사 symbol, name 모두 입력 가능") @RequestParam String query,
                                                                @Parameter(description = "페이징 처리를 위한 파라미터") @RequestParam(required = false, defaultValue = "0") Integer page) {
        PageRequest pageRequest = PageRequest.of(page, 30);
        Slice<AssetDto> assets = assetReadService.getAssetsByNameOrSymbol(query, pageRequest);
        return Response.successWithMeta(ResponseCode.SUCCESS.getCode(), assets.getPageable(), assets.getContent());
    }
    @Operation(summary = "자산 현재가 검색", description = "자산 현재가 검색")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "자산별 현재가를 보여준다. currency는 통화정보이다(KRW, USD). currentPrice가 0인 경우는 api 오류등으로 인해 정보를 얻을 수 없는 경우이다.", useReturnTypeSchema = true)
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/price")
    public Response<Void, List<CurrentPriceResponse>> getCurrentPrice(@Parameter(description = "자산 검색 결과로 반환된 assetId 리스트")@RequestParam(name = "asset-ids") List<Long> assetIds) {
        log.info("logging !!!! {}", assetIds);
        List<CurrentPriceResponse> currentPrices = searchAssetCurrentPriceBySymbol.execute(assetIds);
        return Response.success(ResponseCode.SUCCESS.getCode(), currentPrices);
    }
}
