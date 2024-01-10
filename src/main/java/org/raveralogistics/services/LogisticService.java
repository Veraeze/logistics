package org.raveralogistics.services;

import org.raveralogistics.data.model.User;
import org.raveralogistics.dtos.request.DepositMoneyRequest;
import org.raveralogistics.dtos.request.LoginRequest;
import org.raveralogistics.dtos.request.RegisterRequest;

import java.math.BigDecimal;

public interface LogisticService {
    User register(RegisterRequest registerRequest);

    void login(LoginRequest loginRequest);

    User findAccountBelongingTo(String name);

    void logout(LoginRequest loginRequest);

    void depositMoneyIntoWallet(DepositMoneyRequest depositMoneyRequest);

    void withdrawMoneyFromWallet(String userId, BigDecimal bigDecimal);
}
