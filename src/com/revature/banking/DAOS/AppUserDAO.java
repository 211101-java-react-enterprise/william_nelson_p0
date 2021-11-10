package com.revature.banking.DAOS;


import com.revature.banking.models.AppUser;
import com.revature.banking.utils.ConnectionFactory;
import com.revature.banking.utils.List;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;


public class AppUserDAO implements CrudDAO<AppUser> {

    public AppUser findUserByUsernameAndPassword(String username, String password) {

        try (BufferedReader dataReader = new BufferedReader(new FileReader("resources/data.txt"))) {

            String dataCursor;
            while((dataCursor = dataReader.readLine()) != null) {
                String[] userData = dataCursor.split(":");
                if (userData[4].equals(username) && userData[5].equals(password)) {
                    return new AppUser(userData[0], userData[1], userData[2], userData[3], userData[4], userData[5]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public AppUser save(AppUser newUser) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            newUser.setId(UUID.randomUUID().toString());

            String sql = "insert into app_users (id, first_name, last_name, email, username, password) values (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newUser.getId());
            pstmt.setString(2, newUser.getFirstName());
            pstmt.setString(3, newUser.getLastName());
            pstmt.setString(4, newUser.getEmail());
            pstmt.setString(5, newUser.getUsername());
            pstmt.setString(6, newUser.getPassword());

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted != 0) {
                return newUser;
            }

        } catch (SQLException e) {
            // TODO log this and throw our own custom exception to be caught in the service layer
            e.printStackTrace();

        }

        return null;

    }

    @Override
    public List<AppUser> findAll() {
        return null;
    }

    @Override
    public AppUser findById(String id) {
        return null;
    }

    @Override
    public boolean update(AppUser updatedObj) {
        return false;
    }

    @Override
    public boolean removeById(String id) {
        return false;
    }
}



