package com.ourclassroom.ourclassroom.service.ucenter.service;

import com.ourclassroom.ourclassroom.service.ucenter.entity.vo.Oauth2LoginVo;

public interface Oauth2MemberService {
    void register(Oauth2LoginVo loginVo);

    String login(String openId);
}
