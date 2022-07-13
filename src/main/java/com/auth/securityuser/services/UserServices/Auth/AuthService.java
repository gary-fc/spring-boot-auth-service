package com.auth.securityuser.services.UserServices.Auth;

public interface AuthService {

    boolean verifyToken(String token);
}
