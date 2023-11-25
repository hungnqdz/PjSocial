package com.project.socialNetwork.service.authentication;

import com.project.socialNetwork.model.request.LoginRequest;
import com.project.socialNetwork.model.request.RegisterRequest;
import com.project.socialNetwork.model.response.BaseResponse;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    ResponseEntity<BaseResponse> register(RegisterRequest request);

    ResponseEntity<BaseResponse> login(LoginRequest request);
}
