package com.ourclassroom.ourclassroom.service.edu.controller;

import com.ourclassroom.ourclassroom.base.result.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

////@CrossOrigin
@RestController
@RequestMapping("user")
@Api("UserLogin")
public class LoginController {

    @ApiOperation("Mock user login")
    @PostMapping("login")
    public R login() {
        return R.ok().data("token", "admin");
    }

    @GetMapping("info")
    public R info() {
        return R.ok()
                .data("name", "admin")
                .data("roles", "[admin]")
                .data("avatar", "https://www.google.com/imgres?imgurl=https%3A%2F%2Fi.pinimg.com%2F474x%2Feb%2Fd0%2F02%2Febd002f69ccba8c11d7a35aef69a820e.jpg&imgrefurl=https%3A%2F%2Fwww.pinterest.com%2Fpin%2F832110468646532265%2F&tbnid=TGW0zAZk7rUReM&vet=12ahUKEwjzl77N7MfzAhWarXIEHRx8CH0QMygBegUIARCgAQ..i&docid=d8tjKvo_gE9vkM&w=400&h=400&itg=1&q=%E5%A4%B4%E5%83%8F&ved=2ahUKEwjzl77N7MfzAhWarXIEHRx8CH0QMygBegUIARCgAQ");
    }

    @PostMapping("logout")
    public R logout() {
        return R.ok();
    }

}
