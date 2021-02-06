package com.pjatk.accountmanager.service;

import com.pjatk.accountmanager.model.Bank;
import com.pjatk.accountmanager.repository.BankRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankService {

    private BankRepository bankRepository;

    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public Bank save(Bank bank) {

        if (bank.getBankAccount() <= 0){
            throw new IllegalArgumentException("Bank shouldn't be so poor!");
        }
        return bankRepository.save(bank);
    }

    public List<Bank> findAll() {
        return bankRepository.findAll();
    }
    public Optional<Bank> findByName(String bankName) {
        return bankRepository.findByName(bankName);
    }
}
