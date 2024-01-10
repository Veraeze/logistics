package org.raveralogistics.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.raveralogistics.data.model.Address;
import org.raveralogistics.data.model.Customer;
import org.raveralogistics.data.model.User;
import org.raveralogistics.data.repository.BookingRepository;
import org.raveralogistics.data.repository.FeedbackRepository;
import org.raveralogistics.data.repository.UserRepository;
import org.raveralogistics.dtos.request.*;
import org.raveralogistics.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LogisticServiceTest {
    @Autowired
    LogisticService ravera;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    FeedbackRepository feedbackRepository;

    @AfterEach
    public void delete(){
        userRepository.deleteAll();
        bookingRepository.deleteAll();
        feedbackRepository.deleteAll();
    }

    @Test
    void testThatAUserCanRegister(){

        RegisterRequest registerRequest = request("vera", "08093280641", "veraeze@gmail.com", "password", address("alaka", "lekki", "lagos", "Nigeria", "12001"));
        ravera.register(registerRequest);
        assertEquals(1, userRepository.count());

    }

    @Test
    void testThatExceptionIsThrownWhenUserRegistersWithAnAlreadyRegisteredName() {

        RegisterRequest registerRequest = request("vera", "08093280641", "veraeze@gmail.com", "password", address("alaka", "lekki", "lagos", "Nigeria", "12001"));
        ravera.register(registerRequest);
        assertThrows(UserNameNotAvailable.class, ()-> ravera.register(registerRequest));

    }

    @Test
    void testThatUserCanLogInWithValidNameAndPassword() {

        RegisterRequest registerRequest = request("vera", "08093280641", "veraeze@gmail.com", "password", address("alaka", "lekki", "lagos", "Nigeria", "12001"));
        ravera.register(registerRequest);
        assertFalse(ravera.findAccountBelongingTo("vera").isLoggedIn());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setName("vera");
        loginRequest.setPassword("password");

        ravera.login(loginRequest);
        assertTrue(ravera.findAccountBelongingTo("vera").isLoggedIn());

    }

    @Test
    void testExceptionIsThrown_UserTriesToLogInWithWrongPassword() {

        RegisterRequest registerRequest = request("vera", "08093280641", "veraeze@gmail.com", "password", address("alaka", "lekki", "lagos", "Nigeria", "12001"));
        ravera.register(registerRequest);
        assertFalse(ravera.findAccountBelongingTo("vera").isLoggedIn());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setName("vera");
        loginRequest.setPassword("passwordsss");

        assertThrows(IncorrectDetails.class, ()-> ravera.login(loginRequest));

    }

    @Test
    void testExceptionIsThrown_UserTriesToLogInWithWrongName() {

        RegisterRequest registerRequest = request("vera", "08093280641", "veraeze@gmail.com", "password", address("alaka", "lekki", "lagos", "Nigeria", "12001"));
        ravera.register(registerRequest);
        assertFalse(ravera.findAccountBelongingTo("vera").isLoggedIn());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setName("verant");
        loginRequest.setPassword("password");

        assertThrows(IncorrectDetails.class, ()-> ravera.login(loginRequest));

    }

    @Test
    void testExceptionIsThrown_UserTriesToLogInWithWrongNameAndPassword() {

        RegisterRequest registerRequest = request("vera", "08093280641", "veraeze@gmail.com", "password", address("alaka", "lekki", "lagos", "Nigeria", "12001"));
        ravera.register(registerRequest);
        assertFalse(ravera.findAccountBelongingTo("vera").isLoggedIn());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setName("verant");
        loginRequest.setPassword("passwordss");

        assertThrows(IncorrectDetails.class, ()-> ravera.login(loginRequest));

    }

    @Test
    void testThatUserCanLogOut() {

        RegisterRequest registerRequest = request("vera", "08093280641", "veraeze@gmail.com", "password", address("alaka", "lekki", "lagos", "Nigeria", "12001"));
        ravera.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setName("vera");
        loginRequest.setPassword("password");
        ravera.login(loginRequest);
        assertTrue(ravera.findAccountBelongingTo("vera").isLoggedIn());

        ravera.logout(loginRequest);
        assertFalse(ravera.findAccountBelongingTo("vera").isLoggedIn());

    }

    @Test
    void testThatRegisteredUserCanDepositMoneyIntoWalletAfterSuccessfulLogin() {

        RegisterRequest registerRequest = request("vera", "08093280641", "veraeze@gmail.com", "password", address("alaka", "lekki", "lagos", "Nigeria", "12001"));
        ravera.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setName("vera");
        loginRequest.setPassword("password");
        ravera.login(loginRequest);
        assertTrue(ravera.findAccountBelongingTo("vera").isLoggedIn());

        User user = ravera.findAccountBelongingTo("vera");

        DepositMoneyRequest depositMoneyRequest = new DepositMoneyRequest();
        depositMoneyRequest.setUserId(user.getUserId());
        depositMoneyRequest.setAmount(BigDecimal.valueOf(3000));
        ravera.depositMoneyIntoWallet(depositMoneyRequest);
        assertEquals(BigDecimal.valueOf(3000), userRepository.findUserBy(user.getUserId()).getWallet().getBalance());

    }

    @Test
    void testThatExceptionIsThrown_DepositMoneyIntoWalletWithoutLogin() {

        RegisterRequest registerRequest = request("vera", "08093280641", "veraeze@gmail.com", "password", address("alaka", "lekki", "lagos", "Nigeria", "12001"));
        ravera.register(registerRequest);

        User user = ravera.findAccountBelongingTo("vera");

        DepositMoneyRequest depositMoneyRequest = new DepositMoneyRequest();
        depositMoneyRequest.setUserId(user.getUserId());
        depositMoneyRequest.setAmount(BigDecimal.valueOf(3000));

        assertThrows(LoginError.class, ()-> ravera.depositMoneyIntoWallet(depositMoneyRequest));
    }

    @Test
    void testThatExceptionIsThrown_DepositMoneyLessThan0_AfterSuccessfulLogin() {

        RegisterRequest registerRequest = request("susan", "08093280634", "veraeze18@gmail.com", "pin", address("pentville", "maitama", "abuja", "nigeria", "11002"));
        ravera.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setName("susan");
        loginRequest.setPassword("pin");
        ravera.login(loginRequest);
        assertTrue(ravera.findAccountBelongingTo("susan").isLoggedIn());

        User user = ravera.findAccountBelongingTo("susan");

        DepositMoneyRequest depositMoneyRequest = new DepositMoneyRequest();
        depositMoneyRequest.setUserId(user.getUserId());
        depositMoneyRequest.setAmount(BigDecimal.valueOf(-1000));

        assertThrows(InvalidAmount.class, ()-> ravera.depositMoneyIntoWallet(depositMoneyRequest));

    }

    @Test
    void testThatRegisteredUserCanLogin_DepositMoneyIntoWallet_WithdrawMoneyFromWallet() {

        RegisterRequest registerRequest = request("vera", "08093280641", "veraeze@gmail.com", "password", address("alaka", "lekki", "lagos", "Nigeria", "12001"));
        ravera.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setName("vera");
        loginRequest.setPassword("password");
        ravera.login(loginRequest);
        assertTrue(ravera.findAccountBelongingTo("vera").isLoggedIn());

        User user = ravera.findAccountBelongingTo("vera");

        DepositMoneyRequest depositMoneyRequest = new DepositMoneyRequest();
        depositMoneyRequest.setUserId(user.getUserId());
        depositMoneyRequest.setAmount(BigDecimal.valueOf(3000));

        ravera.depositMoneyIntoWallet(depositMoneyRequest);
        assertEquals(BigDecimal.valueOf(3000), userRepository.findUserBy(user.getUserId()).getWallet().getBalance());

        ravera.withdrawMoneyFromWallet(user.getUserId(), BigDecimal.valueOf(1000));
        assertEquals(BigDecimal.valueOf(2000), userRepository.findUserBy(user.getUserId()).getWallet().getBalance());

    }

    @Test
    void testThatExceptionIsThrown_RegisteredUserCanLogin_DepositMoneyIntoWallet_WithdrawMoneyGreaterThanBalance() {

        RegisterRequest registerRequest = request("vera", "08093280641", "veraeze@gmail.com", "password", address("alaka", "lekki", "lagos", "Nigeria", "12001"));
        ravera.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setName("vera");
        loginRequest.setPassword("password");
        ravera.login(loginRequest);
        assertTrue(ravera.findAccountBelongingTo("vera").isLoggedIn());

        User user = ravera.findAccountBelongingTo("vera");

        DepositMoneyRequest depositMoneyRequest = new DepositMoneyRequest();
        depositMoneyRequest.setUserId(user.getUserId());
        depositMoneyRequest.setAmount(BigDecimal.valueOf(3000));

        ravera.depositMoneyIntoWallet(depositMoneyRequest);
        assertEquals(BigDecimal.valueOf(3000), userRepository.findUserBy(user.getUserId()).getWallet().getBalance());

        assertThrows(InsufficientFunds.class, ()->ravera.withdrawMoneyFromWallet(user.getUserId(), BigDecimal.valueOf(5000)));

    }

    @Test
    void testThatExceptionIsThrown_RegisteredUserCanLogin_DepositMoneyIntoWallet_WithdrawAmountLessThan0() {

        RegisterRequest registerRequest = request("vera", "08093280641", "veraeze@gmail.com", "password", address("alaka", "lekki", "lagos", "Nigeria", "12001"));
        ravera.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setName("vera");
        loginRequest.setPassword("password");
        ravera.login(loginRequest);
        assertTrue(ravera.findAccountBelongingTo("vera").isLoggedIn());

        User user = ravera.findAccountBelongingTo("vera");

        DepositMoneyRequest depositMoneyRequest = new DepositMoneyRequest();
        depositMoneyRequest.setUserId(user.getUserId());
        depositMoneyRequest.setAmount(BigDecimal.valueOf(3000));

        ravera.depositMoneyIntoWallet(depositMoneyRequest);
        assertEquals(BigDecimal.valueOf(3000), userRepository.findUserBy(user.getUserId()).getWallet().getBalance());

        assertThrows(InvalidAmount.class, ()->ravera.withdrawMoneyFromWallet(user.getUserId(), BigDecimal.valueOf(-2000)));

    }



    @Test
    void testThatUserCanLogInAndBookAService() {

        RegisterRequest registerRequest = request("vera", "08093280641", "veraeze@gmail.com", "password", address("alaka", "lekki", "lagos", "Nigeria", "12001"));
        ravera.register(registerRequest);
        assertFalse(ravera.findAccountBelongingTo("vera").isLoggedIn());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setName("vera");
        loginRequest.setPassword("password");

        ravera.login(loginRequest);
        assertTrue(ravera.findAccountBelongingTo("vera").isLoggedIn());





    }

    private static Address address(String street, String city, String state, String country, String zipCode){
        Address homeAddress = new Address();
        homeAddress.setStreet(street);
        homeAddress.setCity(city);
        homeAddress.setState(state);
        homeAddress.setCountry(country);
        homeAddress.setZipCode(zipCode);
        return homeAddress;
    }

    private static RegisterRequest request(String name, String phoneNumber, String email, String password, Address homeAddress){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setName(name);
        registerRequest.setPhoneNumber(phoneNumber);
        registerRequest.setEmail(email);
        registerRequest.setPassword(password);
        registerRequest.setHomeAddress(homeAddress);
        return registerRequest;
    }
}