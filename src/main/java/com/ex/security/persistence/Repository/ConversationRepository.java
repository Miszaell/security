package com.ex.security.persistence.Repository;

import com.ex.security.persistence.Entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    @Query(value = "SELECT * FROM conversation WHERE id_user_one=:idOne AND id_user_two=:idTwo", nativeQuery = true)
    List<Conversation> searchConversation(@Param("idOne") Long idOne, @Param("idTwo") Long idTwo);
}
