package com.project.socialNetwork.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/home")
@AllArgsConstructor
public class HomeController {
    @GetMapping("/page")
    String test(Principal user){
        return user.toString();
    }
}
