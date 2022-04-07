package com.ex.security.persistence.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConversationDTO {

    private Long id;
    private String conversationPath;
    private Long id_user_one;
    private Long id_user_two;
}
