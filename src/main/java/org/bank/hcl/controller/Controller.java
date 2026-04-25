package org.bank.hcl.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1")
public class Controller {


    @PostMapping("/login")
    public String getToken(){
        return null;
    }
}
