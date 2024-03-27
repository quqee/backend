package org.hits.backend.hackaton.configiration.websocket;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hits.backend.hackaton.core.user.repository.UserRepository;
import org.hits.backend.hackaton.core.user.repository.entity.UserAuthoritiesEntity;
import org.hits.backend.hackaton.core.user.repository.entity.UserEntity;
import org.hits.backend.hackaton.public_interface.exception.ExceptionInApplication;
import org.hits.backend.hackaton.public_interface.exception.ExceptionType;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebSocketAuthenticationFilter implements ChannelInterceptor {
    private final UserRepository userRepository;

    @Override
    public Message<?> preSend(@NonNull Message<?> message, @NonNull MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            Object simpUser = accessor.getMessageHeaders().get("simpUser");

            if (simpUser == null || !(simpUser instanceof UsernamePasswordAuthenticationToken)) {
                throw new ExceptionInApplication("User not authenticated", ExceptionType.UNAUTHORIZED);
            }

            UsernamePasswordAuthenticationToken userToken =
                    (UsernamePasswordAuthenticationToken) accessor.getMessageHeaders().get("simpUser");

            if (userToken == null || !(userToken.getPrincipal() instanceof UserEntity)) {
                throw new ExceptionInApplication("User not authenticated", ExceptionType.UNAUTHORIZED);
            }

            var userId = ((UserEntity) userToken.getPrincipal()).id();

            var userEntity = userRepository.findById(userId)
                    .orElseThrow(() -> new ExceptionInApplication("User not found", ExceptionType.NOT_FOUND));

            final UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(
                    userEntity,
                    null,
                    userRepository.findAuthoritiesByUserId(userEntity.id())
                            .stream()
                            .map(UserAuthoritiesEntity::authority)
                            .map(SimpleGrantedAuthority::new)
                            .toList()
            );

            accessor.setUser(user);
        }

        return message;
    }
}
