package com.ourclassroom.ourclassroom.service.ucenter.service.impl;

import com.alibaba.nacos.common.util.Md5Utils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ourclassroom.ourclassroom.base.result.ResultCodeEnum;
import com.ourclassroom.ourclassroom.base.util.FormUtils;
import com.ourclassroom.ourclassroom.base.util.JwtInfo;
import com.ourclassroom.ourclassroom.base.util.JwtUtils;
import com.ourclassroom.ourclassroom.base.util.MD5Utils;
import com.ourclassroom.ourclassroom.service.base.dto.MemberDto;
import com.ourclassroom.ourclassroom.service.base.exception.OurclassroomException;
import com.ourclassroom.ourclassroom.service.ucenter.entity.Member;
import com.ourclassroom.ourclassroom.service.ucenter.entity.vo.LoginVo;
import com.ourclassroom.ourclassroom.service.ucenter.entity.vo.RegisterVo;
import com.ourclassroom.ourclassroom.service.ucenter.entity.vo.UserVo;
import com.ourclassroom.ourclassroom.service.ucenter.mapper.MemberMapper;
import com.ourclassroom.ourclassroom.service.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {



    @Override
    public void register(RegisterVo registerVo) {
        String username = registerVo.getUsername();
        String password = registerVo.getPassword();
        String email = registerVo.getEmail();

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(email)) {
            throw new OurclassroomException("username, password and email are required", ResultCodeEnum.PARAM_ERROR.getCode());
        }

        if (!FormUtils.isValidEmail(email)) {
            throw new OurclassroomException("not a valid email account", ResultCodeEnum.REGISTER_EMAIL_ERROR.getCode());
        }

        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        Integer count = baseMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new OurclassroomException(ResultCodeEnum.REGISTER_EMAIL_ERROR);
        }

        Member member = new Member();
        member.setUsername(username);
        member.setPassword(MD5Utils.encrypt(password));
        member.setAvatar("https://ourclassroom-file.oss-us-east-1.aliyuncs.com/default_avatar.png");
        member.setDisabled(false);
        member.setEmail(email);

        baseMapper.insert(member);
    }

    @Override
    public String login(LoginVo loginVo) {
        String email = loginVo.getEmail();
        String password = loginVo.getPassword();
        if (StringUtils.isEmpty(email) || !FormUtils.isValidEmail(email)) {
            throw new OurclassroomException(ResultCodeEnum.LOGIN_EMAIL_ERROR);
        }

        if (StringUtils.isEmpty(password)) {
            throw new OurclassroomException(ResultCodeEnum.PARAM_ERROR);
        }

        // check if the email exists
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        Member member = baseMapper.selectOne(queryWrapper);

        if (member == null) {
            throw new OurclassroomException("Email is not registered", ResultCodeEnum.LOGIN_EMAIL_ERROR.getCode());
        }

        // check the password
        if (!MD5Utils.encrypt(password).equals(member.getPassword())) {
            throw new OurclassroomException(ResultCodeEnum.LOGIN_PASSWORD_ERROR);
        }

        // check the user is disabled or not
        if (member.getDisabled()) {
            throw new OurclassroomException(ResultCodeEnum.LOGIN_DISABLED_ERROR);
        }

        // generate the token
        JwtInfo info = new JwtInfo();
        info.setUsername(member.getUsername());
        info.setAvatar(member.getAvatar());
        info.setId(member.getId());

        String jwtToken = JwtUtils.getJwtToken(info, 1800);

        return jwtToken;
    }

    @Override
    public MemberDto getMemberDtoByMemberId(String memberId) {
        Member member = baseMapper.selectById(memberId);

        MemberDto memberDto = new MemberDto();
        BeanUtils.copyProperties(member, memberDto);

        return memberDto;

    }

    @Override
    public UserVo getUserVoByMemberId(String memberId) {
        Member member = baseMapper.selectById(memberId);

        UserVo userVo = new UserVo();
        userVo.setId(member.getId());
        userVo.setAvatar(member.getAvatar());
        userVo.setEmail(member.getEmail());
        userVo.setUsername(member.getUsername());
        userVo.setBio(member.getBio());

        return userVo;
    }
}
