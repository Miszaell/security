package com.ex.security.Controllers;

import com.ex.security.Services.ConversationService;
import com.ex.security.persistence.DTO.ConversationDTO;
import com.ex.security.persistence.Entity.Conversation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(value = "*")
@RestController
@RequestMapping("/conversation")
public class ConversationController {

    private final ConversationService conversationService;

    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @GetMapping
    public List<Conversation> findAll(){
        return this.conversationService.findAll();
    }
    @GetMapping("/byId")
    @ResponseBody
    public Conversation findById(@RequestParam("id") Long id) {
        return this.conversationService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Void> createConversation(@RequestBody ConversationDTO conversationDTO, @RequestParam("message") String message) {
        this.conversationService.createConversation(conversationDTO, message);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateCpnversation(@RequestParam("id") Long id, @RequestParam("message") String message) {
        this.conversationService.updateConversation(id, message);

        return ResponseEntity.noContent().build();
    }
}
