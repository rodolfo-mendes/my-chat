package mychat.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final ChatClient.Builder builder;

    public ChatService(ChatRepository chatRepository, MessageRepository messageRepository, ChatClient.Builder builder) {
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
        this.builder = builder;
    }

    public Message sendMessageAndReceiveResponse(
            @NotNull final Long chatId,
            @NotBlank final String userMessageContent) {
        chatRepository.findById(chatId)
            .orElseThrow(() -> new IllegalArgumentException("Chat with id " + chatId + " does not exist."));

        var userMessage = messageRepository.save(new Message(
            null,
            AggregateReference.to(chatId),
            userMessageContent,
            java.time.OffsetDateTime.now())
        );

        var chatClient = builder.build();
        var reponseContent = chatClient
            .prompt(userMessageContent)
            .call()
            .content();

        return messageRepository.save(new Message(
            null,
            userMessage.chatId(),
            reponseContent,
            java.time.OffsetDateTime.now()
        ));
    }

    public Chat createChatWithMessage(@NotBlank String text) {
        var chat = chatRepository.save(new Chat(null, java.time.OffsetDateTime.now()));
        this.sendMessageAndReceiveResponse(chat.id(), text);
        return chat;
    }

    public List<Message> findMessagesByChatId(@NotNull final Long chatId) {
        return messageRepository.findByChatId(AggregateReference.to(chatId));
    }

    public Chat findChatById(@NotNull Long id) {
        return chatRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Chat with id " + id + " does not exist."));
    }
}
