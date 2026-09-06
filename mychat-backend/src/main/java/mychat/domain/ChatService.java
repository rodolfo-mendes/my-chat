package mychat.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class ChatService {
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final ChatClient  defaultChatClient;
    private final ChatClient chatClientWithMemory;

    public ChatService(
            ChatRepository chatRepository,
            MessageRepository messageRepository,
            ChatClient  defaultChatClient,
            @Qualifier("chatClientWithMemory") ChatClient chatClientWithMemory) {
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
        this.defaultChatClient = defaultChatClient;
        this.chatClientWithMemory = chatClientWithMemory;
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

        var reponseContent = chatClientWithMemory
            .prompt()
            .system("You are a helpful assistant chatbot. Keep your answer short unless the user ask for more details.")
            .user(userMessageContent)
            .advisors(spec -> spec.param(ChatMemory.CONVERSATION_ID, chatId))
            .call()
            .content();

        return messageRepository.save(new Message(
            null,
            userMessage.chatId(),
            reponseContent,
            java.time.OffsetDateTime.now()
        ));
    }

    public Chat createChatWithMessage(@NotNull Long userId, @NotBlank String text) {
        String title = defaultChatClient
            .prompt()
            .system("You are a title generator for conversations. For a giving question, generate a single line title for the conversation")
            .user(text)
            .call()
            .content();

        var chat = chatRepository.save(new Chat(null, AggregateReference.to(userId), title, java.time.OffsetDateTime.now()));
        this.sendMessageAndReceiveResponse(chat.id(), text);
        return chat;
    }

    public List<Message> findMessagesByChatId(@NotNull final Long chatId) {
        return messageRepository.findByChatId(AggregateReference.to(chatId));
    }

    public List<Chat> findAllChats() {
        return StreamSupport
            .stream(chatRepository.findAll().spliterator(), false)
            .toList();
    }

    public Optional<Chat> findChatById(@NotNull Long id) {
        return chatRepository.findById(id);
    }
}
