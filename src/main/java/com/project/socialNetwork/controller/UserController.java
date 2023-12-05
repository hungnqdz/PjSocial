package com.project.socialNetwork.controller;

import com.project.socialNetwork.model.request.ChangePasswordRequest;
import com.project.socialNetwork.model.response.BaseResponse;
import com.project.socialNetwork.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/change_password")
    public ResponseEntity<BaseResponse> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest,
                                                       Principal currentAccount) {
        return userService.changePassword(changePasswordRequest, currentAccount);
    }
}
