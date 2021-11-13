package com.revature.banking.models;

import com.revature.banking.DAOS.TransactionsDAO;
import com.revature.banking.Services.AccountService;

public class Transaction {

    private String id;
    private String date;
    private String account_id;
    private String transactor_name;
    private String type;
    private Double amount;


    public Transaction(){}

    public Transaction(String date, String type) {
        this.date = date;
        this.type = type;
    }


    public Transaction(String date, String type, Double amount) {
        this.date = date;
        this.type = type;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getTransactor_name() {
        return transactor_name;
    }

    public void setTransactor_name(String transactor_name) {
        this.transactor_name = transactor_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
