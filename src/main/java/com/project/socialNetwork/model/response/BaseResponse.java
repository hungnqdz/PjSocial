package com.project.socialNetwork.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.ErrorResponse;

import java.io.IOException;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonFormat
public class BaseResponse {
    private int statusCode;
    private String message;
    private Object data;
    public byte[] toRestResponseBytes() throws IOException {
        String serialized = new ObjectMapper().writeValueAsString(this);
        return serialized.getBytes();
    }

    public static void sendUnauthorized(HttpServletResponse response,String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader("Content-Type", "application/json");
        response.getOutputStream().write(BaseResponse.builder().statusCode(401)
                .message(message).build().toRestResponseBytes());
    }

    public static void sendOk(HttpServletResponse response,String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader("Content-Type", "application/json");
        response.getOutputStream().write(BaseResponse.builder().statusCode(200)
                .message(message).build().toRestResponseBytes());
    }

}
