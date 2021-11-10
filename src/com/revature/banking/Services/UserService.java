package com.revature.banking.Services;

//This is a class to package user information, and call functions related to a user.

import com.revature.banking.DAOS.AppUserDAO;
import com.revature.banking.Exceptions.InvalidRequestException;
import com.revature.banking.Exceptions.ResourcePersistenceException;
import com.revature.banking.models.AppUser;

import javax.naming.AuthenticationException;

public class UserService {

    //Appuser DAO
    private AppUserDAO userDAO = new AppUserDAO();

    //Register Function that returns true if a valid user is registered.
    public boolean registerNewUser(AppUser newUser) {
        //To be Written

        if(!isUserValid(newUser)){
            //If the validation function results in false, Throw and Exception
            throw new InvalidRequestException("Invalid user data provided!");
        }

        //TODO Write Logic that verifies the username and password are not already taken!

        AppUser registeredUser = userDAO.save(newUser);

        //If the Registered user is null, do

        if (registeredUser == null){
            //Throw exception if the Registered User ends up Null: Somehow failed the user save or
            //Authenticate issue.
            throw new ResourcePersistenceException("The user could not be persisted to the datasource!");
        }

        return true;

    }

    //This Function authenticates the data given in an AppUser,
    // and if it is valid input, returns an AppUser
    public AppUser authenticateUser(String username, String password) throws AuthenticationException {

        //Only Executes if the data isn't null or empty
        if (username == null || username.trim().equals("") || password.trim().equals("")){
            throw new InvalidRequestException("Invalid credential values provided!");
        }

        //Returns an Authenticated user and calls the Find function from userDAO.
        AppUser authenticatedUser = userDAO.findUserByUsernameAndPassword(username, password);

        if (authenticatedUser == null){
            throw new AuthenticationException();
        }

        //Return the Authenticated user to the caller of the Authenticate process
        return authenticatedUser;


    }

    //This Function Checks that each field in a given Appuser is not Null, or Empty
    public boolean isUserValid(AppUser user){
        if (user == null) return false;
        if (user.getFirstName() == null || user.getFirstName().trim().equals("")) return false;
        if (user.getLastName() == null || user.getLastName().trim().equals("")) return false;
        if (user.getEmail() == null || user.getEmail().trim().equals("")) return false;
        if (user.getUsername() == null || user.getUsername().trim().equals("")) return false;
        return user.getPassword() != null && !user.getPassword().trim().equals("");
    }



}
