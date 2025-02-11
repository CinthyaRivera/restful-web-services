package com.fsv.rest.webservices.restfulwebservices.controllers;

import com.fsv.rest.webservices.restfulwebservices.exception.BadRequest;
import com.fsv.rest.webservices.restfulwebservices.exception.NotFound;
import com.fsv.rest.webservices.restfulwebservices.models.User;
import com.fsv.rest.webservices.restfulwebservices.services.UserDaoService;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.ArrayList;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@RestController
public class UserResourceController {

    private UserDaoService service;

    public UserResourceController(UserDaoService service) {

        this.service = service;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {

        return service.findAll();

    }

    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id) {

        User user = service.findOne(id);

        if (user == null) {

            throw new NotFound("id:" + id);
        }

        if (!isInteger(id)) {
            throw new BadRequest("id:" + id);
        }

        EntityModel<User> entityModel = EntityModel.of(user);

        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());

        entityModel.add(link.withRel("all-users"));

        return entityModel;

        //return service.findOne(id);

    }

    private boolean isInteger(int input) {
        try {
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {

        service.deleteById(id);

    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = service.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(location).build();

    }
}