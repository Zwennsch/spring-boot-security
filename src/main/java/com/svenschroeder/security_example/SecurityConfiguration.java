package com.svenschroeder.security_example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.svenschroeder.security_example.model.MyUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private MyUserDetailService userDetailService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers("/home", "/register/**").permitAll();
                    registry.requestMatchers("/admin/**").hasRole("ADMIN");
                    registry.requestMatchers("/user/**").hasRole("USER");
                    registry.anyRequest().authenticated();
                }).formLogin(formLogin -> formLogin.permitAll()) // by providing a SecurityFilterChain, the normal
                                                                 // Login-Form
                                                                 // gets removed. That's why I have to manually add it
                                                                 // here, By
                                                                 // default there will also be a /logout page
                .build();
    }

    // This was just for in memory usage and testing. No longer needed in real
    // databases like MySql

    // @Bean
    // public UserDetailsService userDetailsService() {
    // UserDetails normalUser = User.builder()
    // .username("Sven")
    // .password("$2a$12$r7tlxcti0WpN/uqTkSQo1.Fx0W4PVCZYXJW9voJK6LKEd5JAXPbcW") //
    // this was generated using an
    // // online bcrypt generator
    // // with password 1234
    // .roles("USER")
    // .build();
    // UserDetails adminUser = User.builder()
    // .username("admin")
    // .password("$2a$12$6IiTc6n0OmCOP8gfJmIw6eQOJ01aHpMhfmcYwwVfIicikSp7JEJ9G") //
    // this was generated using an
    // // online bcrypt generator
    // // with password admin
    // .roles("ADMIN", "USER")
    // .build();

    // return new InMemoryUserDetailsManager(normalUser, adminUser);
    // }

    @Bean
    public UserDetailsService userDetailsService() {
        return userDetailService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        // This is for loading users from the database or any other access to the
        // database and use it for authentication
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
