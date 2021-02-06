package com.pjatk.accountmanager.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Client {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String surname;
    private Long pesel;
    private int salary;
    private int accountSum;
    private int toTransfer;
    private int loan;


    public Client(Long id, String name, String surname, Long pesel, int salary, int accountSum, int toTransfer, int loan) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.salary = salary;
        this.accountSum = accountSum;
        this.toTransfer = toTransfer;
        this.loan = loan;
    }

    public Client() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Long getPesel() {
        return pesel;
    }

    public void setPesel(Long pesel) {
        this.pesel = pesel;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getLoan() {
        return loan;
    }

    public void setLoan(int loan) {
        this.loan = loan;
    }

    public int getAccountSum() {
        return accountSum;
    }

    public void setAccountSum(int accountSum) {
        this.accountSum = accountSum;
    }

    public int getToTransfer() {
        return toTransfer;
    }

    public void setToTransfer(int toTransfer) {
        this.toTransfer = toTransfer;
    }

    @Override
    public String toString() {
        return "Client{" +
                "Id: " + id +
                ", Name: " + name +
                ", Surname: " + surname +
                ", PESEL: " + pesel +
                ", Salary: " + salary +
                ", Savings: " + accountSum + " pln" +
                ", toTransfer: " + toTransfer +
                ", loan: " + loan +
                "}";
    }
}
