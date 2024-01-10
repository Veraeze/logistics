package org.raveralogistics.data.repository;

import org.raveralogistics.data.model.Address;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AddressRepository extends MongoRepository<Address, String> {

}
