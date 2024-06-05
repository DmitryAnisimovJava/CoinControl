package com.mergeteam.coincontrol.security.util;

import com.mergeteam.coincontrol.repository.BannedUserIpRepository;
import io.hypersistence.utils.hibernate.type.basic.Inet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BannedAddressChecker implements RequestMatcher {

    private final BannedUserIpRepository bannedUserIpRepository;
    private final AntPathRequestMatcher requestMatcher = new AntPathRequestMatcher("/api/v1/login",
                                                                                   HttpMethod.POST.name());

    @Override
    public boolean matches(HttpServletRequest request) {
        if (!requestMatcher.matches(request)) {
            return false;
        }
        return bannedUserIpRepository.findByBannedIp(new Inet(request.getRemoteAddr()))
                .isPresent();
    }
}
