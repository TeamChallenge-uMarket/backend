package com.example.securityumarket.services.chat;

import com.example.securityumarket.dto.chat.ChatNotification;
import com.example.securityumarket.models.ChatMessage;
import com.example.securityumarket.models.Users;
import com.example.securityumarket.services.jpa.ChatMessageService;
import com.example.securityumarket.services.jpa.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final UserService userService;

    private final SimpMessagingTemplate messagingTemplate;

    private final ChatMessageService chatMessageService;


    public void addUser(Users users) {
        userService.setUserStatusOnline(users);
    }

    public void disconnect(Users user) {
        userService.setUserStatusOffline(user);
    }

    public List<Users> findConnectedUsers() {
        return userService.findAllByStatus(Users.Status.ONLINE);
    }

    public void processMessage(ChatMessage chatMessage) {
        ChatMessage savedMsg = chatMessageService.save(chatMessage);
        messagingTemplate.convertAndSendToUser(
                String.valueOf(chatMessage.getRecipientId()), "/queue/messages",
                new ChatNotification(
                        savedMsg.getId(),
                        savedMsg.getSenderId(),
                        savedMsg.getRecipientId(),
                        savedMsg.getCarId(),
                        savedMsg.getContent()
                )
        );
    }

    public List<ChatMessage> findChatMessages(Long senderId, Long recipientId, Long carId) {
        return chatMessageService.findChatMessages(senderId, recipientId, carId);
    }
}
