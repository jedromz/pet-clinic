package com.jedromz.petclinic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StaticContentController {

    @GetMapping("/admin-page")
    public String adminPage() {
        return "Only admins can see this message";
    }
}


