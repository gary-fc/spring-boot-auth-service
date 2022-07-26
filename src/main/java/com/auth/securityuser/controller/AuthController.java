package com.auth.securityuser.controller;

import com.auth.securityuser.common.responses.AuthResponse;
import com.auth.securityuser.common.responses.TokenVerifyResponse;
import com.auth.securityuser.common.responses.UserResponse;
import com.auth.securityuser.common.util.TokenVerifyResponseBuilder;
import com.auth.securityuser.common.util.UserResponseBuilder;
import com.auth.securityuser.domain.User;
import com.auth.securityuser.dto.CredentialsDTO;
import com.auth.securityuser.dto.JwtDTO;
import com.auth.securityuser.dto.UserDTO;
import com.auth.securityuser.exceptions.HttpResponseException;
import com.auth.securityuser.security.JwtTokenProvide;
import com.auth.securityuser.service.UserService.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api-user/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final JwtTokenProvide jwtTokenProvide;


    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody CredentialsDTO credentialsDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentialsDTO.getEmail(), credentialsDTO.getPassword()));
        User user = userService.findByEmail(credentialsDTO.getEmail());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvide.createToken(authentication, user);
        return new AuthResponse(token);
    }

    @PostMapping("/register")
    public UserResponse registerUser(@Valid @RequestBody UserDTO userDTO) throws Exception {
        if (userService.findByUsername(userDTO.getUsername()) != null) {
            throw new HttpResponseException(HttpStatus.BAD_REQUEST, "Username exist");
        }
        if (userService.findByEmail(userDTO.getEmail()) != null) {
            throw new HttpResponseException(HttpStatus.BAD_REQUEST, "Email exist");
        }
        if (userService.findByAccountId(userDTO.getAccountId()) != null) {
            throw new HttpResponseException(HttpStatus.BAD_REQUEST, "AccountId exist");
        }
        return UserResponseBuilder.getInstance(userService.createUser(userDTO)).buildUserResponse();
    }

    @PostMapping("/verifyToken")
    public ResponseEntity<TokenVerifyResponse> verifyToken(@Valid @RequestBody JwtDTO jwtDTO) {
        if (jwtTokenProvide.verifyToken(jwtDTO.getAccessToken())) {
            return ResponseEntity.status(200).body(TokenVerifyResponseBuilder.getInstance(jwtTokenProvide.verifyToken(jwtDTO.getAccessToken())).buildTokenVerifyResponse());
        }
        return ResponseEntity.status(401).body(TokenVerifyResponseBuilder.getInstance(jwtTokenProvide.verifyToken(jwtDTO.getAccessToken())).buildTokenVerifyResponse());
    }
}
