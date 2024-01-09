package com.project.socialNetwork.model.dto;

import com.project.socialNetwork.model.token.Token;
import com.project.socialNetwork.model.token.TokenType;
import com.project.socialNetwork.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenDTO {
    private String accessToken;
    private User user;
    private boolean expired;
    private boolean revoke;
    private TokenType tokenType;

    public static TokenDTO from(Token token){
        return TokenDTO.builder()
                .tokenType(token.getTokenType())
                .accessToken(token.getToken())
                .user(token.getAccount().getUser())
                .build();
    }
}
