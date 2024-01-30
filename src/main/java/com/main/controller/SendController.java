package com.main.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendController {

    @PostMapping
    public boolean sendMessage() {
        System.out.println("success");

        return true;
    }
}
