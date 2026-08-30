package mychat.rest;

import jakarta.validation.constraints.NotNull;

public record ChatRequest(
    @NotNull MessageRequest message
) {}
