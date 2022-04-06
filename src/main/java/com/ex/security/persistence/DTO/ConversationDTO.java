package com.ex.security.persistence.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConversationDTO {

    private Long id;
    private Long id_user;
    private LocalDateTime created_at;
}
