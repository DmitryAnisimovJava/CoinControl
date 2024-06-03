package com.mergeteam.coincontrol.service;

import com.mergeteam.coincontrol.repository.ExpiredTokenRepository;
import com.mergeteam.coincontrol.security.token.Token;
import com.mergeteam.coincontrol.security.token.TokenUser;
import com.mergeteam.coincontrol.util.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.Instant;

@RequiredArgsConstructor
@Service
public class TokenAuthUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    private final ExpiredTokenRepository expiredTokenRepository;
    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken authenticationToken) throws UsernameNotFoundException {
        if (authenticationToken.getPrincipal() instanceof Token token) {
            return TokenUser.builderWithToken()
                    .username(token.getSubject())
                    .accountNonExpired(true)
                    .enabled(true)
                    .accountNonLocked(true)
                    .credentialsNonExpired(checkTokenCredentials(token))
                    .password("nopassword")
                    .authorities(token.getAuthorities().stream()
                                         .map(SimpleGrantedAuthority::new)
                                         .toList())
                    .token(token)
                    .build();

        }
        throw new UserNotFoundException();
    }

    private boolean checkTokenCredentials(Token token) {
        return expiredTokenRepository.findById(token.getId())
                .map(expiredToken -> false)
                .orElse(token.getExpiresAt().isAfter(Instant.now()));
    }
}
