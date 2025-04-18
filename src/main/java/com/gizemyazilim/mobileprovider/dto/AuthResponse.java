package com.gizemyazilim.mobileprovider.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private Long subscriberId;
    public AuthResponse() {}

    public AuthResponse(String token,Long subscriberId) {
        this.token = token;
        this.subscriberId = subscriberId;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public Long getSubscriberId() { return subscriberId; }
    public void setSubscriberId(Long subscriberId) { this.subscriberId = subscriberId; }
}

