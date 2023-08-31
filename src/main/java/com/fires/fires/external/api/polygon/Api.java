package com.fires.fires.external.api.polygon;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "divend", url = "https://api.polygon.io/v3/reference")
public interface Api {

    @GetMapping("/dividends")
    PolygonResponse getDivide(@RequestParam String apiKey, @RequestParam String ticker);
}
