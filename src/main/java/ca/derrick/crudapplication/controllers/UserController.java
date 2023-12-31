package ca.derrick.crudapplication.controllers;

import ca.derrick.crudapplication.Repositories.UserRepository;
import ca.derrick.crudapplication.entities.User;
import ca.derrick.crudapplication.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // get all users
    @GetMapping("users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // get user by id rest api
    @GetMapping("users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "User not exist with id :" + id));
        return ResponseEntity.ok(user);
    }

    // create user rest API
    @PostMapping("users")
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    // update user rest api
    @PutMapping("users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "User not exist with id :" + id));
        user.setLastName(userDetails.getLastName());
        user.setFirstName(userDetails.getFirstName());
        user.setEmailId(userDetails.getEmailId());
        User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "User not exist with id :" + id));
        userRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
