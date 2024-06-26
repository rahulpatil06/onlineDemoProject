//package com.altimetrik.category.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//public class SecurityConfig {
////    @Bean
////    public UserDetailsService userDetailsService(){
////        UserDetails user = User.withUsername("rahul").password("rahul").roles("USER").build();
////        UserDetails admin = User.withUsername("admin").password("admin").roles("ADMIN").build();
////        return new InMemoryUserDetailsManager(user,admin);
////    }
////    @Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////       return http.csrf().disable().authorizeHttpRequests().requestMatchers("/category/allCategories").hasRole("USER").and()
////                .authorizeHttpRequests().requestMatchers("/category/**").hasRole("ADMIN")
////               .requestMatchers(AUTH_WHITELIST).permitAll()
////               .anyRequest().authenticated().and()
////                .formLogin().and().build();
////    }
//    private static String[] AUTH_WHITELIST = {
//            "/api/v1/auth/**",
//            "/v3/api-docs/**",
//            "/v3/api-docs.yml",
//            "/swagger-ui/**",
//            "/swagger-ui.html"};
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails admin = User.builder().username("admin")
//                .password("{noop}admin")
//                .roles("ADMIN","USER")
//                .build();
//        UserDetails user = User.builder().username("rahul")
//                .password("{noop}rahul")
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(admin, user);
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http.csrf().disable().securityMatchers().and()
//                .authorizeHttpRequests(auth -> auth
//                .requestMatchers("/category/allCategories").hasAuthority("USER"))
//                .authorizeHttpRequests().requestMatchers(AUTH_WHITELIST).permitAll().and()
//                .authorizeHttpRequests().requestMatchers("/category/addCategory").hasAuthority("ADMIN").anyRequest().authenticated().and()
//                .formLogin().and().build();
//
//    }
//}
