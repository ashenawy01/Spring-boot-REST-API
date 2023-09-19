package com.sigma.SecureAPI.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/demo")
public class DemoController {

    @GetMapping("/ss")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello Smart Sigma! -> www.smartsigma.tech");
    }
}
