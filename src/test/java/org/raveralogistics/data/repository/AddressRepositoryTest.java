package org.raveralogistics.data.repository;

import org.junit.jupiter.api.Test;
import org.raveralogistics.data.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AddressRepositoryTest {
    @Autowired
    AddressRepository addressRepository;

    @Test
    void countIsOneWhenYouSaveOne(){
        Address address = new Address();
        addressRepository.save(address);
        assertEquals(1, addressRepository.count());
    }
}