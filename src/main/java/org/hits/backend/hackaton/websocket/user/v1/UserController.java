package org.hits.backend.hackaton.websocket.user.v1;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackaton.core.user.service.UserService;
import org.hits.backend.hackaton.public_interface.user.UserDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @MessageMapping("/user.connectUser")
    @SendTo("/user/public")
    public UserDto addUser(@AuthenticationPrincipal UsernamePasswordAuthenticationToken authenticationToken) {
        return userService.setUserOnline(authenticationToken);
    }

    @MessageMapping("/user.disconnectUser")
    @SendTo("/user/public")
    public UserDto disconnectUser(@AuthenticationPrincipal UsernamePasswordAuthenticationToken authenticationToken) {
        return userService.setUserOffline(authenticationToken);
    }
}
