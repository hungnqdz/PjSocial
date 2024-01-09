package com.project.socialNetwork.service.authentication;

import com.project.socialNetwork.model.account.Account;
import com.project.socialNetwork.model.account.Role;
import com.project.socialNetwork.model.dto.TokenDTO;
import com.project.socialNetwork.model.request.LoginRequest;
import com.project.socialNetwork.model.request.RegisterRequest;
import com.project.socialNetwork.model.token.Token;
import com.project.socialNetwork.model.response.BaseResponse;
import com.project.socialNetwork.model.token.TokenType;
import com.project.socialNetwork.model.user.User;
import com.project.socialNetwork.repository.AccountRepository;
import com.project.socialNetwork.repository.TokenRepository;
import com.project.socialNetwork.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final TokenRepository tokenRepository;

    @Override
    public ResponseEntity<BaseResponse> register(RegisterRequest request) {
        try {
            Account account = Account.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .user(User.builder().userName(request.getUserName()).build())
                    .role(Role.USER)
                    .build();
            String jwtToken = jwtService.generateToken(account);
            account = accountRepository.save(account);
            Token token = Token.builder()
                    .token(jwtToken)
                    .account(account)
                    .tokenType(TokenType.BEARER)
                    .build();
            tokenRepository.save(token);
            return ResponseEntity.ok().body(BaseResponse.builder()
                    .statusCode(200)
                    .message("Registration successfully")
                    .data(TokenDTO.from(token))
                    .build());
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(
                    BaseResponse.builder()
                            .statusCode(400)
                            .message("Email already in use")
                            .build()
            );
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(
                    BaseResponse.builder()
                            .statusCode(500)
                            .message("Registration failed")
                            .build());
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
            Token token = Token.builder()
                    .token(jwtToken)
                    .account(account)
                    .tokenType(TokenType.BEARER)
                    .build();
            var oldToken = tokenRepository.findTokenByUser(account.getId()).orElse(null);
            if (oldToken != null){
                tokenRepository.delete(oldToken);
            }
            tokenRepository.save(token);
            return ResponseEntity.ok(BaseResponse.builder()
                    .statusCode(200)
                    .message("login successfully")
                    .data(TokenDTO.from(token))
                    .build());
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body(
                    BaseResponse.builder()
                            .statusCode(400)
                            .message("Invalid email or password")
                            .build()
            );
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(
                    BaseResponse.builder()
                            .statusCode(500)
                            .message("Login failed")
                            .build());
        }
    }


}
