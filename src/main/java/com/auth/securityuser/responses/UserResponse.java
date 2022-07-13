package com.auth.securityuser.responses;

public class UserResponse {
    private static final String USER_NOT_FOUND = "User not found";
    private Long accountId;
    private String fullName;
    private String username;
    private String email;

    public UserResponse() {
    }

    public UserResponse(Long accountId, String fullName, String username, String email) {
        this.accountId = accountId;
        this.fullName = fullName;
        this.username = username;
        this.email = email;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
