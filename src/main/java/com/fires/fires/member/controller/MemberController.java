package com.fires.fires.member.controller;

import com.fires.fires.common.constant.ResponseCode;
import com.fires.fires.common.dto.Response;
import com.fires.fires.member.dto.MemberDto;
import com.fires.fires.member.dto.MemberSignupRequest;
import com.fires.fires.member.service.MemberWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberWriteService memberWriteService;

    @PostMapping
    public Response<Void, MemberDto> signUp(@RequestBody MemberSignupRequest memberSignupRequest) {
        MemberDto newMember = memberWriteService.registerMember(memberSignupRequest);
        return Response.success(ResponseCode.SUCCESS.getCode(), newMember);
    }
}
