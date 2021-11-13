package com.revature.banking.models;

import com.revature.banking.util.LinkedList;

import java.util.Objects;


//This class handles the creation and management of single account object data.


public class Account {

    private String id;
    private String type;
    private AppUser owner;
    private Double balance;
    private String name;


    public Account() {
        this.balance = 0.00;
    }

    public Account(String id, String type, AppUser owner, Double balance) {
        this.id = id;
        this.type = type;
        this.owner = owner;
        this.balance = balance;
    }

    public Account(String type, AppUser owner, Double balance) {
        this.type = type;
        this.owner = owner;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AppUser getOwner() {
        return owner;
    }

    public void setOwner(AppUser owner) {
        this.owner = owner;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) && Objects.equals(type, account.type) && Objects.equals(owner, account.owner) && Objects.equals(balance, account.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, owner, balance);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", owner=" + owner +
                ", balance=" + balance +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
}
