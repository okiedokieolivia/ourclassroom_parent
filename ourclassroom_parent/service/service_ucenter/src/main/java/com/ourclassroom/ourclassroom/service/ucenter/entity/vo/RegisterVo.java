package com.ourclassroom.ourclassroom.service.ucenter.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private String email;

}