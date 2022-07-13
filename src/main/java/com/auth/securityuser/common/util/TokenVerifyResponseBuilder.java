package com.auth.securityuser.common.util;

import com.auth.securityuser.responses.TokenVerifyResponse;

public class TokenVerifyResponseBuilder {
    private TokenVerifyResponse tokenVerifyResponse;

    private TokenVerifyResponseBuilder() {
        this.tokenVerifyResponse = new TokenVerifyResponse();
    }

    public static TokenVerifyResponseBuilder getInstance(boolean isValid ) {
        return new TokenVerifyResponseBuilder().setValid(isValid);
    }

    private TokenVerifyResponseBuilder setValid(boolean isValid) {
        if (isValid){
            tokenVerifyResponse.setValid(true);
            tokenVerifyResponse.setMessage("Token valid");
            return this;
        }
        tokenVerifyResponse.setValid(false);
        tokenVerifyResponse.setMessage("No valid");
        return this;
    }

    public TokenVerifyResponse buildTokenVerifyResponse() {
        return tokenVerifyResponse;
    }
}
