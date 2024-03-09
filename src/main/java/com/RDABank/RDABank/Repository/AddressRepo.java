package com.RDABank.RDABank.Repository;

import com.RDABank.RDABank.Models.Address;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AddressRepo extends MongoRepository<Address,Long> {
}
