package com.fires.fires.external.api.kis;

import com.fires.fires.common.config.CacheConfig;
import com.fires.fires.external.api.kis.config.feign.FeignConfig;
import com.fires.fires.external.api.kis.constant.ExchangeCode;
import com.fires.fires.external.api.kis.constant.MarketDivCode;
import com.fires.fires.external.api.kis.dto.DomesticPriceResponse;
import com.fires.fires.external.api.kis.dto.OverseasPriceResponse;
import com.fires.fires.external.api.kis.dto.TokenRequest;
import com.fires.fires.external.api.kis.dto.TokenResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "koreaInvestment", url = "${external.api.korea-invest.base-url}", configuration = FeignConfig.class)
public interface KisClient {

    /**
     * @param headers api 요청 필수 헤더 Map객체
     * @param FID_COND_MRKT_DIV_CODE 시장분류코드
     * @param FID_INPUT_ISCD 종목코드
     * @see <a href = "https://apiportal.koreainvestment.com/apiservice/apiservice-domestic-stock-quotations#L_07802512-4f49-4486-91b4-1050b6f5dc9d">국내주식 현재가 시세 api</a>
     * @return 주식 현재가에 관련된 record반환
     */
    @GetMapping("/uapi/domestic-stock/v1/quotations/inquire-price")
    public DomesticPriceResponse getDomesticAssetCurrentPrice(@RequestHeader Map<String, String> headers,
                                                              @RequestParam MarketDivCode FID_COND_MRKT_DIV_CODE,
                                                              @RequestParam String FID_INPUT_ISCD);
    @GetMapping("/uapi/overseas-price/v1/quotations/price")
    public OverseasPriceResponse getOverseasAssetCurrentPrice(@RequestHeader Map<String, String> headerMap,
                                                              @RequestParam String AUTH,
                                                              @RequestParam ExchangeCode EXCD,
                                                              @RequestParam String SYMB);

    /**
     * @param tokenRequest developers에서 발급한 앱키, 앱시크릿키가 포함된 요청객체
     * @See <a href = "https://apiportal.koreainvestment.com/apiservice/oauth2#L_fa778c98-f68d-451e-8fff-b1c6bfe5cd30">접근토큰 발급 api</a>
     * @return 접근토큰과 관련된 정보가 포함된 record반환
     */
    @Cacheable(cacheNames = CacheConfig.CACHE_NAME_KIS_ACCESS_KEY, key = "#tokenRequest.appsecret")
    @GetMapping("/oauth2/tokenP")
    public TokenResponse getAccessToken(@RequestBody TokenRequest tokenRequest);


}
