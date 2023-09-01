package com.fires.fires.member.service;

import com.fires.fires.member.dto.MemberDto;
import com.fires.fires.member.dto.request.MemberSignupRequest;

public interface MemberWriteService {
    MemberDto registerMember(MemberSignupRequest memberSignupRequest);
}
