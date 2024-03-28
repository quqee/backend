package org.hits.backend.hackaton.rest.auth.v1;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hits.backend.hackaton.core.auth.AuthenticationService;
import org.hits.backend.hackaton.public_interface.auth.LoginDto;
import org.hits.backend.hackaton.public_interface.auth.RefreshTokenDto;
import org.hits.backend.hackaton.public_interface.user.CreateUserDto;
import org.hits.backend.hackaton.rest.auth.v1.request.CreateUserRequest;
import org.hits.backend.hackaton.rest.auth.v1.request.LoginRequest;
import org.hits.backend.hackaton.rest.auth.v1.request.RefreshTokenRequest;
import org.hits.backend.hackaton.rest.auth.v1.response.JwtResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public JwtResponse signIn(@RequestBody @Valid LoginRequest request) {
        var dto = new LoginDto(
                request.email(),
                request.password()
        );
        return authenticationService.signIn(dto);
    }

    @PostMapping("/refresh")
    public JwtResponse refresh(@RequestBody RefreshTokenRequest request) {
        var dto = new RefreshTokenDto(
                request.refreshToken()
        );
        return authenticationService.refresh(dto);
    }

    @PostMapping("/logout")
    public void signOut(@RequestBody RefreshTokenRequest request) {
        var dto = new RefreshTokenDto(
                request.refreshToken()
        );
        authenticationService.logout(dto);
    }
}
