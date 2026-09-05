package mychat.rest;

import java.time.OffsetDateTime;

public record ChatListItem(Long id, String title, OffsetDateTime date) {
}
