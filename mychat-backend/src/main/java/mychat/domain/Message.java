package mychat.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import java.time.OffsetDateTime;

public record Message (
    @Id Long id,
    AggregateReference<Chat, Long> chatId,
    String content,
    OffsetDateTime receivedAt
) {}
