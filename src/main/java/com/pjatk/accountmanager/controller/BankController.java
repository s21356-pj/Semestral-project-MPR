package com.pjatk.accountmanager.controller;

import com.pjatk.accountmanager.model.Bank;
import com.pjatk.accountmanager.service.BankService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bank")
public class BankController {

    private BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping
    public ResponseEntity<List<Bank>> findAll(){
        return ResponseEntity.ok(bankService.findAll());
    }

    @PostMapping
    public ResponseEntity<Bank> save(@RequestBody Bank bank){
        return ResponseEntity.ok(bankService.save(bank));
    }
}
