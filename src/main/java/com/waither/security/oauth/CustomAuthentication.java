package com.waither.security.oauth;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter
public class CustomAuthentication implements OAuth2User, UserDetails {

    private Long id;
    private String email;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public CustomAuthentication(String id, String email, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.authorities = authorities;
    }


    public static CustomAuthentication create(User user) {
        List<GrantedAuthority> authorityList = Collections.singletonList(new SimpleGrantedAuthority(
                "" + RoleType.USER));
        return new CustomAuthentication(
                user.getId(),
                user.getEmail(),
                authorityList
        );
    }

    public static CustomAuthentication create(User user, Map<String, Object> attributes) {
        CustomAuthentication customAuthentication = CustomAuthentication.create(user);
        customAuthentication.setAttributes(attributes);
        return customAuthentication;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}
