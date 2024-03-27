package org.hits.backend.hackaton.configiration.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.hits.backend.hackaton.core.user.repository.UserRepository;
import org.hits.backend.hackaton.core.user.repository.entity.UserAuthoritiesEntity;
import org.hits.backend.hackaton.core.user.repository.entity.UserEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String HEADER_NAME = "Authorization";
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // Get the token from the header
        var authHeader = request.getHeader(HEADER_NAME);
        if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Cut the prefix from the token and extract the user id
        var jwt = authHeader.substring(BEARER_PREFIX.length());
        var userId = jwtProvider.extractUserIdFromAccessToken(jwt);

        if (StringUtils.isNotEmpty(userId) && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserEntity user = userRepository.findById(UUID.fromString(userId)).orElseThrow();

            // // If the token is valid, authenticate the user
            if (jwtProvider.isAccessTokenValid(jwt, user)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        userRepository.findAuthoritiesByUserId(user.id())
                                .stream()
                                .map(UserAuthoritiesEntity::authority)
                                .map(SimpleGrantedAuthority::new)
                                .toList()
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            }
        }
        filterChain.doFilter(request, response);
    }
}
