package com.ourclassroom.ourclassroom.service.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ourclassroom.ourclassroom.base.result.ResultCodeEnum;
import com.ourclassroom.ourclassroom.base.util.JwtInfo;
import com.ourclassroom.ourclassroom.base.util.JwtUtils;
import com.ourclassroom.ourclassroom.service.base.exception.OurclassroomException;
import com.ourclassroom.ourclassroom.service.ucenter.entity.Member;
import com.ourclassroom.ourclassroom.service.ucenter.entity.vo.Oauth2LoginVo;
import com.ourclassroom.ourclassroom.service.ucenter.mapper.MemberMapper;
import com.ourclassroom.ourclassroom.service.ucenter.service.Oauth2MemberService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class Oauth2MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements Oauth2MemberService {
    @Override
    public void register(Oauth2LoginVo loginVo) {
        String openid = loginVo.getOpenid();
        String username = loginVo.getUsername();
        String email = loginVo.getEmail();

        if (StringUtils.isEmpty(openid)) {
            throw new OurclassroomException(ResultCodeEnum.LOGIN_EMAIL_ERROR);
        }

        // check if the openid exist
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid", openid);
        Member member = baseMapper.selectOne(queryWrapper);

        if (member == null) {
            member = new Member();
            member.setOpenid(openid);
            member.setAvatar("https://ourclassroom-file.oss-us-east-1.aliyuncs.com/default_avatar.png");
            member.setUsername(username);
            member.setEmail(email);
            member.setDisabled(false);

            baseMapper.insert(member);
        }

    }

    @Override
    public String login(String openid) {
        if (StringUtils.isEmpty(openid)) {
            throw new OurclassroomException(ResultCodeEnum.LOGIN_EMAIL_ERROR);
        }

        // check if the openid exist
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid", openid);
        Member member = baseMapper.selectOne(queryWrapper);

        if (member == null) {
            throw new OurclassroomException(ResultCodeEnum.LOGIN_EMAIL_ERROR);
        }

        // check if the member is disabled
        if (member.getDisabled()) {
            throw new OurclassroomException(ResultCodeEnum.LOGIN_DISABLED_ERROR);
        }

        // get the jwt token
        JwtInfo info = new JwtInfo();
        info.setUsername(member.getUsername());
        info.setAvatar(member.getAvatar());
        info.setId(member.getId());

        String jwtToken = JwtUtils.getJwtToken(info, 1800);

        return jwtToken;





    }
}
