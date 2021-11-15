package com.revature.banking.services;


import com.revature.banking.DAOS.AppUserDAO;
import com.revature.banking.Exceptions.AuthenticationException;
import com.revature.banking.Exceptions.InvalidRequestException;
import com.revature.banking.Services.UserService;
import com.revature.banking.models.AppUser;
import com.revature.banking.util.logging.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class UserServiceTestSuite {


    // System Under Test
    //UserService sut = new UserService();

    UserService sut;
    AppUserDAO mockUserDAO;
    Logger logger = new Logger(false);

    /*
        JUnit Annotations
            - @Before (runs before each test case)
            - @After (runs after each test case)
            - @BeforeClass (runs once before all test cases)
            - @AfterClass (runs once after all test cases)
            - @Test (marks a method in a test suite as a test case)
            - @Ignore (indicates that the annotated test case should be skipped)
     */

    @Before
    public void testCaseSetup() {
        mockUserDAO = mock(AppUserDAO.class);
        sut = new UserService(mockUserDAO, logger);

    }

    @After
    public void testCaseCleanUp() {
        sut = null;
    }

    @Test
    public void test_getSessionUser_returnsNull_given_No_Stored_SessionUser() {


    }

    @Test
    public void test_isUserValid_returnsTrue_givenValidUser() {

        // AAA pattern: Arrange, Act, Assert

        // Arrange
        AppUser validUser = new AppUser("valid", "valid", "valid", "valid", "valid");

        // Act
        boolean actualResult = sut.isUserValid(validUser);

        // Assert
        Assert.assertTrue("Expected user to be considered valid", actualResult);

    }

    @Test
    public void test_isUserValid_returnsFalse_givenUserWithInvalidFirstName() {

        // Arrange
        AppUser invalidUser_1 = new AppUser(null, "valid", "valid", "valid", "valid");
        AppUser invalidUser_2 = new AppUser("", "valid", "valid", "valid", "valid");
        AppUser invalidUser_3 = new AppUser("             ", "valid", "valid", "valid", "valid");

        // Act
        boolean actualResult_1 = sut.isUserValid(invalidUser_1);
        boolean actualResult_2 = sut.isUserValid(invalidUser_2);
        boolean actualResult_3 = sut.isUserValid(invalidUser_3);

        // Assert
        Assert.assertFalse("Expected user to be considered false.", actualResult_1);
        Assert.assertFalse("Expected user to be considered false.", actualResult_2);
        Assert.assertFalse("Expected user to be considered false.", actualResult_3);

    }

    // TODO correct implementation so that UserService#registerNewUser is tested in isolation (hint: mock/fake the AppUserDAO)
    @Test
    public void test_registerNewUser_returnsTrue_givenValidUser() {

        // Arrange
        AppUser validUser = new AppUser("valid", "valid", "valid", "valid", "valid");
        when(mockUserDAO.findUserByUsername(validUser.getUsername())).thenReturn(null);
        when(mockUserDAO.findUserByEmail(validUser.getEmail())).thenReturn(null);
        when(mockUserDAO.save(validUser)).thenReturn(validUser);

        // Act
        boolean actualResult = sut.registerNewUser(validUser);

        // Assert
        Assert.assertTrue("Expected result to be true with valid user provided.", actualResult);
        verify(mockUserDAO, times(1)).save(validUser);

    }

    @Test (expected = InvalidRequestException.class)
    public void test_authenticateUser_throwsException_givenInvalidUsernameAndPassword(){
        // Arrange
        String username = "";
        String password = "";
        String username_2 = null;

        // Act
        try{
            sut.authenticateUser(username, password);
        } finally {
            verify(mockUserDAO, times(0)).findUserByUsernameAndPassword(username, password);
        }

        try{
            sut.authenticateUser(username_2, password);
        } finally {
            verify(mockUserDAO, times(0)).findUserByUsernameAndPassword(username_2, password);
        }

        // Assert
        //What would go here. The test is working. Just a printed out message?

    }





    @Test
    public void test_registerNewUser_throwsResourcePersistenceException_givenValidUserWithTakenUsername() {

    }

    @Test
    public void test_registerNewUser_throwsResourcePersistenceException_givenValidUserWithTakenEmail() {

    }

    @Test(expected = InvalidRequestException.class)
    public void test_registerNewUser_throwsInvalidRequestException_givenInvalidUser() {
        sut.registerNewUser(null);
    }

    // TODO implement test case
    @Test
    public void test_registerNewUser_throwsInvalidRequestException_givenUserWithDuplicatedEmailOrUsername() {

        // Arrange

        // Act

        // Assert

    }





}
