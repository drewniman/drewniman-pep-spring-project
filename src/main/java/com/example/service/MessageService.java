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
        validateMessage(message);

        Optional<Account> optionalAccount = accountRepository.findById(message.getPostedBy());
        if (!optionalAccount.isPresent()) {
            throw new InvalidMessageException("The account does not exist.");
        }

        return messageRepository.save(message);
    }

    public List<Message> getMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(int id) {
        return messageRepository.findById(id).orElse(null);
    }

    public int deleteMessageById(int id) {
        if (messageRepository.findById(id).isPresent()) {
            messageRepository.deleteById(id);
            return 1;
        }
        return 0;
    }

    public void updateMessage(int id, Message message) {
        validateMessage(message);

        Optional<Message> optionalMessage = messageRepository.findById(id);
        if (optionalMessage.isEmpty()) {
            throw new InvalidMessageException("Message does not exist.");
        }

        Message updatedMessage = optionalMessage.get();
        updatedMessage.setMessageText(message.getMessageText());
        messageRepository.save(updatedMessage);
    }

    private void validateMessage(Message message) {
        if (message.getMessageText() == null || message.getMessageText().isEmpty()) {
            throw new InvalidMessageException("Message must not be empty.");
        }
        if (message.getMessageText().length() > 255) {
            throw new InvalidMessageException("Message must not be over 255 characters.");
        }
    }

}
