package com.fires.fires.member.service;

import com.fires.fires.common.constant.ResponseCode;
import com.fires.fires.common.exception.ResourceNotFoundException;
import com.fires.fires.member.dto.MemberDto;
import com.fires.fires.member.entity.Member;
import com.fires.fires.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberReadService {

    private final MemberRepository memberRepository;
    /**
     * 서비스 퍼사드에서 Member Entity를 직접 사용해야 하는 경우 호출
     * @param memberId 얻고자 하는 멤버 id
     * @return 멤버 Entity
     */
    public Member getMemberEntity(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new ResourceNotFoundException(ResponseCode.MEMBER_NOT_FOUND));
    }

    /**
     * Filter, Controller 등에서 member Dto를 얻을 때 사용,
     * @param providerUserId oauth proivder가 제공한 사용자 식별자
     * @return 멤버 dto 객체
     */
    public MemberDto getMemberDto(String providerUserId) {
        Member member = memberRepository.findByProviderUserId(providerUserId).orElseThrow(() -> new ResourceNotFoundException(ResponseCode.MEMBER_NOT_FOUND));
        return MemberDto.from(member);
    }

    /**
     * 로그인 사용자의 Member프록시객체가 있을 때 사용
     * memberId가 존재하지 않을 수 있는 경우에는 사용하지 말 것
     * @param memberId 얻고자 하는 멤버 id
     * @return 멤버 프록시 객체
     */
    public Member getMemberProxy(Long memberId) {
        return memberRepository.getReferenceById(memberId);
    }
}
