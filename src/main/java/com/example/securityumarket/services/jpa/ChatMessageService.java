package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.ChatMessageDAO;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.models.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageDAO chatMessageDAO;

    private final ChatRoomService chatRoomService;

    public ChatMessage save(ChatMessage chatMessage) {
        var chatId = chatRoomService
                .getChatRoomId(chatMessage.getSenderId(), chatMessage.getRecipientId(), true)
                .orElseThrow(() -> new DataNotFoundException("Chat room"));
        chatMessage.setChatId(chatId);
        chatMessageDAO.save(chatMessage);
        return chatMessage;
    }

    public List<ChatMessage> findChatMessages(Long senderId, Long recipientId) {
        var chatId = chatRoomService.getChatRoomId(senderId, recipientId, false);
        return chatId.map(chatMessageDAO::findByChatId).orElse(Collections.emptyList());
    }
}
