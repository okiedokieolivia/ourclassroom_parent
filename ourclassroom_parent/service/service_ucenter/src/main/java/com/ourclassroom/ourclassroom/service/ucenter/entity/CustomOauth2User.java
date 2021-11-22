package com.ourclassroom.ourclassroom.service.ucenter.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomOauth2User implements OAuth2User {
    private OAuth2User oAuth2User;

    public CustomOauth2User(OAuth2User oauth2User) {
        this.oAuth2User = oauth2User;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oAuth2User.getAuthorities();
    }

    @Override
    public String getName() {
        return oAuth2User.getName();
    }

    public String getUsername() {
        return oAuth2User.getAttribute("name");
    }

    public String getEmail() {
        return oAuth2User.getAttribute("email");
    }

    public String getOpenid() {
        return oAuth2User.getName();
    }

}
