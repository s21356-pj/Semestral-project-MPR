package com.pjatk.accountmanager.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Bank {

    @Id
    private String name;
    private int bankAccount;

    public Bank(String name, int bankAccount) {
        this.name = name;
        this.bankAccount = bankAccount;
    }

    public Bank() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(int bankAccount) {
        this.bankAccount = bankAccount;
    }
}
