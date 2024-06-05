package com.mergeteam.coincontrol.security;

import com.mergeteam.coincontrol.repository.BannedUserIpRepository;
import com.mergeteam.coincontrol.security.rest.RestAuthenticationConfigurer;
import com.mergeteam.coincontrol.security.token.*;
import com.mergeteam.coincontrol.security.util.BannedAddressChecker;
import com.mergeteam.coincontrol.service.LoginService;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import java.text.ParseException;
import java.time.Duration;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final LoginService loginService;
    private static final int BCRYPT_STRENGTH = 12;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   TokenCookieSessionAuthenticationStrategy tokenCookieSessionAuthenticationStrategy,
                                                   TokenCookieAuthenticationConfigurer cookieAuthenticationConfigurer,
                                                   RestAuthenticationConfigurer restAuthenticationConfigurer,
                                                   BannedAddressChecker bannedAddressChecker,
                                                   @Value("${jwt.time-to-live}") Duration tokenTtl)
            throws Exception {
        CookieCsrfTokenRepository cookieCsrfTokenRepository = new CookieCsrfTokenRepository();
        cookieCsrfTokenRepository.setCookieCustomizer(cookie -> cookie.maxAge(tokenTtl.getSeconds()));
        http.formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeHttpRequests ->
                                               authorizeHttpRequests
                                                       .requestMatchers(bannedAddressChecker).denyAll()
                                                       .requestMatchers("/api/v1/login",
                                                                        "/v3/api-docs/**",
                                                                        "/swagger-ui.html",
                                                                        "/swagger-ui/**")
                                                       .permitAll()
                                                       .anyRequest()
                                                       .authenticated())
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .sessionAuthenticationStrategy(tokenCookieSessionAuthenticationStrategy))
                .csrf(csrf -> csrf.csrfTokenRepository(cookieCsrfTokenRepository)
                        .csrfTokenRequestHandler(new CookieCsrfTokenRequestAttributeHandler())
                        .sessionAuthenticationStrategy((authentication, request, response) -> {
                        }));

        http.with(restAuthenticationConfigurer, Customizer.withDefaults());
        http.with(cookieAuthenticationConfigurer, Customizer.withDefaults());
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(BCRYPT_STRENGTH);
    }

    @Bean
    public TokenFactory<Authentication, Token> authenticationTokenTokenFactory() {
        return new DefaultCookieTokenFactory();
    }

    @Bean
    public TokenStringConverter<Token> tokenStringConverter(
            @Value("${jwt.cookie-token-key}") String tokenCookieKey)
            throws ParseException, KeyLengthException {
        return new JweTokenStringConverter(new DirectEncrypter(OctetSequenceKey.parse(tokenCookieKey)),
                                           new DirectDecrypter(OctetSequenceKey.parse(tokenCookieKey)));
    }

}
