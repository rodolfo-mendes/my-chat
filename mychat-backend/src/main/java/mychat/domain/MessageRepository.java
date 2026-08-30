package mychat.domain;

import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message,Long> {
    List<Message> findByChatId(AggregateReference<Chat, Long> chatId);
}
