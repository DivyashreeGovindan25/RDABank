package com.RDABank.RDABank.Repository;

import com.RDABank.RDABank.Models.NameDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NameDetailsRepo extends MongoRepository<NameDetails,Long> {
}
