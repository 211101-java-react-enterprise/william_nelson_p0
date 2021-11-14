package com.revature.banking.DAOS;


import com.revature.banking.models.Transaction;
import com.revature.banking.util.database.ConnectionFactory;
import com.revature.banking.util.collections.List;

import java.sql.*;
import java.util.UUID;

public class TransactionsDAO implements CrudDAO<Transaction>{

    @Override //Write Transaction to database
    public Transaction save(Transaction newTran) {

            try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
                //They will Give Account Type,
                //User Session will have Owner id
                //Need to Provide ID
                //Create new Account ID
                newTran.setId(UUID.randomUUID().toString());
                newTran.setDate(new Timestamp(System.currentTimeMillis()).toString());


                String sql = "insert into transactions (id, date, type, amount, newBalance, associated_account_id) values (?, ?, ?, ?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, newTran.getId());
                pstmt.setString(2, newTran.getDate());
                pstmt.setString(3, newTran.getType());
                pstmt.setDouble(4, newTran.getAmount());
                pstmt.setDouble(5, newTran.getNewBalance());
                pstmt.setString(6, newTran.getOwner().getId());

                int rowsInserted = pstmt.executeUpdate();

                if (rowsInserted != 0) {
                    return newTran;
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

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



    //Withdrawl





}
