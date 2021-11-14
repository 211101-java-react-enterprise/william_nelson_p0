package com.revature.banking.Services;

import com.revature.banking.DAOS.AppUserDAO;
import com.revature.banking.Exceptions.AuthenticationException;
import com.revature.banking.Exceptions.InvalidRequestException;
import com.revature.banking.Exceptions.ResourcePersistenceException;
import com.revature.banking.models.AppUser;
import com.revature.banking.util.logging.Logger;

//TODO Write Tests and Figure Out why they don't work.
public class UserService {

    //Appuser DAO
    //AppUser to be passed to screens to track user and loggin status
    //List of Accounts that can be passed through the program
    private final AppUserDAO userDAO;
    private AppUser sessionUser;
    private final Logger logger;

    public UserService(AppUserDAO userDAO, Logger logger) {
        this.userDAO = userDAO;
        this.sessionUser = null;
        this.logger = logger;
    }

    //A way to get the session user for tracking loggin functions
    public AppUser getSessionUser() {
        return sessionUser;
    }

    //Register Function that returns true if a valid user is registered.
    public boolean registerNewUser(AppUser newUser) {

        if(!isUserValid(newUser)){
            //If the validation function results in false, Throw and Exception
            logger.log("Invalid user data provided");
            throw new InvalidRequestException("Invalid user data provided!");
        }

        //Ensure that the Username and Email are unique to the DB by making boolean fields when
        //The register function is called. If they are still null or Eval to False the names/emails
        //Not usable.
        boolean usernameAvailable = userDAO.findUserByUsername(newUser.getUsername()) == null;
        boolean emailAvailable = userDAO.findUserByEmail(newUser.getEmail()) == null;


        //If they aren't usable, print which is/are not usable and yeet exception
        if (!usernameAvailable || !emailAvailable)
        {
            String msg = "The provided username was already taken in the datasource:";
            if (!usernameAvailable) msg = msg + "\n\t- username";
            if (!emailAvailable) msg = msg + "\n\t- email";
            throw new ResourcePersistenceException(msg);
        }

        logger.log("User not in Database. Writing to Database");
        //Both should be fine and allow for user to be saved to DB.
        AppUser registeredUser = userDAO.save(newUser);

        //If the Registered user is null, throw an exception.
        if (registeredUser == null){
            //Throw exception if the Registered User ends up Null: Somehow failed the user save or
            //Authenticate issue.
            String msg = "The user could not be persisted to the datasource!";
            logger.log(msg);
            throw new ResourcePersistenceException(msg);
        }

        return true;

    }

    //This Function authenticates the data given based on a username and password,
    // and if it is valid input, sets the session user to the Authenticated user.
    //Dashboard will use this user to determine if login was successful.
    public void authenticateUser(String username, String password) {

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
        sessionUser = authenticatedUser;


    }

    public void logout() {
        sessionUser = null;
    }

    public boolean isSessionActive() {
        return sessionUser != null;
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
