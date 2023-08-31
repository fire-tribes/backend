package com.fires.fires.external.api.kis.config.feign;

import com.fires.fires.external.api.kis.config.feign.decoder.KisFeignErrorDecoder;
import feign.Logger;
import org.springframework.context.annotation.Bean;

public class FeignConfig {

    @Bean
    Logger.Level feignLoggerlevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public KisFeignErrorDecoder kisFeignErrorDecoder() {
        return new KisFeignErrorDecoder();
    }
}
