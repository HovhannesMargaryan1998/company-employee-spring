package com.example.companyemployeespring.config;

import com.example.companyemployeespring.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailsService userDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.GET,"/users/add").permitAll()
                .antMatchers(HttpMethod.POST,"/users/add").permitAll()
                .antMatchers(HttpMethod.GET, "/getImage").permitAll()
                .antMatchers(HttpMethod.GET, "/employees/add").hasAuthority(Role.MANAGER.name())
                .antMatchers(HttpMethod.POST, "/employees/add").hasAuthority(Role.MANAGER.name())
                .antMatchers(HttpMethod.GET, "/companies/add").hasAuthority(Role.MANAGER.name())
                .antMatchers(HttpMethod.POST, "/companies/add").hasAuthority(Role.MANAGER.name())
                .antMatchers("/companies/delete").hasAuthority(Role.MANAGER.name())
                .anyRequest().authenticated();


    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService)
                .passwordEncoder(passwordEncoder);
    }
}
