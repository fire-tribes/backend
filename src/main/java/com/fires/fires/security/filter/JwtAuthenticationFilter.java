package com.fires.fires.security.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fires.fires.common.constant.ResponseCode;
import com.fires.fires.common.dto.Response;
import com.fires.fires.common.exception.ResourceNotFoundException;
import com.fires.fires.member.constant.Provider;
import com.fires.fires.member.controller.MemberController;
import com.fires.fires.member.dto.MemberDto;
import com.fires.fires.member.dto.MemberSignupRequest;
import com.fires.fires.member.service.MemberReadService;
import com.fires.fires.security.dto.UserPrincipal;
import com.fires.fires.security.util.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


@RequiredArgsConstructor
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final String LOGIN_URL = "/api/v1/auth/token";
    private final AntPathRequestMatcher DEFAULT_REQUEST_MATCHER = new AntPathRequestMatcher(LOGIN_URL, "POST");
    private SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    private final JwtTokenUtil jwtTokenUtil;
    private final ObjectMapper objectMapper;
    private final MemberReadService memberReadService;
    private final MemberController memberController;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (!DEFAULT_REQUEST_MATCHER.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        String body = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
        log.debug("Login request : {}", body);
        //todo: front에서 보내는 데이터임을 검증하는 로직 필요
        MemberLoginRequest memberLoginRequest = null;
        try {
            memberLoginRequest = objectMapper.readValue(body, MemberLoginRequest.class);
            requestValidationCheck(memberLoginRequest);
            //todo: db에서 회원정보 조회필요 있는지 검토 필요, 권한정보 체크가 필요한 경우 db조회는 필요
            //Front에서 문제가 있는 memberRequest를 보냈을 때(존재하지 않는 사용자)의 체크 용도로도 db조회는 필요할 수 있음
            MemberDto memberDto = memberReadService.getMemberDto(memberLoginRequest.providerUserId);
            UserPrincipal userPrincipal = UserPrincipal.of(memberDto.id(), memberDto.providerUserid(), memberDto.username(), memberDto.password());

            String token = jwtTokenUtil.createToken(userPrincipal);
            authenticationSuccessHandler(response, token);
        } catch(ResourceNotFoundException e) {
            //기존 회원이 아닐 경우 로그인 처리를 한다.
            //별도 api로 구분할지 고민, 기존 회원이 아닐 경우 추가 api를 호출하게 하는 방법도 있으나, api 호출횟수를 줄일 수 있도록 filter에서 회원가입까지 진행
            signUpMemberAndSendResponse(memberLoginRequest, response);
        } catch (JsonProcessingException | IllegalArgumentException e) {
            illegalAuthRequestHandler(response, e);
        }  catch (Exception e) {
            authenticationFailHandler(response, e);
        }
    }

    private void signUpMemberAndSendResponse(MemberLoginRequest memberLoginRequest, HttpServletResponse response) {
        MemberSignupRequest memberSignupRequest = new MemberSignupRequest(memberLoginRequest.providerUserId, Provider.KAKAO, "default", memberLoginRequest.email);
        MemberDto newMember = memberController.signUp(memberSignupRequest)
                                              .getData();


        UserPrincipal userPrincipal = UserPrincipal.of(newMember.id(), newMember.providerUserid(), newMember.username(), newMember.password());
        String token = jwtTokenUtil.createToken(userPrincipal);

        Response<isFirstLogin, Token> successResponse = Response.successWithMeta(ResponseCode.SUCCESS.getCode(), new isFirstLogin(false), new Token(token));
        sendResponse(response, successResponse);
    }

    private void requestValidationCheck(MemberLoginRequest memberLoginRequest) {
        if(memberLoginRequest == null || memberLoginRequest.providerUserId() == null){
            log.debug("[Application Error] Invalid login request {}", memberLoginRequest);
            throw new IllegalArgumentException();
        }
    }

    private void authenticationSuccessHandler(HttpServletResponse response, String token){
        Response<isFirstLogin, Token> successResponse = Response.successWithMeta(ResponseCode.SUCCESS.getCode(), new isFirstLogin(true), new Token(token));
        sendResponse(response, successResponse);
    }

    private void authenticationFailHandler(HttpServletResponse response, Exception e){
        log.debug("[Application Error] Jwt Token Verification Fail!", e);
        this.securityContextHolderStrategy.clearContext();
        Response<String, Void> errorResponse = Response.failWithMeta(ResponseCode.INVALID_TOKEN_ERROR.getCode(), ResponseCode.INVALID_TOKEN_ERROR.getDesc());
        sendResponse(response, errorResponse);

    }

    private void illegalAuthRequestHandler(HttpServletResponse response, Exception e){
        log.debug("[Application Error] Invalid login request");
        this.securityContextHolderStrategy.clearContext();
        Response<String, Void> errorResponse = Response.failWithMeta(ResponseCode.INVALID_REQUEST_ERROR.getCode(), ResponseCode.INVALID_REQUEST_ERROR.getDesc());
        sendResponse(response, errorResponse);
    }

    private void sendResponse(HttpServletResponse response, Response<?, ?> loginResponse) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.OK.value());
        try {
            String body = objectMapper.writeValueAsString(loginResponse);
            response.getWriter().println(body);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    /**
     * json 생성 편의성을 위한 dto 객체
     */
    private record isFirstLogin(boolean isFirstLogin) {
    }

    /**
     * json 생성 편의성을 위한 dto 객체
     */
    private record Token(String token) {
    }

    /**
     * json 생성 편의성을 위한 dto 객체
     */
    private record MemberLoginRequest(String providerUserId,
                                      String email) {
    }
}

