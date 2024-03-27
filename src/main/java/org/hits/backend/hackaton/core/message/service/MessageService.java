package org.hits.backend.hackaton.core.message.service;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackaton.core.chat.service.ChatRoomService;
import org.hits.backend.hackaton.core.message.repository.MessageRepository;
import org.hits.backend.hackaton.core.message.repository.entity.MessageEntity;
import org.hits.backend.hackaton.core.user.repository.UserRepository;
import org.hits.backend.hackaton.core.user.repository.entity.UserEntity;
import org.hits.backend.hackaton.public_interface.common.PageResponse;
import org.hits.backend.hackaton.public_interface.exception.ExceptionInApplication;
import org.hits.backend.hackaton.public_interface.exception.ExceptionType;
import org.hits.backend.hackaton.rest.message.v1.response.MessageDto;
import org.hits.backend.hackaton.websocket.chat.v1.RequestMessageDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository repository;
    private final UserRepository userRepository;
    private final ChatRoomService chatRoomService;

    public MessageEntity save(RequestMessageDto message, UUID senderId) {
        var user = userRepository.findById(senderId)
                .orElseThrow(() -> new ExceptionInApplication("User not found", ExceptionType.NOT_FOUND));
        var chatId = chatRoomService.getChatRoomId(user.id(), message.recipientId());

        var entity = MessageEntity.builder()
                .senderId(user.id())
                .recipientId(message.recipientId())
                .content(message.content())
                .createdAt(OffsetDateTime.now())
                .chatRoomId(chatId)
                .build();

        return repository.save(entity);
    }

    public PageResponse<MessageDto> findChatMessages(String senderId, UserEntity user, PageRequest pageRequest) {
        checkIfUserIdIsNull(senderId);
        checkIfUserIdIsNull(String.valueOf(user.id()));

        var senderIdUUID = validateUUID(senderId);

        var chatId = chatRoomService.getChatRoomId(senderIdUUID, user.id());
        var messagesEntities = repository.findChatMessages(chatId, pageRequest);
        var totalElements = repository.countChatMessages(chatId);

        var messagesDto = convertToDto(messagesEntities, user);

        var metadata = new PageResponse.Metadata(pageRequest.getPageNumber(), pageRequest.getPageSize(), totalElements);
        return new PageResponse<>(messagesDto, metadata);
    }

    private void checkIfUserIdIsNull(String userId) {
        if (userId == null) {
            throw new ExceptionInApplication("User id cannot be null", ExceptionType.INVALID);
        }
    }

    private UUID validateUUID(String uuid) {
        try {
            return UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            throw new ExceptionInApplication("Invalid UUID", ExceptionType.INVALID);
        }
    }

    private List<MessageDto> convertToDto(List<MessageEntity> messageEntities, UserEntity user) {
        return messageEntities.stream()
                .map(entity -> MessageDto.builder()
                        .messageId(entity.messageId())
                        .chatRoomId(entity.chatRoomId())
                        .senderId(entity.senderId())
                        .recipientId(entity.recipientId())
                        .content(entity.content())
                        .createdAt(entity.createdAt())
                        .myMessage(entity.senderId().equals(user.id()))
                        .build())
                .collect(Collectors.toList());
    }
}
