package org.raveralogistics.services;

import org.raveralogistics.data.model.User;
import org.raveralogistics.data.repository.UserRepository;
import org.raveralogistics.dtos.request.LoginRequest;
import org.raveralogistics.dtos.request.RegisterRequest;
import org.raveralogistics.exceptions.AccountDoesNotExist;
import org.raveralogistics.exceptions.IncorrectDetails;
import org.raveralogistics.exceptions.UserNameNotAvailable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.raveralogistics.utils.Mapper.*;

@Service
public class LogisticsServiceImpl implements LogisticService{
    @Autowired
    UserRepository userRepository;
    @Override
    public User register(RegisterRequest registerRequest) {
        if (validate(registerRequest.getName())) throw new UserNameNotAvailable(registerRequest.getName() + " already exists!");
        User user = map(registerRequest.getName(), registerRequest.getPassword(),registerRequest.getPhoneNumber(),
                registerRequest.getEmail(), registerRequest.getHomeAddress(), String.valueOf(userRepository.count()+1));
        userRepository.save(user);
        return user;
    }

    @Override
    public void login(LoginRequest loginRequest) {
      User user = userRepository.findUserBy(loginRequest.getName());
      if (!validate(loginRequest.getName())) throw new AccountDoesNotExist("Account with this username does not exist!");
      if (!loginRequest.getPassword().equals(user.getPassword())) throw new IncorrectDetails("Invalid username or password");
      user.setLoggedIn(true);
      userRepository.save(user);
    }

    @Override
    public User findAccountBelongingTo(String name) {
        User user = userRepository.findUserBy(name);
        if (user == null) throw new AccountDoesNotExist("Account with this username does not exist!");
        return user;
    }

    public boolean validate(String userName){
        User user = userRepository.findUserBy(userName);
        return user != null;
    }
}
