package com.auth.securityuser.common.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private Long accountId;
    private String fullName;
    private String username;
    private String email;
}
