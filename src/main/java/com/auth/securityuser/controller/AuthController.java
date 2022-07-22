package com.auth.securityuser.controller;

import com.auth.securityuser.common.util.TokenVerifyResponseBuilder;
import com.auth.securityuser.common.util.UserResponseBuilder;
import com.auth.securityuser.domain.Role;
import com.auth.securityuser.domain.User;
import com.auth.securityuser.dto.CredentialsDTO;
import com.auth.securityuser.dto.JwtDTO;
import com.auth.securityuser.dto.UserDTO;
import com.auth.securityuser.exceptions.HttpResponseException;
import com.auth.securityuser.repository.RoleRepository;
import com.auth.securityuser.repository.UserRepository;
import com.auth.securityuser.responses.AuthResponse;
import com.auth.securityuser.responses.TokenVerifyResponse;
import com.auth.securityuser.responses.UserResponse;
import com.auth.securityuser.security.JwtTokenProvide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Date;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository rolRepository;

    @Autowired
    private JwtTokenProvide jwtTokenProvide;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @CrossOrigin("*")
    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody CredentialsDTO credentialsDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentialsDTO.getEmail(), credentialsDTO.getPassword()));
        User user = new User();
        user = userRepository.findByEmail(credentialsDTO.getEmail()).get();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvide.createToken(authentication, user);
        return new AuthResponse(token);
    }

    @CrossOrigin("*")
    @PostMapping("/register")
    public UserResponse registerUser(@Valid @RequestBody UserDTO userDTO) throws Exception {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new HttpResponseException(HttpStatus.BAD_REQUEST, "Username exist");
        }

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new HttpResponseException(HttpStatus.BAD_REQUEST, "Email exist");
        }

        if (userRepository.existsByAccountId(userDTO.getAccountId())) {
            throw new HttpResponseException(HttpStatus.BAD_REQUEST, "Email exist");
        }

        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = this.composeUser(userDTO);

        Role roles = rolRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));
        return UserResponseBuilder.getInstance(userRepository.save(user)).buildUserResponse();
    }

    @CrossOrigin("*")
    @PostMapping("/verifyToken")
    public ResponseEntity<TokenVerifyResponse> verifyToken(@Valid @RequestBody JwtDTO jwtDTO){
        if (jwtTokenProvide.verifyToken(jwtDTO.getAccessToken())){
            return ResponseEntity.status(200).body(TokenVerifyResponseBuilder.getInstance(jwtTokenProvide.verifyToken(jwtDTO.getAccessToken())).buildTokenVerifyResponse());
        }
        return ResponseEntity.status(401).body(TokenVerifyResponseBuilder.getInstance(jwtTokenProvide.verifyToken(jwtDTO.getAccessToken())).buildTokenVerifyResponse());
    }

    private User composeUser(UserDTO userDTO) {
        User user = new User();
        user.setAccountId(userDTO.getAccountId());
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setCreatedDate(new Date());
        user.setIsDeleted(false);
        return user;
    }
}
