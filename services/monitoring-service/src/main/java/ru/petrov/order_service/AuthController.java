package ru.petrov.order_service;

import org.springframework.resilience.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
@Retryable
public class AuthController {
    @GetMapping("/login")
    public String work(){
        return "Work";
    }

    @GetMapping("/internal")
    public String internal(){
        return " Not Work";
    }
}

