package com.ex.security.persistence.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConversationDTO {

    private Long id;
    private Long id_user_one;
    private Long id_user_two;
    private LocalDateTime created_at;
}
