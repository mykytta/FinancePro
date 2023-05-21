package com.mykyta.stockapi.config;

import com.mykyta.stockapi.security.CustomUserDetailsService;
import com.mykyta.stockapi.security.RestAuthenticationEntryPoint;
import com.mykyta.stockapi.security.TokenAuthenticationFilter;
import com.mykyta.stockapi.security.oauth2.CustomOAuth2UserService;
import com.mykyta.stockapi.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.mykyta.stockapi.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.mykyta.stockapi.security.oauth2.OAuth2AuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
@RequiredArgsConstructor
public class SecurityConfig{

    private final CustomUserDetailsService customUserDetailsService;

    private final CustomOAuth2UserService customOAuth2UserService;

    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    private final TokenAuthenticationFilter tokenAuthenticationFilter;

    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
         http
                 .cors()
                 .and()
                 .sessionManagement()
                 .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                 .and()
                 .csrf().disable()
                 .formLogin().disable()
                 .httpBasic().disable()
                 .exceptionHandling().authenticationEntryPoint(new RestAuthenticationEntryPoint())
                 .and()
                 .authorizeHttpRequests()
                 .requestMatchers("/",
                         "/**")
                 .permitAll()
                 .requestMatchers("/auth/**", "/oauth2/**")
                 .permitAll()
                 .anyRequest()
                 .authenticated()
                 .and()
                 .oauth2Login()
                 .authorizationEndpoint()
                 .baseUri("/oauth2/authorize")
                 .authorizationRequestRepository(cookieAuthorizationRequestRepository())
                 .and()
                 .redirectionEndpoint()
                 .baseUri("/oauth2/callback/*")
                 .and()
                 .userInfoEndpoint()
                 .userService(customOAuth2UserService)
                 .and()
                 .successHandler(oAuth2AuthenticationSuccessHandler)
                 .failureHandler(oAuth2AuthenticationFailureHandler);

        http.addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
