package com.auth.securityuser.common.util;

import com.auth.securityuser.common.responses.UserResponse;
import com.auth.securityuser.domain.User;

public class UserResponseBuilder {
    private UserResponse userResponse;

    private UserResponseBuilder() {
        this.userResponse = new UserResponse();
    }

    public static UserResponseBuilder getInstance(User user) {
        return new UserResponseBuilder().setUser(user);
    }

    private UserResponseBuilder setUser(User user) {
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        userResponse.setFullName(user.getFullName());
        userResponse.setAccountId(user.getAccountId());
        return this;
    }

    public UserResponse buildUserResponse() {
        return userResponse;
    }
}

