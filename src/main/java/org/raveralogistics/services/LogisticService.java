package org.raveralogistics.services;

import org.raveralogistics.data.model.User;
import org.raveralogistics.dtos.request.LoginRequest;
import org.raveralogistics.dtos.request.RegisterRequest;

public interface LogisticService {
    User register(RegisterRequest registerRequest);

    void login(LoginRequest loginRequest);

    User findAccountBelongingTo(String name);
}