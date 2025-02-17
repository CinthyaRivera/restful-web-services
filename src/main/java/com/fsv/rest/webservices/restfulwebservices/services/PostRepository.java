package com.fsv.rest.webservices.restfulwebservices.services;

import com.fsv.rest.webservices.restfulwebservices.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {


}
