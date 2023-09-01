package com.fires.fires.security.config;

import com.fires.fires.common.dto.Response;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.*;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * Security Filter에 swagger를 적용하기 위한 수동 설정 클래스
 * 현재 access token 발급 url은 controller에서 처리 하지 않고, filter에서 처리중
 * 향후에는 RestDocs로 전환하고 싶기에 코드 리팩토링까지는 불필요
 */
@Configuration
@Slf4j
public class FilterSwaggerConfig {
    private record MemberLoginRequest(String providerUserId,
                                      String email) {
    }
    @Bean
    OpenApiCustomizer jwtAuthFilterApiCustomizer() {

        return openApi -> {

            Operation operation = new Operation();
            ObjectSchema objectSchema = new ObjectSchema();
            objectSchema.addProperty("providerUserId", new ComposedSchema());
            objectSchema.addProperty("email", new ComposedSchema());

            objectSchema.type(org.springframework.http.MediaType.APPLICATION_JSON_VALUE);
            operation.description("access token 생성");
            operation.setTags(List.of("로그인, 회원가입"));
            Parameter parameter = new Parameter();

            parameter.name("로그인 요청");
            parameter.setSchema(objectSchema);
            parameter.setIn("body");

            RequestBody requestBody = new RequestBody();
            Schema<MemberLoginRequest> memberLoginRequestSchema = new Schema<>();
            memberLoginRequestSchema.addProperty("providerUserId", new StringSchema());
            memberLoginRequestSchema.addProperty("email", new StringSchema());
            requestBody.required(true);
            requestBody.setContent(new Content().addMediaType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE, new MediaType().schema(memberLoginRequestSchema)));

            operation.requestBody(requestBody);


            ApiResponses apiResponses = new ApiResponses();

            Schema<Response<String, String>> loginSuccessSchema = new Schema<>();
            loginSuccessSchema.addProperty("code", new IntegerSchema());
            loginSuccessSchema.addProperty("meta", new MapSchema().addProperty("isFirstLogin", new BooleanSchema()._default(true)));
            loginSuccessSchema.addProperty("data", new MapSchema().addProperty("token", new StringSchema()._default("jwt_token")));
            ApiResponse loginSuccessResponse = new ApiResponse().content(new Content().addMediaType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE, new MediaType().schema(loginSuccessSchema))).description("기존 사용자 로그인 성공");
            apiResponses.addApiResponse(String.valueOf(HttpStatus.OK.value()), loginSuccessResponse);

            Schema<Response<String, String>> signUpneededSchema = new Schema<>();
            signUpneededSchema.addProperty("code", new IntegerSchema());
            signUpneededSchema.addProperty("meta", new MapSchema().addProperty("isFirstLogin", new BooleanSchema()._default(false)));
            signUpneededSchema.addProperty("data", new MapSchema().addProperty("token", new StringSchema()._default("jwt_token")));
            ApiResponse signupNeededResponse = new ApiResponse().content(new Content().addMediaType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE, new MediaType().schema(signUpneededSchema))).description("기존에 가입되지 않은 사용자 접근 시 응답(응답코드와 meta가 다름)");
            apiResponses.addApiResponse(String.valueOf(HttpStatus.CREATED.value()), signupNeededResponse);





            operation.responses(apiResponses);
            PathItem pathItem = new PathItem().post(operation);
            openApi.getPaths().addPathItem("/api/v1/auth/token", pathItem);
        };
    }

}
