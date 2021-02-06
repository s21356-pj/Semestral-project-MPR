package com.pjatk.accountmanager.service;
import com.pjatk.accountmanager.model.Bank;
import com.pjatk.accountmanager.model.Client;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;


@SpringBootTest
public class ClientServiceTestIT {

    @Autowired
    private ClientService clientService;

    @Autowired
    private BankService bankService;

    @AfterEach
    void cleanUp() {
        clientService.deleteAll();
    }

    @Test
    void shouldNotFindAnyone() {
        List<Client> allClients = clientService.findAll();
        assertThat(allClients).isEmpty();
    }

    @Test
    void shouldFindAllClients() {
        clientService.save(new Client(1L, "Adam", "nOWaK", 82062307138L, 3800, 0, 0, 0));
        List<Client> allClients = clientService.findAll();
        assertThat(allClients).isNotEmpty();
    }

    @Test
    void shouldSaveClient() {
        Client client = clientService.save(new Client(1L, "Adam", "nOWaK", 82062307138L, 3800, 0, 0, 0));
        assertThat(client.getId()).isPositive();
    }

    @Test
    void shouldFindClientByID() {
        Client client = clientService.save(new Client(1L, "Adam", "nOWaK", 82062307138L, 3800, 0, 0, 0));

        assertThat(clientService.findById(client.getId())).isNotEmpty();
    }

    @Test
    void shouldThrowExceptionOnFindByPESEL() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> clientService.peselCheck(2062307138L));
    }

    @Test
    void shouldTransferMoney() {
        Client fromClient = clientService.save(new Client(1L, "Adam", "nOWaK", 82062307138L, 3800, 10000, 2000, 0));
        Client toClient = clientService.save(new Client(2L, "Marta", "Nawrot", 83072305124L, 3300, 0, 0, 0));

        clientService.moneyTransfer(fromClient.getId(), toClient.getId());

        assertThat(clientService.findById(toClient.getId()).get().getAccountSum()).isEqualTo(2000);
    }

    @Test
    void shouldPaySalary() {
        Client client = clientService.save(new Client(1L, "Adam", "nOWaK", 82062307138L, 3800, 10000, 2000, 0));
        Client client1 = clientService.save(new Client(2L, "Rafał", "nOWaK", 84062307138L, 3000, 10000, 2000, 0));
        Client client2 = clientService.save(new Client(3L, "Marta", "Nawrot", 83072305124L, 3300, 0, 0, 0));
        Bank bank = bankService.save(new Bank ("Bank", 100000));
        clientService.salaryPayment(client2.getId(), bank.getName());

        assertThat(clientService.findById(client.getId()).get().getAccountSum()).isEqualTo(13800);
        assertThat(bankService.findByName(bank.getName()).get().getBankAccount()).isEqualTo(89900);
    }

    @Test
    void shouldPayLoan() {
        Client client = clientService.save(new Client(1L, "Adam", "nOWaK", 82062307138L, 3000, 10000, 2000, 0));
        Bank bank = bankService.save(new Bank ("Bank", 100000));
        clientService.employeeLoan(client.getId(), bank.getName());

        assertThat(clientService.findById(client.getId()).get().getLoan()).isEqualTo(9000);
        assertThat(clientService.findById(client.getId()).get().getAccountSum()).isEqualTo(19000);
        assertThat(bankService.findByName(bank.getName()).get().getBankAccount()).isEqualTo(91000);
    }
}
