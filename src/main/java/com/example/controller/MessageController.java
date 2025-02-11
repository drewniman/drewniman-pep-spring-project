package com.example.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Message;
import com.example.service.MessageService;

@RestController
@RequestMapping("messages")
public class MessageController {

    private MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message newMessage = messageService.createMessage(message);
        return ResponseEntity.ok(newMessage);
    }

    @GetMapping
    public ResponseEntity<List<Message>> getMessages() {
        List<Message> messages = messageService.getMessages();
        return ResponseEntity.ok(messages);
    }

    @GetMapping("{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable int id) {
        Message message = messageService.getMessageById(id);
        return ResponseEntity.ok(message);
    }

}
