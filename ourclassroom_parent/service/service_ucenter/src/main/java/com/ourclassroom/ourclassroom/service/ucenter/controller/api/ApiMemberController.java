package com.ourclassroom.ourclassroom.service.ucenter.controller.api;


import com.ourclassroom.ourclassroom.base.result.R;
import com.ourclassroom.ourclassroom.base.result.ResultCodeEnum;
import com.ourclassroom.ourclassroom.base.util.ExceptionUtils;
import com.ourclassroom.ourclassroom.base.util.JwtInfo;
import com.ourclassroom.ourclassroom.base.util.JwtUtils;
import com.ourclassroom.ourclassroom.service.base.dto.MemberDto;
import com.ourclassroom.ourclassroom.service.base.exception.OurclassroomException;
import com.ourclassroom.ourclassroom.service.ucenter.entity.vo.LoginVo;
import com.ourclassroom.ourclassroom.service.ucenter.entity.vo.RegisterVo;
import com.ourclassroom.ourclassroom.service.ucenter.entity.vo.UserVo;
import com.ourclassroom.ourclassroom.service.ucenter.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "Member management")
//@CrossOrigin
@RestController
@RequestMapping("/api/ucenter/member")
@Slf4j
public class ApiMemberController {

    @Autowired
    private MemberService memberService;

    @ApiOperation(value = "Sign up")
    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok().message("Successfully registered!");
    }

    @ApiOperation("Sign in")
    @PostMapping("login")
    public R login(@RequestBody LoginVo loginVo) {
        String token = memberService.login(loginVo);
        return R.ok().data("token", token).message("login successfully");
    }

    @ApiOperation(value = "Get user info from token")
    @GetMapping("get-login-info")
    public R getLoginInfo(HttpServletRequest request) {
        try{
            JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
            return R.ok().data("userInfo", jwtInfo);
        }catch (Exception e){
            ExceptionUtils.getMessage(e);
            log.error("Fail to fetch the user information" + e.getMessage());
            throw new OurclassroomException(ResultCodeEnum.FETCH_USERINFO_ERROR);
        }
    }

    @ApiOperation(value = "Get user info from token")
    @GetMapping("get-user-info")
    public R getUserInfo(HttpServletRequest request) {
        try {
            JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
            UserVo userVo = memberService.getUserVoByMemberId(jwtInfo.getId());
            return R.ok().data("user", userVo);
        } catch (Exception e) {
            ExceptionUtils.getMessage(e);
            log.error("Fail to fetch the user information" + e.getMessage());
            throw new OurclassroomException(ResultCodeEnum.FETCH_USERINFO_ERROR);
        }
    }


    @ApiOperation("Get user info by id")
    @GetMapping("inner/get-member-dto/{memberId}")
    public MemberDto getMemberDtoByMemberId(
            @ApiParam(value = "memberID", required = true)
            @PathVariable String memberId){
        MemberDto memberDto = memberService.getMemberDtoByMemberId(memberId);
        return memberDto;
    }
}

