package com.fires.fires.member.service;

import com.fires.fires.member.dto.MemberDto;
import com.fires.fires.member.entity.Member;

public interface MemberReadService {
    public Member getMemberEntity(Long memberId);
    public MemberDto getMemberDto(String providerUserId);
    public Member getMemberProxy(Long memberId);
}
