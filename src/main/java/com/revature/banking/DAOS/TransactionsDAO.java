package com.revature.banking.DAOS;

import com.revature.banking.models.Account;
import com.revature.banking.models.AppUser;
import com.revature.banking.models.Transaction;
import com.revature.banking.util.ConnectionFactory;
import com.revature.banking.util.List;

import java.sql.*;

public class TransactionsDAO implements CrudDAO<Transaction>{
    @Override //Write Transaction to database
    public Transaction save(Transaction newObj) {
        return null;
    }

    @Override //Get Transaction List
    public List<Transaction> findAll() {
        return null;
    }

    @Override //Find Single transaction by id. Likely won't implement.
    public Transaction findById(String id) {
        return null;
    }

    @Override
    public boolean update(Transaction updatedObj) {
        return false;
    }

    @Override
    public boolean removeById(String id) {
        return false;
    }

    //Show all Transactions
    //List collection Function.




    //Get Balance from Current Account(AccountDAO should have Account and User available.)
    //TransactionDAO holding account dao which is holding user dao which has a user
    public Double getBalance (String accountId){
        //Create Data Coalating Objects
        Double balance = 0.0;

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {


        String sql = "select * from accounts where id = ?";
        PreparedStatement pstmt = conn.prepareStatement("sql");
        pstmt.setString(1, accountId );
        ResultSet rs = pstmt.executeQuery(sql);
        balance = rs.getDouble("Balance");
        //Display Account Types and Balances


            /*
            *   private String id;
                private String date;
                private String account_id;
                private String transactor_name;
                private String type;
                private Double amount;*/

    } catch (SQLException e) {
            e.printStackTrace();
        }

    return balance;
    }

    //Try a network connection
    //Prepare view statement
    //Display Account Types and Balances



    //Withdrawl



    //Deposit




}
