package com.fires.fires.external.api.polygon;

import java.util.List;

public record PolygonResponse(String next_url,
                              List<dividend> results,
                              String status) {


    record dividend(String cash_amount,
                    String declaration_date,
                    String dividend_type,
                    String ex_dividend_date,
                    Integer frequency,
                    String pay_date,
                    String record_date,
                    String ticker){

    }
}
