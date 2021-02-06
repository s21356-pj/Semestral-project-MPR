package com.pjatk.accountmanager.controller;

import com.pjatk.accountmanager.model.Client;
import com.pjatk.accountmanager.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/client")
public class ClientController {

    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<List<Client>> findAll(){
        return ResponseEntity.ok(clientService.findAll());
    }

    @PostMapping
    public ResponseEntity<Client> save(@RequestBody Client client){
        return ResponseEntity.ok(clientService.save(client));
    }

    @GetMapping("/pesel/{pesel}")
    public ResponseEntity<Client> findByPesel(@PathVariable Long pesel) {
        Optional<Client> optionalPlayer = clientService.findByPesel(pesel);

        if (optionalPlayer.isPresent()) {
            return ResponseEntity.ok(optionalPlayer.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> findById(@PathVariable Long id){
        Optional<Client> optionalClient = clientService.findById(id);

        if (optionalClient.isPresent()) {
            return ResponseEntity.ok(optionalClient.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<Client> update(@RequestBody Client client) {
        return ResponseEntity.ok(clientService.update(client));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        clientService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/transfer/{fromClientId}/{toClientId}")
    public ResponseEntity<Client> moneyTransfer(@PathVariable Long fromClientId, @PathVariable Long toClientId){
        return ResponseEntity.ok(clientService.moneyTransfer(fromClientId, toClientId));
    }

    @GetMapping("/salary/{employeeId}/{bankName}")
    public ResponseEntity<Client> salaryPayment(@PathVariable Long employeeId, @PathVariable String bankName) {
        return ResponseEntity.ok(clientService.salaryPayment(employeeId, bankName));
    }

    @GetMapping("/loan/{loanerId}/{bankName}")
    public ResponseEntity<Client> employeeLoan(@PathVariable Long loanerId, @PathVariable String bankName) {
        return ResponseEntity.ok(clientService.employeeLoan(loanerId, bankName));
    }
}
