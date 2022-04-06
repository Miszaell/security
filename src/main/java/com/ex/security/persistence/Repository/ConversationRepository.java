package com.ex.security.persistence.Repository;

import com.ex.security.persistence.Entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
}
