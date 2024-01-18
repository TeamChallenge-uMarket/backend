package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.ChatRoomDAO;
import com.example.securityumarket.models.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomDAO chatRoomDAO;

    public Optional<String> getChatRoomId(
            Long senderId,
            Long recipientId,
            Long carId,
            boolean createNewRoomIfNotExists
    ) {
        return chatRoomDAO
                .findBySenderIdAndRecipientIdAndCarId(senderId, recipientId, carId)
                .map(ChatRoom::getChatId)
                .or(() -> {
                    if (createNewRoomIfNotExists) {
                        var chatId = createChatId(senderId, recipientId, carId);
                        return Optional.of(chatId);
                    }

                    return Optional.empty();
                });
    }

    private String createChatId(Long senderId, Long recipientId, Long carId) {
        var chatId = String.format("%s_%s_%s", senderId, recipientId, carId);

        ChatRoom senderRecipient = ChatRoom
                .builder()
                .chatId(chatId)
                .senderId(senderId)
                .recipientId(recipientId)
                .carId(carId)
                .build();
        chatRoomDAO.save(senderRecipient);

        ChatRoom recipientSender = ChatRoom
                .builder()
                .chatId(chatId)
                .senderId(recipientId)
                .recipientId(senderId)
                .carId(carId)
                .build();
        chatRoomDAO.save(recipientSender);

        return chatId;
    }
}