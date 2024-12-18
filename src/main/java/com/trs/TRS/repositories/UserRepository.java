package com.trs.TRS.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.trs.TRS.models.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findByUsername(String username);

    User findByEmail(String email);

    // User findByUsernameAndEmail(String username, String email);
}
