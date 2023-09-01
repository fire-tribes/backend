package com.fires.fires.external.api.kis.config.feign.decoder;

import com.fires.fires.external.exception.KisException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

public class KisFeignErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        final HttpStatus httpStatus = HttpStatus.resolve(response.status());

        if (httpStatus == null || httpStatus.isError()) {
            throw new KisException(HttpStatus.INTERNAL_SERVER_ERROR, errorDecoder.decode(methodKey, response));
        }


        return errorDecoder.decode(methodKey, response);
    }
}
