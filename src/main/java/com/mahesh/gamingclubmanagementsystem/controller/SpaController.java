package com.mahesh.gamingclubmanagementsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SpaController {

    @GetMapping({
            "/",
            "/login",
            "/dashboard",
            "/sessions",
            "/resources",
            "/customers",
            "/reports"
    })
    public String forwardToReact() {
        return "forward:/index.html";
    }
}