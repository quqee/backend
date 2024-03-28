package org.hits.backend.hackaton.core.auth;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hits.backend.hackaton.core.user.repository.UserRepository;
import org.hits.backend.hackaton.core.user.repository.entity.UserAuthoritiesEntity;
import org.hits.backend.hackaton.core.user.repository.entity.UserEntity;
import org.hits.backend.hackaton.core.user.service.UserService;
import org.hits.backend.hackaton.public_interface.auth.LoginDto;
import org.hits.backend.hackaton.public_interface.auth.RefreshTokenDto;
import org.hits.backend.hackaton.public_interface.auth.TokenGenerationData;
import org.hits.backend.hackaton.public_interface.exception.ExceptionInApplication;
import org.hits.backend.hackaton.public_interface.exception.ExceptionType;
import org.hits.backend.hackaton.public_interface.user.CreateUserDto;
import org.hits.backend.hackaton.rest.auth.v1.response.JwtResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public JwtResponse signIn(@NonNull LoginDto dto) {
        UserEntity user = userRepository.findByEmail(dto.email())
                .orElseThrow(() -> new ExceptionInApplication("Invalid email or password", ExceptionType.UNAUTHORIZED));

        if (!userService.checkPassword(dto.password(), user.password())) {
            throw new ExceptionInApplication("Invalid email or password", ExceptionType.UNAUTHORIZED);
        }

        var userData = new TokenGenerationData(
                user.id(),
                userRepository.findAuthoritiesByUserId(user.id())
                        .stream()
                        .map(UserAuthoritiesEntity::authority)
                        .collect(Collectors.toSet())
        );
        return tokenService.createTokens(userData);
    }

    public JwtResponse refresh(@NonNull RefreshTokenDto dto) {
        tokenService.checkRefreshToken(dto.refreshToken());

        final String tokenId = tokenService.getTokenIdInRefreshToken(dto.refreshToken());
        tokenService.deleteRefreshToken(tokenId);

        final String userId = tokenService.getUserIdInRefreshToken(dto.refreshToken());
        final UserEntity user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ExceptionInApplication("User not found", ExceptionType.NOT_FOUND));
        var tokenGenerationData = new TokenGenerationData(
                user.id(),
                userRepository.findAuthoritiesByUserId(user.id())
                        .stream()
                        .map(UserAuthoritiesEntity::authority)
                        .collect(Collectors.toSet())
        );

        return tokenService.createTokens(tokenGenerationData);
    }

    public void logout(@NonNull RefreshTokenDto dto) {
        tokenService.checkRefreshToken(dto.refreshToken());
        final String tokenId = tokenService.getTokenIdInRefreshToken(dto.refreshToken());
        tokenService.deleteRefreshToken(tokenId);
    }
}

