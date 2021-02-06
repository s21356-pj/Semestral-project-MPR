package com.pjatk.accountmanager.service;

import com.pjatk.accountmanager.model.Bank;
import com.pjatk.accountmanager.model.Client;
import com.pjatk.accountmanager.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private AccountService accountService;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    @Test
    void save(){
        //given
       Client client = new Client(1L, "Adam", "Nowak", 82062307138L, 4200, 0, 0, 0);
       when(clientRepository.save(client)).thenReturn(new Client(1L, "Adam", "Nowak", 82062307138L, 4200, 0, 0, 0));
        //when
        Client saveClient = clientService.save(client);
        //then
        assertThat(saveClient.getName()).isEqualTo("Adam");
    }

    @Test
    void upperCaseName(){
        //Given
        Client client = new Client(1L, "aDaM", "Nowak", 82062307138L, 4200, 0, 0, 0);
        when(clientRepository.save(client)).thenReturn(new Client(1L, clientService.upperCaseName(client.getName()), "Nowak", 82062307138L, 3800, 0, 0, 0));
        //when
        Client saveClient = clientService.save(client);
        //then
        assertThat(saveClient.getName()).isEqualTo("Adam");
    }

    @Test
    void upperCaseSurname(){
        //Given
        Client client = new Client(1L, "Adam", "nOWaK", 82062307138L, 3800, 0, 0, 0);
        when(clientRepository.save(client)).thenReturn(new Client(1L, "Adam", clientService.upperCaseSurname(client.getSurname()), 82062307138L, 3800, 0, 0, 0));
        //when
        Client saveClient = clientService.save(client);
        //then
        assertThat(saveClient.getSurname()).isEqualTo("Nowak");
    }

   @Test
   void shouldThrowExceptionOnPeselCheck() {
       assertThatExceptionOfType(IllegalArgumentException.class)
               .isThrownBy(() -> clientService.peselCheck(2062307138L));
   }

    @Test
    void deleteById() {
        //Given

        //When
        clientService.deleteById(1L);
        clientService.deleteById(2L);
        clientService.deleteById(3L);
        //Then
        verify(clientRepository, times(3)).deleteById(anyLong());
    }

    @Test
    void findAll() {
        //Given
        when(clientRepository.findAll())
                .thenReturn(List.of(new Client()));
        //When
        List<Client> allClients = clientService.findAll();
        //Then
        assertThat(allClients).hasSize(1);
    }

    @Test
    void shouldTransferMoney() {
        //Given
        Client fromClient = new Client(1L, "Adam", "nOWaK", 82062307138L, 3800, 10000, 2000, 0);
        Client toClient = new Client(2L, "Marta", "Nawrot", 83072305124L, 3300, 0, 0, 0);
        //When
        when(clientRepository.findById(1L)).thenReturn(Optional.of(fromClient));
        when(clientRepository.findById(2L)).thenReturn(Optional.of(toClient));
        when(clientRepository.save(toClient)).thenReturn(toClient);
        when(accountService.moneyTransfer(any(), any())).thenCallRealMethod();
        //Then
        Client afterTransfer = clientService.moneyTransfer(1L, 2L);
        assertThat(afterTransfer.getAccountSum()).isEqualTo(2000);
    }
    @Test
    void shouldPaySalary() {
        //Given
        Client client1 = new Client(1L, "Adam", "nOWaK", 82062307138L, 3000, 10000, 2000, 0);
        Bank bank = new Bank("Bank", 100000);
        //When
        when(accountService.salaryPayment(any(), any())).thenCallRealMethod();
        //Then
        Client afterSalary = accountService.salaryPayment(client1, bank);
        assertThat(afterSalary.getAccountSum()).isEqualTo(13000);
    }

    @Test
    void shouldLoanMoney(){
        //Given
        Client client = new Client(1L, "Adam", "nOWaK", 82062307138L, 3800, 0, 0, 0);
        Bank bank = new Bank("Bank", 100000);
        //when
        when(accountService.employeeLoan(client,bank)).thenCallRealMethod();
        Client afterLoan = accountService.employeeLoan(client, bank);
        //then
        assertThat(afterLoan.getLoan()).isEqualTo(11400);
    }
}
