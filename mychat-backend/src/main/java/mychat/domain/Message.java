package mychat.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import java.time.OffsetDateTime;

public record Message (
    @Id
    Long id,
    @NotNull
    AggregateReference<Chat, Long> chatId,
    @NotBlank
    String content,
    OffsetDateTime receivedAt
) {}
