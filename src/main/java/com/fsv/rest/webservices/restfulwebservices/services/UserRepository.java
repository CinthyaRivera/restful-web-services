package com.fsv.rest.webservices.restfulwebservices.services;

import com.fsv.rest.webservices.restfulwebservices.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    
}
