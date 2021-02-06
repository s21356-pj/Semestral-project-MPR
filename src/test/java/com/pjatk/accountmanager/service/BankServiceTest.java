package com.pjatk.accountmanager.service;

import com.pjatk.accountmanager.model.Bank;
import com.pjatk.accountmanager.model.Client;
import com.pjatk.accountmanager.repository.BankRepository;
import com.pjatk.accountmanager.repository.ClientRepository;
import com.sun.el.stream.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BankServiceTest {

    @Mock
    private BankRepository bankRepository;

    @InjectMocks
    private BankService bankService;

    @Test
    void shouldSaveBank(){
        //given
        Bank bank = new Bank("Bank", 100000);
        when(bankRepository.save(bank)).thenReturn(new Bank("Bank",100000));
        //when
        Bank saveBank = bankService.save(bank);
        //then
        assertThat(saveBank.getName()).isEqualTo("Bank");
    }

    @Test
    void shouldFindAllBanks() {
        //Given
        when(bankRepository.findAll())
                .thenReturn(List.of(new Bank()));
        //When
        List<Bank> all = bankService.findAll();
        //Then
        assertThat(all).hasSize(1);
    }
}
