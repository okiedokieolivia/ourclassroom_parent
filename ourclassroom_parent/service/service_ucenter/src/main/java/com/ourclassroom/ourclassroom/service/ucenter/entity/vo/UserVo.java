package com.ourclassroom.ourclassroom.service.ucenter.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String avatar;
    private String email;
    private String username;
    private String bio;
}
