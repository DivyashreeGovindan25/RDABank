package com.RDABank.RDABank.Repository;

import com.RDABank.RDABank.Models.Address;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepo extends MongoRepository<Address,Long> {
}
