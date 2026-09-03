package mychat.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request body for creating a new chat.")
public record ChatRequest(
    @Schema(description = "The initial message to be sent in the new chat.", minLength = 1)
    @NotNull
    MessageRequest message
) {}
