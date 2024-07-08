//package com.project.throw_wa.auth.vo;
//
//import com.project.throw_wa.member.entity.Member;
//import lombok.Data;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//
//import java.util.Collection;
//import java.util.Map;
//
//@Data
//public class MemberDetails implements UserDetails, OAuth2User {
//
//    final Member member;
//    private Map<String, Object> attributes;
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return this.member.getAuthorities().stream()
//                .map((authority -> authority.getRoleAuth().name()))
//                .map((roleAuth) -> new SimpleGrantedAuthority(roleAuth))
//                .toList();
//    }
//
//    @Override
//    public String getPassword() {
//        return this.member.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return this.member.getUsername();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//
//    /**
//     * OAuth2User 구현메소드
//     * - name : username을 의미 (12345678@google)
//     * - attributes : IDP에 제공되는 사용자정보를 담은 맵객체
//     * @return
//     */
//    @Override
//    public String getName() {
//        return this.member.getUsername();
//    }
//    @Override
//    public Map<String, Object> getAttributes() {
//        return this.attributes;
//    }
//}
