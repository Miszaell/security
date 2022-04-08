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

    public void createConversation(ConversationDTO conversationDTO, String message){
        Conversation conversation = mnapper.map(conversationDTO);

        CypherCl cypherCl = new CypherCl();
        User user = conversation.getUserOne();
        User user2 = conversation.getUserTwo();
        String privateKeyPath = "uploads/"+user.getMatricula()+"_"+user.getNombre()+"_private.enc";
        String keyPath = "src/main/resources/Keys/ConversationKey/"+user.getMatricula() +"_"+user2.getMatricula()+".enc";
        try {
            byte[] sign = cypherCl.signMessage(privateKeyPath, message);
            boolean verifyResult = cypherCl.verifySign(user.getPublicKeyPath(), message, sign);
            if (verifyResult) {
                cypherCl.setMessages(conversation.getConversationPath(),message);
                this.repo.save(conversation);
            } else {
                throw new ConversationExceptions("Key invalid", HttpStatus.NOT_ACCEPTABLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        throw new ConversationExceptions("Cannot create a conversation", HttpStatus.CONFLICT);
        }

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
            System.out.print(convers);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conver;
    }

    public void updateConversation(Long id, String message){
        Optional<Conversation> conversation = this.repo.findById(id);

        if (conversation.isEmpty()){
            throw  new ConversationExceptions("Conversation not found", HttpStatus.NOT_FOUND);
        }
        CypherCl cypherCl = new CypherCl();
        Conversation convr = conversation.get();
        User user = convr.getUserOne();
        User user2 = convr.getUserTwo();
        String privateKeyPath = "uploads/"+user.getMatricula()+"_"+user.getNombre()+"_private.enc";
        String keyPath = "src/main/resources/Keys/ConversationKey/"+user.getMatricula() +"_"+user2.getMatricula()+".enc";
        try {
            byte[] sign = cypherCl.signMessage(privateKeyPath, message);
            boolean verifyResult = cypherCl.verifySign(user.getPublicKeyPath(), message, sign);
            if (verifyResult) {
                String cnv = cypherCl.getMessages(convr.getConversationPath());
                String data = cnv +"\n"+ message;
                cypherCl.updateFile(convr.getConversationPath(),data);
            } else {
                throw new ConversationExceptions("Key invalid", HttpStatus.NOT_ACCEPTABLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Conversation> findConvarsation(Long userOne, Long userTwo) {
        return this.repo.searchConversation(userOne, userTwo);
    }




}
