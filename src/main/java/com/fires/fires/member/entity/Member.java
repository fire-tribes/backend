package com.fires.fires.member.entity;

import com.fires.fires.common.entity.BaseTimeEntity;
import com.fires.fires.member.constant.Provider;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 애플리케이션 전반에서 사용될 사용자 entity
 * 현재는 oauth만 사용하므로 password필드는 의미없음
 * 애플리케이션에서 현재 사용자 이름은 활용하지 않으므로 의미없음
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "members")
@Getter
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String providerUserId;
    @Enumerated(EnumType.STRING)
    private Provider provider;
    private String username;
    private String email;
    private String password;
    private LocalDateTime lastAccessedTime;

    private Member(String providerUserId, Provider provider, String username, String email, String password, LocalDateTime lastAccessedTime) {
        this.providerUserId = providerUserId;
        this.provider = provider;
        this.username = username;
        this.email = email;
        this.password = password;
        this.lastAccessedTime = lastAccessedTime;
    }

    /**
     * 신규 멤버 생성용 정적생성자
     * @param providerUserId Oauth provider가 제공한 사용자 식별자
     * @param provider Oauth provider
     * @param email 사용자 이메일
     * @return
     */
    public static Member of(String providerUserId, Provider provider, String email) {
        return new Member(providerUserId,
                provider,
                "default",
                email,
                UUID.randomUUID().toString().substring(0, 8),
                LocalDateTime.now());
    }


}
