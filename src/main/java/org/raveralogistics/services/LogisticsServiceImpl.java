package org.raveralogistics.services;

import org.raveralogistics.data.model.User;
import org.raveralogistics.data.model.Wallet;
import org.raveralogistics.data.repository.UserRepository;
import org.raveralogistics.dtos.request.DepositMoneyRequest;
import org.raveralogistics.dtos.request.LoginRequest;
import org.raveralogistics.dtos.request.RegisterRequest;
import org.raveralogistics.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static org.raveralogistics.utils.Mapper.*;

@Service
public class LogisticsServiceImpl implements LogisticService{
    @Autowired
    UserRepository userRepository;
    @Override
    public User register(RegisterRequest registerRequest) {
        if (validateUsername(registerRequest.getName())) throw new UserNameNotAvailable(registerRequest.getName() + " already exists!");

        User user = map(registerRequest.getName(), registerRequest.getPassword(),registerRequest.getPhoneNumber(),
                registerRequest.getEmail(), registerRequest.getHomeAddress(), String.valueOf(userRepository.count()+1));

        Wallet wallet = new Wallet();
        user.setWallet(wallet);

        userRepository.save(user);
        return user;
    }

    @Override
    public void login(LoginRequest loginRequest) {
      User user = userRepository.findUserBy(loginRequest.getName());
      if (!validateUsername(loginRequest.getName())){
          throw new AccountDoesNotExist("Account with this username does not exist!");
      }
      if (!loginRequest.getName().equals(user.getName())){
          throw new IncorrectDetails("Invalid username or password");
      }
      if (!loginRequest.getPassword().equals(user.getPassword())){
          throw new IncorrectDetails("Invalid username or password");
      }
      user.setLoggedIn(true);
      userRepository.save(user);
    }



    @Override
    public User findAccountBelongingTo(String name) {
        User user = userRepository.findUserBy(name);
        if (user == null) throw new AccountDoesNotExist("Account with this username does not exist!");
        return user;
    }

    @Override
    public void logout(LoginRequest loginRequest) {
        User user = userRepository.findUserBy(loginRequest.getName());
        user.setLoggedIn(false);
        userRepository.save(user);
    }

    @Override
    public void depositMoneyIntoWallet(DepositMoneyRequest depositMoneyRequest) {

        User user = userRepository.findUserBy(depositMoneyRequest.getUserId());

        if (user == null) throw new AccountDoesNotExist("Account with this username does not exist!");
        if(!user.isLoggedIn()) throw new LoginError("Login to perform this action");

        Wallet wallet = user.getWallet();
        BigDecimal walletBalance = wallet.getBalance();

        validateAmount(depositMoneyRequest.getAmount());
        wallet.setBalance(walletBalance.add(depositMoneyRequest.getAmount()));

        userRepository.save(user);
    }

    @Override
    public void withdrawMoneyFromWallet(String userId, BigDecimal amount) {
        User user = userRepository.findUserBy(userId);

        if (user == null) throw new AccountDoesNotExist("Account with this username does not exist!");
        if(!user.isLoggedIn()) throw new LoginError("Login to perform this action");

        Wallet wallet = user.getWallet();
        BigDecimal walletBalance = wallet.getBalance();

        validateAmount(amount);
        validateSufficientFund(walletBalance, amount);
        wallet.setBalance(walletBalance.subtract(amount));

        userRepository.save(user);
    }

    public boolean validateUsername(String userName){
        User user = userRepository.findUserBy(userName);
        return user != null;
    }

    private void validateAmount(BigDecimal amount){
        if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new InvalidAmount("Error!, amount must be greater than 0 \nPlease try again");
    }

    private void validateSufficientFund(BigDecimal balance,BigDecimal amount){
        if (balance.compareTo(amount)  < 0) throw new InsufficientFunds("Error! wallet balance is not sufficient to withdraw this amount");
    }

}
