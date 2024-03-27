package org.hits.backend.hackaton.rest.message.v1;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackaton.core.message.service.MessageService;
import org.hits.backend.hackaton.core.user.repository.entity.UserEntity;
import org.hits.backend.hackaton.public_interface.common.PageResponse;
import org.hits.backend.hackaton.rest.message.v1.response.MessageDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/v1/messages")
public class MessageController {
    private final MessageService messageService;

    @GetMapping("/{senderId}")
    public PageResponse<MessageDto> findChatMessages(@PathVariable String senderId,
                                                     @RequestParam(required = false, defaultValue = "0") int page,
                                                     @RequestParam(required = false, defaultValue = "10") int size,
                                                     @AuthenticationPrincipal UserEntity authentication) {
        PageRequest pageable = PageRequest.of(page, size);
        return messageService.findChatMessages(senderId, authentication, pageable);
    }
}
