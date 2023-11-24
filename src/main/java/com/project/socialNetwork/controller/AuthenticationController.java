package com.project.socialNetwork.controller;

import com.project.socialNetwork.model.request.LoginRequest;
import com.project.socialNetwork.model.request.RegisterRequest;
import com.project.socialNetwork.model.response.BaseResponse;
import com.project.socialNetwork.service.authentication.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse> register(@RequestBody RegisterRequest request) {
        return authenticationService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse> login(@RequestBody LoginRequest request) {
        return authenticationService.login(request);
    }
}
