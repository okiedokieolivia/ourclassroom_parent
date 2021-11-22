package com.ourclassroom.ourclassroom.service.ucenter.controller.api;

import com.ourclassroom.ourclassroom.base.result.R;
import com.ourclassroom.ourclassroom.service.ucenter.entity.vo.Oauth2LoginVo;
import com.ourclassroom.ourclassroom.service.ucenter.service.Oauth2MemberService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Api(tags = "google login")
//@CrossOrigin
@Controller
@RequestMapping("/api/ucenter/google")
@Slf4j
public class ApiOauth2Controller {

    @Autowired
    private Oauth2MemberService oauth2MemberService;

    @GetMapping("login")
    public String login(Principal principal) {
        String openid = principal.getName();
        System.out.println(principal);
        String token = oauth2MemberService.login(openid);
        System.out.println(token);
        return "redirect:http://localhost:3000?token=" + token;
    }

}
