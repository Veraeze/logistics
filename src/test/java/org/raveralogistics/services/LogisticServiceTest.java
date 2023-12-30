package org.raveralogistics.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.raveralogistics.data.model.Address;
import org.raveralogistics.data.repository.UserRepository;
import org.raveralogistics.dtos.request.LoginRequest;
import org.raveralogistics.dtos.request.RegisterRequest;
import org.raveralogistics.exceptions.IncorrectDetails;
import org.raveralogistics.exceptions.UserNameNotAvailable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LogisticServiceTest {
    @Autowired
LogisticService ravera;
    @Autowired
UserRepository userRepository;

    @AfterEach
    public void delete(){
        userRepository.deleteAll();
    }

    @Test
    public void testThatTheUserRepositorySizeIncreasesByOneWhenAUserIsRegistered(){
        Address homeAddress = new Address();
        homeAddress.setCity("lekki");
        homeAddress.setState("lagos");
        homeAddress.setCountry("Nigeria");
        homeAddress.setZipCode("12001");
        homeAddress.setStreet("alaka");

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("veraeze@gmail.com");
        registerRequest.setName("vera");
        registerRequest.setPassword("password");
        registerRequest.setHomeAddress(homeAddress);
        registerRequest.setPhoneNumber("08093280641");

        ravera.register(registerRequest);
        assertEquals(1, userRepository.count());
    }

    @Test
    public void testThatExceptionIsThrownWhenUserRegistersWithAnAlreadyRegisteredName() {
        Address homeAddress = new Address();
        homeAddress.setCity("lekki");
        homeAddress.setState("lagos");
        homeAddress.setCountry("Nigeria");
        homeAddress.setZipCode("12001");
        homeAddress.setStreet("alaka");

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("veraeze@gmail.com");
        registerRequest.setName("vera");
        registerRequest.setPassword("password");
        registerRequest.setHomeAddress(homeAddress);
        registerRequest.setPhoneNumber("08093280641");

        ravera.register(registerRequest);
        assertThrows(UserNameNotAvailable.class, ()-> ravera.register(registerRequest));
    }

    @Test
    public void testThatUserCanLogInWithNameAndPassword() {
        Address homeAddress = new Address();
        homeAddress.setCity("lekki");
        homeAddress.setState("lagos");
        homeAddress.setCountry("Nigeria");
        homeAddress.setZipCode("12001");
        homeAddress.setStreet("alaka");

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("veraeze@gmail.com");
        registerRequest.setName("vera");
        registerRequest.setPassword("password");
        registerRequest.setHomeAddress(homeAddress);
        registerRequest.setPhoneNumber("08093280641");

        ravera.register(registerRequest);
        assertFalse(ravera.findAccountBelongingTo("vera").isLoggedIn());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setName("vera");
        loginRequest.setPassword("password");

        ravera.login(loginRequest);
        assertTrue(ravera.findAccountBelongingTo("vera").isLoggedIn());

    }

    @Test
    public void testExceptionIsThrown_UserTriesToLogInWithWrongPassword() {
        Address homeAddress = new Address();
        homeAddress.setCity("lekki");
        homeAddress.setState("lagos");
        homeAddress.setCountry("Nigeria");
        homeAddress.setZipCode("12001");
        homeAddress.setStreet("alaka");

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("veraeze@gmail.com");
        registerRequest.setName("vera");
        registerRequest.setPassword("password");
        registerRequest.setHomeAddress(homeAddress);
        registerRequest.setPhoneNumber("08093280641");

        ravera.register(registerRequest);
        assertFalse(ravera.findAccountBelongingTo("vera").isLoggedIn());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setName("vera");
        loginRequest.setPassword("passwordsss");

        assertThrows(IncorrectDetails.class, ()-> ravera.login(loginRequest));
    }

}