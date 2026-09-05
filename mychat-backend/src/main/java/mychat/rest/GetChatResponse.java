package mychat.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;
import java.util.List;

@Schema(description = "Response body for retrieving a chat, including its messages and creation timestamp.")
public record GetChatResponse(
    @Schema(description = "The unique identifier of the chat.", example = "1")
    @NotNull Long id,
    @Schema(description = "The title of the chat.")
    @NotNull String title,
    @Schema(description = "The list of messages associated with the chat.")
    @NotNull List<MessageResponse> messages,
    @Schema(description = "The timestamp when the chat was created.", example = "2024-06-01T12:00:00Z")
    @NotNull OffsetDateTime createdAt
) {}
