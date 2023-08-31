package com.fires.fires.external.api.kis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OverseasPriceResponse(@JsonProperty(value = "rt_cd")
                                    String returnCode,
                                    @JsonProperty(value = "msg_cd")
                                    String messageCode,
                                    @JsonProperty(value = "msg1")
                                    String message,
                                    @JsonProperty(value = "output")
                                    ResponseDetail output) {

    public record ResponseDetail(@JsonProperty(value = "rsym")
                                 String symbol,
                                 @JsonProperty("last")
                                 String price,
                                 @JsonProperty(value = "diff")
                                 String priceChange,
                                 @JsonProperty("sign")
                                 String priceChangeCode) {
    }

}
