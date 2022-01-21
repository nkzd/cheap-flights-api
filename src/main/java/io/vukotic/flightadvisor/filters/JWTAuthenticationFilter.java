package io.vukotic.flightadvisor.filters;

import io.vukotic.flightadvisor.service.JWTTokenService;
import io.vukotic.flightadvisor.error.exception.JWTException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JWTTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        var authorizationHeader = httpServletRequest.getHeader("Authorization");

        if (authorizationHeader == null
                || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        try {
            var auth = jwtTokenService.parseJwtToken(authorizationHeader.replace("Bearer ", ""));
            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch (JWTException jwtException) {
            httpServletResponse.setStatus(400);
            httpServletResponse.getWriter().write("Invalid Authorization Token");
        }

    }
}
