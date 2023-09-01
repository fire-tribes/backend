package com.fires.fires.member.dto.request;

import com.fires.fires.member.constant.Provider;

public record MemberSignupRequest(String providerUserId,
                                  Provider provider,
                                  String username,
                                  String email) {
}
