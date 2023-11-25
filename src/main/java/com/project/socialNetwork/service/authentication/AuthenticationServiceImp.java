package com.project.socialNetwork.service.authentication;

import com.project.socialNetwork.model.account.Account;
import com.project.socialNetwork.model.account.Role;
import com.project.socialNetwork.model.request.LoginRequest;
import com.project.socialNetwork.model.request.RegisterRequest;
import com.project.socialNetwork.model.response.Token;
import com.project.socialNetwork.model.response.BaseResponse;
import com.project.socialNetwork.model.user.User;
import com.project.socialNetwork.repository.AccountRepository;
import com.project.socialNetwork.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<BaseResponse> register(RegisterRequest request) {
        try {
            Account account = Account.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .user(User.builder().userName(request.getUserName()).build())
                    .role(Role.USER)
                    .build();
            accountRepository.save(account);
            String jwtToken = jwtService.generateToken(account);
            return ResponseEntity.ok().body(BaseResponse.builder()
                    .statusCode(200)
                    .message("register successfully")
                    .data(Token.builder().token(jwtToken).build())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    BaseResponse.builder()
                            .statusCode(400)
                            .message("Registration failed")
                            .build()
            );
        }
    }


    @Override
    public ResponseEntity<BaseResponse> login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            Account account = accountRepository.findByEmail(request.getEmail()).orElseThrow();
            String jwtToken = jwtService.generateToken(account);
            return ResponseEntity.ok(BaseResponse.builder()
                    .statusCode(200)
                    .message("login successfully")
                    .data(Token.builder().token(jwtToken).build())
                    .build());
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body(
                    BaseResponse.builder()
                            .statusCode(400)
                            .message("Invalid email or password")
                            .data("")
                            .build()
            );
        }
    }

}
