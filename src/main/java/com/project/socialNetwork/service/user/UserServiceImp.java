package com.project.socialNetwork.service.user;

import com.project.socialNetwork.model.account.Account;
import com.project.socialNetwork.model.request.ChangePasswordRequest;
import com.project.socialNetwork.model.response.BaseResponse;
import com.project.socialNetwork.repository.AccountRepository;
import com.project.socialNetwork.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    @Override
    public ResponseEntity<BaseResponse> changePassword(ChangePasswordRequest changePasswordRequest, Principal currentAccount) {
        Account account = (Account) ((UsernamePasswordAuthenticationToken) currentAccount).getPrincipal();
        if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(),account.getPassword())) {
            return ResponseEntity.badRequest().body(
                    BaseResponse.builder()
                            .statusCode(400)
                            .message("Invalid password")
                            .data("")
                            .build()
            );
        }
        if (passwordEncoder.matches(changePasswordRequest.getNewPassword(),account.getPassword())) {
            return ResponseEntity.badRequest().body(
                    BaseResponse.builder()
                            .statusCode(400)
                            .message("New password must not similar to old password")
                            .data("")
                            .build()
            );
        }
        account.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        accountRepository.save(account);
        return ResponseEntity.badRequest().body(
                BaseResponse.builder()
                        .statusCode(200)
                        .message("Change password successfully")
                        .data("")
                        .build()
        );
    }


}
