package org.raveralogistics.data.repository;

import org.raveralogistics.data.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findUserBy(String name);
}
