package com.RDABank.RDABank.Repository;

import com.RDABank.RDABank.Models.NameDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NameDetailsRepo extends MongoRepository<NameDetails,Long> {
}
