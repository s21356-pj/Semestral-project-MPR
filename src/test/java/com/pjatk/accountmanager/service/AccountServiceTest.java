package com.pjatk.accountmanager.service;

import com.pjatk.accountmanager.model.Bank;
import com.pjatk.accountmanager.model.Client;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountServiceTest {

    private AccountService accountService = new AccountService();

    private Client createClient1() {
        return new Client(1L, "Adam", "Nowak", 82062307138L, 4200, 10000, 1000, 0);
    }

    private Client createClient2() {
        return new Client(2L, "Ala", "Marcyniuk", 83091306142L, 4600, 5000, 0,0);
    }

    @Test
    void shouldTransferToClient(){
        //given
        Client fromClient = createClient1();
        Client toClient = createClient2();
        //when
        accountService.moneyTransfer(fromClient, toClient);
        //then
        assertThat(toClient.getAccountSum()).isEqualTo(6000);
    }

    @Test
    void shouldPaySalary(){
        //given
        Client client1 = createClient1();
        Client client2 = createClient2();
        Bank bank = new Bank("Bank", 100000);
        //when
        accountService.salaryPayment(client1, bank);
        accountService.salaryPayment(client2, bank);
        //then
        assertThat(client1.getAccountSum()).isEqualTo(14200);
        assertThat(client2.getAccountSum()).isEqualTo(9600);
        assertThat(bank.getBankAccount()).isEqualTo(91200);
    }

    @Test
    void shouldLoanMoney(){
        //given
        Client client1 = createClient1();
        Bank bank = new Bank("Bank", 100000);
        //when
        accountService.employeeLoan(client1, bank);
        //then
        assertThat(client1.getLoan()).isEqualTo(12600);
        assertThat(bank.getBankAccount()).isEqualTo(87400);
    }
}
