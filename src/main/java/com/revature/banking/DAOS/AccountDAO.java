package com.revature.banking.DAOS;

import com.revature.banking.models.Account;
import com.revature.banking.models.AppUser;
import com.revature.banking.util.ConnectionFactory;
import com.revature.banking.util.LinkedList;
import com.revature.banking.util.List;

import java.sql.*;
import java.util.UUID;

public class AccountDAO implements CrudDAO<Account> {


    public List<Account> findAccountsByOwnerId(String userId) {

        List<Account> accounts = new LinkedList<>();

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = "select * from accounts where owner_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();

            //loop all cards found into list
           while (rs.next()) {
               //Account to be inserted into list
               Account account = new Account();
               //User to be inserted into account
               AppUser accountCreator = new AppUser();
               //setting account settings in holder account
               account.setId(rs.getString("id"));
               account.setType(rs.getString("type"));
               account.setBalance(rs.getDouble("balance"));
               accountCreator.setId(rs.getString("id"));
               accountCreator.setFirstName(rs.getString("first_name"));
               accountCreator.setLastName(rs.getString("last_name"));
               accountCreator.setEmail(rs.getString("email"));
               accountCreator.setUsername(rs.getString("username"));
               accountCreator.setEmail(rs.getString("email"));
               account.setOwner(accountCreator);
               accounts.add(account);
           }



        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Give list
        return accounts;

    }

    @Override
    public Account save(Account newAccount) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            //They will Give Account Type,
            //User Session will have Owner id
            //Need to Provide ID

            //Create new Account ID
            newAccount.setId(UUID.randomUUID().toString());

            String sql = "insert into accounts (id, type, balance, owner_id) values (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newAccount.getId());
            pstmt.setString(2, newAccount.getType());
            pstmt.setDouble(3, newAccount.getBalance());
            pstmt.setString(4, newAccount.getOwner().getId());

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted != 0) {
                return newAccount;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    public List<Account> findAll() {

            List<Account> accounts = new LinkedList<>();

            try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

                String sql = "select * from accounts a join app_users u on f.creator_id = u.id";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                //loop all cards found into list
                while (rs.next()) {
                    //Account to be inserted into list
                    Account account = new Account();
                    //User to be inserted into account
                    AppUser accountCreator = new AppUser();
                    //setting account settings in holder account
                    account.setId(rs.getString("id"));
                    account.setType(rs.getString("type"));
                    account.setBalance(rs.getDouble("balance"));
                    accountCreator.setId(rs.getString("id"));
                    accountCreator.setFirstName(rs.getString("first_name"));
                    accountCreator.setLastName(rs.getString("last_name"));
                    accountCreator.setEmail(rs.getString("email"));
                    accountCreator.setUsername(rs.getString("username"));
                    accountCreator.setEmail(rs.getString("email"));
                    account.setOwner(accountCreator);
                    accounts.add(account);
                }



            } catch (SQLException e) {
                e.printStackTrace();
            }

            //Give list
            return accounts;

        }


    @Override
    public Account findById(String id) {
        return null;
    }

    @Override
    public boolean update(Account updatedObj) {
        return false;
    }

    @Override
    public boolean removeById(String id) {
        return false;
    }
}
