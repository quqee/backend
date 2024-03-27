package org.hits.backend.hackaton.rest.user.v1;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hits.backend.hackaton.core.user.repository.entity.UserEntity;
import org.hits.backend.hackaton.core.user.service.UserService;
import org.hits.backend.hackaton.public_interface.user.UserDto;
import org.hits.backend.hackaton.rest.user.v1.request.UpdateUserProfileRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/my/profile")
    public UserDto getMyProfile(@AuthenticationPrincipal UserEntity authentication) {
        return userService.getMyProfile(authentication.id());
    }

    @PutMapping("/my/profile")
    public UserDto updateMyProfile(@AuthenticationPrincipal UserEntity authentication, @RequestBody @Valid UpdateUserProfileRequest request) {
        return userService.updateMyProfile(authentication.id(), request);
    }

    @GetMapping("/available")
    public List<UserDto> findConnectedUsers(@RequestParam(required = false, name = "online") Boolean onlineStatus) {
        return userService.findConnectedUsers(onlineStatus);
    }
}
