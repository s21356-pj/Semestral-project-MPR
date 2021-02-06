package com.pjatk.accountmanager.service;
import com.pjatk.accountmanager.model.Bank;
import com.pjatk.accountmanager.model.Client;
import com.pjatk.accountmanager.repository.BankRepository;
import com.pjatk.accountmanager.repository.ClientRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private ClientRepository clientRepository;
    private AccountService accountService;
    private BankService bankService;
    private BankRepository bankRepository;

    public ClientService(ClientRepository clientRepository, AccountService accountService, BankService bankService, BankRepository bankRepository) {
        this.clientRepository = clientRepository;
        this.accountService = accountService;
        this.bankService = bankService;
        this.bankRepository = bankRepository;
    }

    public Client save(Client client) {

        client.setName(upperCaseName(client.getName()));
        client.setSurname(upperCaseSurname(client.getSurname()));
        client.setPesel(peselCheck((client.getPesel())));
        return clientRepository.save(client);
    }

    public String upperCaseName(String name){
        return name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
    }

    public String upperCaseSurname(String surname){
        return surname.substring(0,1).toUpperCase() + surname.substring(1).toLowerCase();
    }

    public Long peselCheck(Long pesel){
        Optional<Client> optionalClient = findByPesel(pesel);
        int numberOfdigits = pesel.toString().length();
        if (numberOfdigits != 11){
            throw new IllegalArgumentException("Wrong PESEL");
        }else
            if (optionalClient.isPresent()){
                throw new IllegalArgumentException("This PESEL already exists in the database");
            }
        return pesel;
    }

    public List<Client> findAll() {

        return clientRepository.findAll();
    }

    public Optional<Client> findByPesel(Long clientPesel) {
        return clientRepository.findByPesel(clientPesel);
        }

    public Optional<Client> findById(Long clientId){
        return clientRepository.findById(clientId);
        }

    public Client update(Client client) {
        Optional<Client> byPesel = clientRepository.findByPesel(client.getPesel());
        if (byPesel.isEmpty()) {
            throw new RuntimeException();
        } else {
            return clientRepository.save(client);
        }
    }

    public void deleteById(Long Id) {
        clientRepository.deleteById(Id);
    }
    public void deleteAll() {
        clientRepository.deleteAll();
    }

    public Client moneyTransfer(Long fromClientId, Long toClientId){
        Client fromClient = findById(fromClientId).get();
        Client toClient = findById((toClientId)).get();
        toClient = accountService.moneyTransfer(fromClient, toClient);
        return clientRepository.save(toClient);
    }

    public Client salaryPayment(Long employeeId, String bankName){
        Client employee = null;
            for (Long i = 1L; i <= employeeId; i++) {
            employee = findById(i).get();
            Bank bank = bankService.findByName("Bank").get();
            employee = accountService.salaryPayment(employee, bank);
            clientRepository.save(employee);
            bankRepository.save(bank);
        }
        return employee;
    }

    public Client employeeLoan (Long loanerId, String bankName){
        Client loaner = findById(loanerId).get();
        Bank bank = bankService.findByName("Bank").get();
        if (loaner.getLoan() == 0) {
            loaner = accountService.employeeLoan(loaner, bank);
            bankRepository.save(bank);
        }

        return clientRepository.save(loaner);
    }
}
