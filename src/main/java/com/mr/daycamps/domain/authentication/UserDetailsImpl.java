package com.mr.daycamps.domain.authentication;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserDetailsImpl implements UserDetails {

    private final String username;
    private final String email;
    private final String phone;
    @JsonIgnore
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final String firstName;
    private final String lastName;
    private final String schoolName;
    private final String schoolAddress;

    public UserDetailsImpl(String username,
                           String email,
                           String phone,
                           String password,
                           Collection<? extends GrantedAuthority> authorities,
                           String firstName,
                           String lastName,
                           String schoolName,
                           String schoolAddress) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.authorities = authorities;
        this.firstName = firstName;
        this.lastName = lastName;
        this.schoolName = schoolName;
        this.schoolAddress = schoolAddress;
    }

    public static UserDetailsImpl build(User user) {
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(user.getRole().name());
        List<GrantedAuthority> authorities = Collections.singletonList(simpleGrantedAuthority);

        return new UserDetailsImpl(
                user.getUsername(),
                user.getEmail(),
                user.getPhone(),
                user.getPassword(),
                authorities,
                Parent.class.equals(user.getClass()) ? ((Parent) user).getFirstName() : null,
                Parent.class.equals(user.getClass()) ? ((Parent) user).getLastName() : null,
                School.class.equals(user.getClass()) ? ((School) user).getName() : null,
                School.class.equals(user.getClass()) ? ((School) user).getAddress() : null);
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public String getSchoolAddress() {
        return schoolAddress;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
