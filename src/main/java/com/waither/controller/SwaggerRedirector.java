package com.waither.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Controller
@RequestMapping("/api/usage")
class SwaggerRedirector {
    @GetMapping
    public String api() { return "redirect:/swagger-ui/index.html"; }
}
