package com.example.service;

import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.DuplicateUsernameException;
import com.example.exception.InvalidAccountException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account register(Account account) throws InvalidAccountException, DuplicateUsernameException {
        if (account.getUsername() == null || account.getUsername().isEmpty()) {
            throw new InvalidAccountException("Username must not be blank.");
        }
        if (account.getPassword() == null || account.getPassword().length() < 4) {
            throw new InvalidAccountException("Password must be at least 4 characters long.");
        }
        if (accountRepository.findByUsername(account.getUsername()).isPresent()) {
            throw new DuplicateUsernameException("An account with that username already exists.");
        }
        return accountRepository.save(account);
    }

}
