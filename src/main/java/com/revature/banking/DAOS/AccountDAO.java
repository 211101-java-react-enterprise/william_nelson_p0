package com.revature.banking.DAOS;

import com.revature.banking.models.Account;
import com.revature.banking.models.AppUser;
import com.revature.banking.util.database.ConnectionFactory;
import com.revature.banking.util.collections.LinkedList;
import com.revature.banking.util.collections.List;

import java.sql.*;
import java.util.UUID;

public class AccountDAO implements CrudDAO<Account> {


    public List<Account> findAccountsByOwnerId(String userId) {

        List<Account> accounts = new LinkedList<>();

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = "select * from accounts a join app_users au on a.owner_id = au.id where au.id = ?";
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
               accountCreator.setId(rs.getString("owner_id"));
               accountCreator.setFirstName(rs.getString("first_name"));
               accountCreator.setLastName(rs.getString("last_name"));
               accountCreator.setEmail(rs.getString("email"));
               accountCreator.setUsername(rs.getString("username"));
               accountCreator.setEmail(rs.getString("email"));
               account.setName(rs.getString("name"));
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

            String sql = "insert into accounts (id, name, type, balance, owner_id) values (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newAccount.getId());
            pstmt.setString(2, newAccount.getName());
            pstmt.setString(3, newAccount.getType());
            pstmt.setDouble(4, newAccount.getBalance());
            pstmt.setString(5, newAccount.getOwner().getId());

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
    public boolean update(Account account) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = "update accounts set name = ?, type = ?, balance = ? where owner_id = ? and id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, account.getName());
            pstmt.setString(2, account.getType());
            pstmt.setDouble(3, account.getBalance());
            pstmt.setString(4, account.getOwner().getId());
            pstmt.setString(5, account.getId());

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted != 0) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }



    @Override
    public Account findById(String id) {
        return null;
    }

    //Maybe Implement, Maybe not an online feature. Ask Wezley

    @Override
    public boolean removeById(String id) {
        return false;
    }
}
