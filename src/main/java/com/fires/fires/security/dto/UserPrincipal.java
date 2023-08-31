package com.fires.fires.security.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Spring Security에서 사용할 UserDetail 객체
 * @param id  애플리케이션에서 생성한 사용자 id
 * @param userProviderId oAuthProvider(현재는 KAKAO)가 부여한 사용자별 식별자
 * @param username 사용자 이름
 * @param password 비밀번호
 * @param authorities 사용자 권한
 */
public record UserPrincipal(Long id,
                            String userProviderId,
                            String username,
                            String password,
                            Collection<? extends GrantedAuthority> authorities) implements UserDetails {


    public static UserPrincipal of(Long id, String userProviderId, String username, String password) {
        return new UserPrincipal(id,
                userProviderId,
                username,
                password,
                AuthorityUtils.createAuthorityList("USER"));
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {return this.getAuthorities();}

    @Override
    public String getPassword() {return this.password;}

    @Override
    public String getUsername() {return this.username;}

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {return false;}

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {return false;}

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {return false;}

    @JsonIgnore
    @Override
    public boolean isEnabled() {return false;}
}
