package com.ex.security.Services;

import com.ex.security.Exceptions.ConversationExceptions;
import com.ex.security.Mapper.ConversationDTOtoConversation;
import com.ex.security.persistence.DTO.ConversationDTO;
import com.ex.security.persistence.Entity.Conversation;
import com.ex.security.persistence.Entity.User;
import com.ex.security.persistence.Repository.ConversationRepository;
import com.ex.security.utils.CypherCl;
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

        CypherCl cypherCl = new CypherCl();
        User user = conversation.getUserTwo();
        String publicKey = user.getPublicKeyPath();
        String message = "2520160077-hello";
        System.out.print(conversation.getConversationPath());
        try {
            String data = cypherCl.encrypt(message, publicKey);
            System.out.print(data);
            cypherCl.setMessages(conversation.getConversationPath(),data);
        } catch (Exception e) {
            throw new ConversationExceptions("The message couldn't be saved", HttpStatus.CONFLICT);
        }

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
        User user = conver.getUserTwo();
        CypherCl cypherCl = new CypherCl();

        try {
            String convers = cypherCl.getMessages(conver.getConversationPath());
            String decrypt = cypherCl.decrypt(convers, user.getPrivateKeyPath());
            System.out.println(convers);
            System.out.println(decrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conver;
    }

    public void updateConversation(Long id, Long idUserOne, Long idUserTwo){
        Optional<Conversation> conversation = this.repo.findById(id);

        if (conversation.isEmpty()){
            throw  new ConversationExceptions("Conversation not found", HttpStatus.NOT_FOUND);
        }
        String messages;
        CypherCl cypherCl = new CypherCl();
        Conversation convr = conversation.get();
        try {
            messages = cypherCl.getMessages(convr.getConversationPath());
            System.out.print(messages);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Conversation> findConvarsation(Long userOne, Long userTwo) {
        return this.repo.searchConversation(userOne, userTwo);
    }




}
