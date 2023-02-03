package com.waither.controller;

import com.waither.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/settings")
public class LoginController {

    @Autowired
    UserService userService;


}
