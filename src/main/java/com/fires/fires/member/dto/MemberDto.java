package com.fires.fires.member.dto;

import com.fires.fires.member.constant.Provider;
import com.fires.fires.member.entity.Member;

import java.time.LocalDateTime;

public record MemberDto(Long id,
                        String providerUserid,
                        Provider provider,
                        String email,
                        String username,
                        String password,
                        LocalDateTime lastAccessedTime) {

    public static MemberDto from(Member member) {
        return new MemberDto(member.getId(),
                            member.getProviderUserId(),
                            member.getProvider(),
                            member.getEmail(),
                            member.getUsername(),
                            member.getPassword(),
                            member.getLastAccessedTime());
    }
}
