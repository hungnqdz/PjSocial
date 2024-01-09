package com.project.socialNetwork.service.authentication;

import com.project.socialNetwork.model.response.BaseResponse;
import com.project.socialNetwork.repository.TokenRepository;
import com.project.socialNetwork.service.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;

    @SneakyThrows
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            BaseResponse.sendUnauthorized(response, "Unauthorized");
            return;
        }
        String token = header.substring(7);
        var storeToken = tokenRepository.findByToken(token).orElse(null);
        if (storeToken == null) BaseResponse.sendUnauthorized(response, "Unauthorized");
        else {
            tokenRepository.delete(storeToken);
        }
        SecurityContextHolder.clearContext();
        BaseResponse.sendOk(response, "Logout successfully");
    }
}
