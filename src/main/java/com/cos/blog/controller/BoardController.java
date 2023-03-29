package com.cos.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {
    @GetMapping({"","/"})
    public String index(){
        return "index";
    }

    @GetMapping({"/layout/header"})
    public String header(){
        return "layout/header";
    }
    @GetMapping({"/layout/footer"})
    public String footer(){
        return "layout/footer";
    }
}
