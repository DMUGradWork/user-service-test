package growmeet.userservice.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import growmeet.userservice.domain.User;
import growmeet.userservice.exception.UserNotFoundException;
import growmeet.userservice.repository.UserRepository;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/user")
public class UserJPAController {

    private final UserRepository userRepository;

    public UserJPAController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedUser);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity retrieveUser(@PathVariable long id) {
        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }

        EntityModel<User> entityModel = EntityModel.of(user.get());

        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(linkTo.withRel("all-users")); // all-users -> http://localhost:8088/users

        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable long id){
        userRepository.deleteById(id);
    }

}
