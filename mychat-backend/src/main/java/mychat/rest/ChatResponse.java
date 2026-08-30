package mychat.rest;

import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;
import java.util.List;

public record ChatResponse(
    @NotNull Long id,
    @NotNull List<MessageResponse> messages,
    @NotNull OffsetDateTime createdAt
) {}
