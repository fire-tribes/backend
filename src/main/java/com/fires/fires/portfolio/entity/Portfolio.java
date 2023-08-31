package com.fires.fires.portfolio.entity;

import com.fires.fires.common.entity.BaseTimeEntity;
import com.fires.fires.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.springframework.security.core.parameters.P;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "portfolios")
@Entity
public class Portfolio extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_id")
    private Long id;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Portfolio(Member member) {
        this.member = member;
    }

    public static Portfolio of(Member member) {
        return new Portfolio(member);
    }
}
