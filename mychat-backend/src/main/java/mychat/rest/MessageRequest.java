package mychat.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request body for sending a message in a chat.")
public record MessageRequest(
    @Schema(description = "The content of the message to be sent.", minLength = 1)
    @NotBlank
    String text
) {}
