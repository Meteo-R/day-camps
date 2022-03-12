package com.mr.daycamps.api.authentication;

import lombok.Getter;

import java.util.List;

@Getter
class LoginResponse {
    private final String token;
    private final String tokenType;
    private final String username;
    private final String email;
    private final List<String> roles;

    private LoginResponse(String token, String tokenType, String username, String email, List<String> roles) {
        this.token = token;
        this.tokenType = tokenType;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    public static LoginResponseBuilder builder() {
        return new LoginResponseBuilder();
    }

    public static class LoginResponseBuilder {
        private String token;
        private String tokenType = "Bearer";
        private String username;
        private String email;
        private List<String> roles;

        LoginResponseBuilder() {
        }

        public LoginResponseBuilder setToken(String token) {
            this.token = token;
            return this;
        }

        public LoginResponseBuilder setTokenType(String tokenType) {
            this.tokenType = tokenType;
            return this;
        }

        public LoginResponseBuilder setUsername(String username) {
            this.username = username;
            return this;
        }

        public LoginResponseBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public LoginResponseBuilder setRoles(List<String> roles) {
            this.roles = roles;
            return this;
        }

        public LoginResponse build() {
            return new LoginResponse(token, tokenType, username, email, roles);
        }

        public String toString() {
            return "LoginResponse.LoginResponseBuilder(token=" + this.token + ", tokenType=" + this.tokenType + ", username=" + this.username + ", email=" + this.email + ", roles=" + this.roles + ")";
        }
    }

}
