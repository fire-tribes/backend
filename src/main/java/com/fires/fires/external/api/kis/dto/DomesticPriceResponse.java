package com.fires.fires.external.api.kis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 한국투자증권 주식현재가 시세 api로부터 받은 응답값
 * @author galmegiz
 * @param returnCode
 * @param messageCode
 * @param message
 * @param output
 * @see <a href = "https://apiportal.koreainvestment.com/apiservice/apiservice-domestic-stock-quotations#L_07802512-4f49-4486-91b4-1050b6f5dc9d">open api link</a>
 */
public record DomesticPriceResponse(@JsonProperty(value = "rt_cd")
                                    String returnCode,
                                    @JsonProperty(value = "msg_cd")
                                    String messageCode,
                                    @JsonProperty(value = "msg1")
                                    String message,
                                    @JsonProperty(value = "output", required = false)
                                    ResponseDetail output
) {

    public record ResponseDetail(@JsonProperty(value = "stck_prpr")
                                 String price,
                                 @JsonProperty(value = "stck_prdy_vrss")
                                 String priceChange,
                                 @JsonProperty(value = "stck_vrss_sign")
                                 Integer priceChangeCode) {

    }
}
