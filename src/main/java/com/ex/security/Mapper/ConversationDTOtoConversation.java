package com.ex.security.Mapper;

import com.ex.security.Services.UserService;
import com.ex.security.persistence.DTO.ConversationDTO;
import com.ex.security.persistence.Entity.Conversation;
import com.ex.security.persistence.Entity.User;
import org.springframework.stereotype.Component;

@Component
public class ConversationDTOtoConversation implements IMapper<ConversationDTO, Conversation> {

    private final UserService userService;

    public ConversationDTOtoConversation(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Conversation map(ConversationDTO conversationDTO) {
        Conversation conversation = new Conversation();
        User userOne = this.userService.findById(conversationDTO.getId_user_one());
        User userTwo = this.userService.findById(conversationDTO.getId_user_two());

        conversation.setConversationPath(conversationDTO.getConversationPath());
        conversation.setConversationKey(conversationDTO.getConversationKey());
        conversation.setUserOne(userOne);
        conversation.setUserTwo(userTwo);
        return conversation;
    }
}
