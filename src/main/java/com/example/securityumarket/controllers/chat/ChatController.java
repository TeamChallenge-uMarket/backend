package com.example.securityumarket.controllers.chat;


import com.example.securityumarket.models.ChatMessage;
import com.example.securityumarket.models.Users;
import com.example.securityumarket.services.chat.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/user.addUser")
    @SendTo("/user/public")
    public Users addUser(@Payload Users users) {
        chatService.addUser(users);
        return users;
    }

    @MessageMapping("/user.disconnectUser")
    @SendTo("/user/public")
    public Users disconnect(@Payload Users user) {
        chatService.disconnect(user);
        return user;
    }

    @GetMapping("/users")
    public ResponseEntity<List<Users>> findConnectedUsers() {
        return ResponseEntity.ok(chatService.findConnectedUsers());
    }

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage chatMessage) {
        chatService.processMessage(chatMessage);
    }

    @GetMapping("/messages/{senderId}/{recipientId}/{carId}")
    public ResponseEntity<List<ChatMessage>> findChatMessages(
            @PathVariable Long senderId,
            @PathVariable Long recipientId,
            @PathVariable Long carId) {
        return ResponseEntity.ok(chatService.findChatMessages(senderId, recipientId, carId));
    }
}
