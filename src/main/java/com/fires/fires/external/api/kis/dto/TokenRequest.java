package com.fires.fires.external.api.kis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenRequest(@JsonProperty(value = "grant_type")
                           String grantType,
                           String appkey,
                           String appsecret) {

    public TokenRequest(String appkey, String appsecret) {
        this("client_credentials", appkey, appsecret);
    }
}
