package com.ex.security.Services;

import com.ex.security.Exceptions.ConversationExceptions;
import com.ex.security.Mapper.ConversationDTOtoConversation;
import com.ex.security.persistence.DTO.ConversationDTO;
import com.ex.security.persistence.Entity.Conversation;
import com.ex.security.persistence.Repository.ConversationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConversationService {

    private final ConversationRepository repo;
    private final ConversationDTOtoConversation mnapper;

    public ConversationService(ConversationRepository repo, ConversationDTOtoConversation mnapper) {
        this.repo = repo;
        this.mnapper = mnapper;
    }

    public Conversation createConversation(ConversationDTO conversationDTO){
        Conversation conversation = mnapper.map(conversationDTO);

        return this.repo.save(conversation);
    }

    public List<Conversation> findAll() {
        return this.repo.findAll();
    }

    public Conversation findById(Long id){
        Optional<Conversation> conversation = this.repo.findById(id);

        if (conversation.isEmpty()) {
            throw new ConversationExceptions("Conversation not found", HttpStatus.NOT_FOUND);
        }

        Conversation conver = conversation.get();

        return conver;
    }

    public List<Conversation> findConvarsation(Long userOne, Long userTwo) {
        return this.repo.searchConversation(userOne, userTwo);
    }




}
