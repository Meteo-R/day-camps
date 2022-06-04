package com.mr.daycamps.application.configuration.security;

import com.mr.daycamps.application.configuration.security.jwt.AuthenticationEntryPointJwt;
import com.mr.daycamps.domain.authentication.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        // securedEnabled = true,
        // jsr250Enabled = true,
        prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationEntryPointJwt authenticationEntryPointJwt;

    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, AuthenticationEntryPointJwt authenticationEntryPointJwt) {
        this.userDetailsService = userDetailsService;
        this.authenticationEntryPointJwt = authenticationEntryPointJwt;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPointJwt).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/api/authentication/**").permitAll()
                .antMatchers("/api/test/**").permitAll()
                .antMatchers("/api/parent/**").permitAll()
                .antMatchers("/api/school/**").permitAll()
                .antMatchers("/api/enrollments/**").permitAll()
                .antMatchers("/api/daycamps/**").permitAll()
                .anyRequest().authenticated();
    }
}
