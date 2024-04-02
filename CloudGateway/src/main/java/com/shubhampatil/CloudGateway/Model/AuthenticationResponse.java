package com.shubhampatil.CloudGateway.Model;

import java.util.Collection;

public class AuthenticationResponse {
    private String userId;
    private String accessToken;
    private String refreshToken;
    private long expiresAt;
    private Collection<String> authorityList;
}
