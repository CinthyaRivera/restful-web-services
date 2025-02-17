package com.fsv.rest.webservices.restfulwebservices.controllers;

import com.fsv.rest.webservices.restfulwebservices.exception.BadRequest;
import com.fsv.rest.webservices.restfulwebservices.exception.NotFound;
import com.fsv.rest.webservices.restfulwebservices.models.Post;
import com.fsv.rest.webservices.restfulwebservices.models.User;
import com.fsv.rest.webservices.restfulwebservices.services.PostRepository;
import com.fsv.rest.webservices.restfulwebservices.services.UserRepository;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserJpaResourceController {

    private UserRepository repository;

    private PostRepository postRepository;

    public UserJpaResourceController(UserRepository repository, PostRepository postRepository) {

        this.repository = repository;
        this.postRepository = postRepository;
    }

    @GetMapping("/jpa/v1/users")
    public List<User> retrieveAllUsers() {

        return repository.findAll();

    }

    @GetMapping("/jpa/v1/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id) {

        Optional<User> user = repository.findById(id);

        if (user.isEmpty()) {

            throw new NotFound("id:" + id);
        }

        if (!isInteger(id)) {
            throw new BadRequest("id:" + id);
        }

        EntityModel<User> entityModel = EntityModel.of(user.get());

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

    @DeleteMapping("/jpa/v1/users/{id}")
    public void deleteUser(@PathVariable int id) {

        repository.deleteById(id);

    }

    @PostMapping("/jpa/v1/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = repository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    @GetMapping("/jpa/v1/users/{id}/posts")
    public List<Post> retrievePostsForUser(@PathVariable int id) {

        Optional<User> user = repository.findById(id);

        if (user.isEmpty()) {

            throw new NotFound("id:" + id);
        }

        if (!isInteger(id)) {
            throw new BadRequest("id:" + id);
        }

        return user.get().getPosts();
    }

    @GetMapping("/jpa/v1/users/{id}/posts/{postId}")
    public EntityModel<Post> retrieveUserPost(@PathVariable int id, @PathVariable int postId) {

        Optional<User> userOptional = repository.findById(id);

        if (userOptional.isEmpty()) {

            throw new NotFound("id:" + id);
        }

        User user = userOptional.get();

        Post foundPost = user.getPosts().stream().filter(post -> post.getId().equals((int) postId)).findFirst().orElse(null);

        if (foundPost == null) {
            throw new NotFound("id:" + postId);
        }

        EntityModel<Post> entityModel = EntityModel.of(foundPost);

        WebMvcLinkBuilder linkToPosts = linkTo(methodOn(this.getClass()).retrievePostsForUser(id));

        entityModel.add(linkToPosts.withRel("user-posts"));

        return entityModel;

        //return service.findOne(id);

    }

    @PostMapping("/jpa/v1/users/{id}/posts")
    public ResponseEntity<Object> createPostForUser(@PathVariable int id, @Valid @RequestBody Post post) {

        Optional<User> user = repository.findById(id);

        if (user.isEmpty()) {

            throw new NotFound("id:" + id);
        }

        if (!isInteger(id)) {
            throw new BadRequest("id:" + id);
        }

        post.setUser(user.get());

        Post savedPost = postRepository.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().
                path("/{id}").
                buildAndExpand(savedPost.getId()).
                toUri();

        return ResponseEntity.created(location).build();

    }

    @DeleteMapping("/jpa/v1/users/{id}/posts/{postId}")
    public void deleteUserPost(@PathVariable int id, @PathVariable int postId) {

        Optional<User> userOptional = repository.findById(id);

        if (userOptional.isEmpty()) {

            throw new NotFound("id:" + id);
        }

        User user = userOptional.get();

        Post foundPost = user.getPosts().stream().filter(post -> post.getId().equals((int) postId)).findFirst().orElse(null);

        if (foundPost == null) {
            throw new NotFound("id:" + postId);
        }

        postRepository.delete(foundPost);
        
    }

}