package com.ourclassroom.ourclassroom.service.ucenter.service;

import com.ourclassroom.ourclassroom.service.base.dto.MemberDto;
import com.ourclassroom.ourclassroom.service.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ourclassroom.ourclassroom.service.ucenter.entity.vo.LoginVo;
import com.ourclassroom.ourclassroom.service.ucenter.entity.vo.RegisterVo;
import com.ourclassroom.ourclassroom.service.ucenter.entity.vo.UserVo;

public interface MemberService extends IService<Member> {

    void register(RegisterVo registerVo);

    String login(LoginVo loginVo);

    MemberDto getMemberDtoByMemberId(String memberId);

    UserVo getUserVoByMemberId(String memberId);
}
