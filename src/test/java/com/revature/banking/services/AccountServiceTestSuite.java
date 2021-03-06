package com.revature.banking.services;

import com.revature.banking.DAOS.AccountDAO;
import com.revature.banking.exceptions.AuthenticationException;
import com.revature.banking.exceptions.AuthorizationException;
import com.revature.banking.exceptions.InvalidRequestException;
import com.revature.banking.exceptions.ResourcePersistenceException;
import com.revature.banking.models.Account;
import com.revature.banking.models.AppUser;
import com.revature.banking.util.collections.LinkedList;
import com.revature.banking.util.collections.List;
import com.revature.banking.util.logging.Logger;
import org.junit.*;

import static org.mockito.Mockito.*;

public class AccountServiceTestSuite {

    AccountService sut;
    UserService mockUserService;
    AccountDAO mockAccountDAO;
    TransactionService mockTransactionService;
    Logger mockLogger;

    @Before
    public void testCaseSetup() {
        mockAccountDAO = mock(AccountDAO.class);
        mockUserService = mock(UserService.class);
        mockTransactionService = mock(TransactionService.class);
        mockLogger = mock(Logger.class);
        sut = new AccountService(mockAccountDAO, mockUserService, mockLogger);

    }

    @After
    public void testCaseCleanUp() {
        sut = null;
    }

//Test isAccountValid
    //Test if session is not active get an exception
    @Test (expected = AuthorizationException.class)
    public void test_if_isSessionActive_returnsFalse_an_authorizationException_is_thrown(){

        //Arrange
        List accountList = new LinkedList();
        when(mockUserService.isSessionActive()).thenReturn(false);

        //Act
        try{
           accountList = sut.findMyAccounts();
        }finally{
            verify(mockAccountDAO, times(0)).findAccountsByOwnerId("valid");
        }

    }

    //Test if session is active, a list is given.
    //Test if session is not active get an exception
    @Test //(expected = RuntimeException.class)
    public void test_if_isSessionActive_returns_throws_exception_if_no_accounts(){

        //Arrange
        List<Account> accountList = new LinkedList<>();
        List<Account> secondList = new LinkedList<>();
        Account appAccount = new Account();
        AppUser validUser = new AppUser("id", "valid", "valid", "valid", "valid", "valid");
        secondList.add(appAccount);
        when(mockUserService.isSessionActive()).thenReturn(true);
        when(mockUserService.getSessionUser()).thenReturn(validUser);
        when(mockAccountDAO.findAccountsByOwnerId(mockUserService.getSessionUser().getId())).thenReturn(secondList);

        //Act
        try{
            accountList = sut.findMyAccounts();
        }finally{
           Assert.assertNotNull(accountList);
        }

    }
//Test createNewAccount
    //Test Throws InvalidRequest if account is not valid
    @Ignore
    @Test (expected = InvalidRequestException.class)
    public void test_if_isSessionActive_returnsFalse_throw_InvalidRequestException(){

        //Arrange
        List accountList = new LinkedList();
        AppUser validUser = new AppUser("id", "valid", "valid", "valid", "valid", "valid");
        Account dummyAccount = new Account();
        when(mockUserService.isSessionActive()).thenReturn(true);
        when(mockUserService.getSessionUser()).thenReturn(validUser);


        //Act
        try{
           sut.createNewAccount(dummyAccount);
        }finally{
            verify(mockAccountDAO, times(0)).save(dummyAccount);
        }

    }

    @Test (expected = ResourcePersistenceException.class)
    public void test_if_isSessionActive_returnsTrue_and_CreatedAccountEquals_Null_Throw_Exception(){

        //Arrange
        List accountList = new LinkedList();
        AppUser validUser = new AppUser("Valid", "valid", "valid", "valid", "valid", "valid");
        Account dummyAccount = new Account();
        Account savedDummy = new Account();

        when(sut.isAccountValid(dummyAccount)).thenReturn(true);
       // when(dummyAccount.setOwner(mockUserService.getSessionUser())).thenReturn(validUser);
        when(mockAccountDAO.save(dummyAccount)).thenReturn(null);

        //Act
        try{
            sut.createNewAccount(dummyAccount);
        }finally{
            //What can I verify here.
        }

    }

//Test updateOldAccount

//Test findMyAccounts

}
