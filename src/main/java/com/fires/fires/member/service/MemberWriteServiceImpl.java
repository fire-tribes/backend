package com.fires.fires.member.service;

import com.fires.fires.member.dto.MemberDto;
import com.fires.fires.member.dto.request.MemberSignupRequest;
import com.fires.fires.member.entity.Member;
import com.fires.fires.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberWriteServiceImpl implements MemberWriteService{
    private final MemberRepository memberRepository;

    /**
     *
     * @param memberSignupRequest 등록하고자 하는 사용자 정보
     * @return 등록된 사용자 dto 정보
     */
    public MemberDto registerMember(MemberSignupRequest memberSignupRequest) {
        Member newMember = Member.of(memberSignupRequest.providerUserId(),
                                     memberSignupRequest.provider(),
                                     memberSignupRequest.email());
        memberRepository.save(newMember);
        return MemberDto.from(newMember);
    }
}
