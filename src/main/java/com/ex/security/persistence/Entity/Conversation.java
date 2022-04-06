package com.ex.security.persistence.Entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table ( name = "conversation")
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idUserOne")
    private User userOne;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idUserTwo")
    private User userTwo;

    private LocalDateTime created_at;
}
