package com.auth.securityuser.services.UserServices.Auth.AuthServiceImpl;

import com.auth.securityuser.security.JwtTokenProvide;
import com.auth.securityuser.services.UserServices.Auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    JwtTokenProvide jwtTokenProvide;


    @Override
    public boolean verifyToken(String token) {
        return jwtTokenProvide.verifyToken(token);
    }
}
