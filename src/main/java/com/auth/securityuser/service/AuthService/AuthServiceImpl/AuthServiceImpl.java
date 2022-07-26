package com.auth.securityuser.service.AuthService.AuthServiceImpl;

import com.auth.securityuser.security.JwtTokenProvide;
import com.auth.securityuser.service.AuthService.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final JwtTokenProvide jwtTokenProvide;

    public AuthServiceImpl(JwtTokenProvide jwtTokenProvide) {
        this.jwtTokenProvide = jwtTokenProvide;
    }

    @Override
    public Boolean verifyToken(String token) {
        return jwtTokenProvide.verifyToken(token);
    }
}
