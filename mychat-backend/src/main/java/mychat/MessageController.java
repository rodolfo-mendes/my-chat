package mychat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    private ChatClient.Builder builder;

    public MessageController(ChatClient.Builder builder) {
        this.builder = builder;
    }

    @PostMapping("/message")
    public String sendMessage(@RequestBody String message) {
        ChatClient chatClient = builder.build();

        return chatClient
            .prompt(message)
            .call()
            .content();
    }
}
