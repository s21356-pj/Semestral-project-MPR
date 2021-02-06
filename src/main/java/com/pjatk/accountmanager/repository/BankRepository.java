package com.pjatk.accountmanager.repository;

import com.pjatk.accountmanager.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {
    Optional<Bank> findByName(String bankName);
}
