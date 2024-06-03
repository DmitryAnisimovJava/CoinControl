package com.mergeteam.coincontrol.security.token;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@EqualsAndHashCode(callSuper = true)
public class TokenUser extends User {

    private final Token token;
    @Builder(builderMethodName = "builderWithToken")
    public TokenUser(String username, String password, boolean enabled, boolean accountNonExpired,
                     boolean credentialsNonExpired, boolean accountNonLocked,
                     Collection<? extends GrantedAuthority> authorities, Token token) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.token = token;
    }

    @Builder(builderMethodName = "relaxedBuilderWithToken", builderClassName = "RelaxedBuilder")
    public TokenUser(String username, String password, Collection<? extends GrantedAuthority> authorities, Token token) {
        super(username, password, authorities);
        this.token = token;
    }
}
