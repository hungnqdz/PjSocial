package com.project.socialNetwork.service.user;

import com.project.socialNetwork.model.request.ChangePasswordRequest;
import com.project.socialNetwork.model.response.BaseResponse;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

public interface UserService {
    ResponseEntity<BaseResponse> changePassword(ChangePasswordRequest changePasswordRequest, Principal user);
}
