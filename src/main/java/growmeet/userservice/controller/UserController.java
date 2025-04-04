package growmeet.userservice.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import growmeet.userservice.domain.User;
import growmeet.userservice.dto.UserRegistrationRequestDTO;
import growmeet.userservice.dto.UserUpdateRequestDTO;
import growmeet.userservice.exception.UserNotFoundException;
import growmeet.userservice.service.UserCommandService;
import growmeet.userservice.service.UserQueryService;
import growmeet.userservice.service.UserRegistrationService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRegistrationService userRegistrationService;
    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserRegistrationRequestDTO request) {
        User savedUser = userRegistrationService.register(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedUser);
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userQueryService.findAll();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> retrieveUser(@PathVariable long id) {
        User user = userQueryService.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));

        EntityModel<User> entityModel = EntityModel.of(user);
        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(linkTo.withRel("all-users"));

        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable long id) {
        userCommandService.deleteById(id);
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody UserUpdateRequestDTO dto) {
        User updatedUser = userCommandService.update(id, dto);
        return ResponseEntity.accepted().body(updatedUser);
    }
}
