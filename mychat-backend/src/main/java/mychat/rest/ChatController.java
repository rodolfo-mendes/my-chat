package mychat.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import mychat.domain.ChatService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chats")
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ChatResponse create(@Valid @RequestBody ChatRequest createChatRequest) {
        var chat = chatService.createChatWithMessage(createChatRequest.message().text());
        var messages = chatService
            .findMessagesByChatId(chat.id())
            .stream()
            .map(message -> new MessageResponse(message.id(), message.content(), message.receivedAt()))
            .toList();

        return new ChatResponse(chat.id(), messages, chat.createdAt());
    }

    @GetMapping("{id}")
    public ChatResponse findById(@NotNull @PathVariable Long id) {
        var chat = chatService.findChatById(id);

        var messages = chatService
            .findMessagesByChatId(chat.id())
            .stream()
            .map(message -> new MessageResponse(message.id(), message.content(), message.receivedAt()))
            .toList();

        return new ChatResponse(chat.id(), messages, chat.createdAt());
    }

    @PostMapping("{id}/messages")
    public MessageResponse sendMessage(
            @NotNull @PathVariable Long id,
            @Valid @RequestBody MessageRequest messageRequest) {
        var message = chatService.sendMessageAndReceiveResponse(id, messageRequest.text());
        return new MessageResponse(message.id(), message.content(), message.receivedAt());
    }
}
