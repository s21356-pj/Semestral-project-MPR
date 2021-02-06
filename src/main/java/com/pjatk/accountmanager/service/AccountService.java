package com.pjatk.accountmanager.service;

import com.pjatk.accountmanager.model.Bank;
import com.pjatk.accountmanager.model.Client;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    public Client moneyTransfer(Client fromClient, Client toClient){
        if (fromClient.getToTransfer() > fromClient.getAccountSum()){
            throw new IllegalArgumentException("You have not enough money!");
        }
        toClient.setAccountSum(toClient.getAccountSum() + fromClient.getToTransfer());
        fromClient.setAccountSum(fromClient.getAccountSum() - fromClient.getToTransfer());
        fromClient.setToTransfer(0);
        return toClient;
    }

    public Client salaryPayment(Client employees, Bank bank){
        employees.setAccountSum(employees.getAccountSum() + employees.getSalary());
        bank.setBankAccount(bank.getBankAccount() - employees.getSalary());
        return employees;
    }

    public Client employeeLoan(Client loaner, Bank bank){
        loaner.setLoan(3 * loaner.getSalary());
        loaner.setAccountSum(loaner.getAccountSum() + loaner.getLoan());
        bank.setBankAccount(bank.getBankAccount() - loaner.getLoan());
        return loaner;
    }
}
