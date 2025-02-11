package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.InvalidMessageException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message createMessage(Message message) {
        if (message.getMessageText().isEmpty()) {
            throw new InvalidMessageException("Message must not be empty.");
        }
        if (message.getMessageText().length() > 255) {
            throw new InvalidMessageException("Message must not be over 255 characters.");
        }
        Optional<Account> optionalAccount = accountRepository.findById(message.getPostedBy());
        if (!optionalAccount.isPresent()) {
            throw new InvalidMessageException("The account does not exist.");
        }
        return messageRepository.save(message);
    }

    public List<Message> getMessages() {
        return messageRepository.findAll();
    }

}
