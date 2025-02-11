package com.example.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Message;
import com.example.service.MessageService;

@RestController
@RequestMapping("accounts")
public class AccountController {
    
    private MessageService messageService;

    public AccountController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("{id}/messages")
    public ResponseEntity<List<Message>> getMessages(@PathVariable int id) {
        List<Message> messages = messageService.getMessagesByAccount(id);
        return ResponseEntity.ok(messages);
    }

}
