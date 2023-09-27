package com.example.user;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")


public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public List<User>fetchUserList(){
            return userService.fetchUserList();
    }
    @GetMapping("/register/{id}")
    public User fetchUserById(@PathVariable ("id")Long userId){
            return userService.fetchUserById(userId);
    }
    @PostMapping("/register")
//    @ExceptionHandler
    public String registerUser(@Valid @RequestBody User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "Validation errors: " + bindingResult.getAllErrors();
        }
        // Check if the username is already taken
        if (userService.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("Username is already taken");
        }
        if (userService.findByEmail(user.getEmail()) != null) {
            return "Email is already registered";
        }

        if (!isPasswordValid(user.getPassword())) {
            bindingResult.rejectValue("password", "error.password", "Password is not valid");
        }

        if (bindingResult.hasErrors()) {
            // Handle validation errors, e.g., return to the registration form with error messages.
            return " password error";
        }

        userService.save(user);
        return "Registration successful";

    }

    @PostMapping("/change")
    public String changePassword(@RequestBody PasswordChangeRequest request) {
        userService.changePassword(request);
        return "Password changed successfully";
    }
    private boolean isPasswordValid(String password) {
        // Implement your password validation logic here.
        // For example, check length, complexity, etc.
        return password != null && password.length() >= 8; // Minimum 8 characters for simplicity.
    }

}
