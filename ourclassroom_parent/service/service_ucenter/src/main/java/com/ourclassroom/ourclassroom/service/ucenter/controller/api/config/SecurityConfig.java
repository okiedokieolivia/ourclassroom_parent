package com.ourclassroom.ourclassroom.service.ucenter.controller.api.config;

import com.ourclassroom.ourclassroom.service.ucenter.entity.CustomOauth2User;
import com.ourclassroom.ourclassroom.service.ucenter.entity.vo.Oauth2LoginVo;
import com.ourclassroom.ourclassroom.service.ucenter.service.Oauth2MemberService;
import com.ourclassroom.ourclassroom.service.ucenter.service.impl.CustomOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomOauth2UserService oauthUserService;

    @Autowired
    private Oauth2MemberService oauth2MemberService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // cors().and()
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/ucenter/google/login")
                .authenticated()
                .anyRequest().permitAll()
                .and().oauth2Login().userInfoEndpoint().userService(oauthUserService)
                .and().successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        CustomOauth2User oauth2User = (CustomOauth2User) authentication.getPrincipal();
                        Oauth2LoginVo loginVo = new Oauth2LoginVo();
                        loginVo.setOpenid(oauth2User.getOpenid());
                        loginVo.setUsername(oauth2User.getUsername());
                        loginVo.setEmail(oauth2User.getEmail());
                        oauth2MemberService.register(loginVo);

                        httpServletResponse.sendRedirect("/api/ucenter/google/login");
                    }
                });
    }
}
