package com.mr.daycamps.api.authentication;

import lombok.Getter;

import java.util.List;

@Getter
class LoginResponse {
    private final String token;
    private final String tokenType;
    private final String username;
    private final String email;
    private final String phone;
    private final List<String> roles;
    private final String firstName;
    private final String lastName;
    private final String schoolName;
    private final String schoolAddress;

    private LoginResponse(String token,
                          String tokenType,
                          String username,
                          String email,
                          String phone,
                          List<String> roles,
                          String firstName,
                          String lastName,
                          String schoolName,
                          String schoolAddress) {
        this.token = token;
        this.tokenType = tokenType;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.roles = roles;
        this.firstName = firstName;
        this.lastName = lastName;
        this.schoolName = schoolName;
        this.schoolAddress = schoolAddress;
    }

    public static LoginResponseBuilder builder() {
        return new LoginResponseBuilder();
    }

    public static class LoginResponseBuilder {
        private String token;
        private String tokenType = "Bearer";
        private String username;
        private String email;
        private String phone;
        private List<String> roles;
        private String firstName;
        private String lastName;
        private String schoolName;
        private String schoolAddress;

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

        public LoginResponseBuilder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public LoginResponseBuilder setRoles(List<String> roles) {
            this.roles = roles;
            return this;
        }

        public LoginResponseBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public LoginResponseBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public LoginResponseBuilder setSchoolName(String schoolName) {
            this.schoolName = schoolName;
            return this;
        }

        public LoginResponseBuilder setSchoolAddress(String schoolAddress) {
            this.schoolAddress = schoolAddress;
            return this;
        }

        public LoginResponse build() {
            return new LoginResponse(token, tokenType, username, email, phone, roles, firstName, lastName, schoolName, schoolAddress);
        }

        public String toString() {
            return "LoginResponse.LoginResponseBuilder(" +
                    "token=" + this.token + ", " +
                    "tokenType=" + this.tokenType + ", " +
                    "username=" + this.username + ", " +
                    "email=" + this.email + ", " +
                    "roles=" + this.roles + ", " +
                    "firstName=" + this.firstName + ", " +
                    "lastName=" + this.lastName + ", " +
                    "schoolName=" + this.schoolName + ", " +
                    "schoolAddress=" + this.schoolAddress + ")";
        }
    }

}
