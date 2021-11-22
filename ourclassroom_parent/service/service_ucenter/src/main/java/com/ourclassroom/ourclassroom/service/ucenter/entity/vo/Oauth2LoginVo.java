package com.ourclassroom.ourclassroom.service.ucenter.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Oauth2LoginVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String openid;
    private String email;
    private String username;

}
