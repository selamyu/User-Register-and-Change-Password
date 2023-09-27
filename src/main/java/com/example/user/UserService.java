package com.example.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
    public User findByUsername(String username) {
       return userRepository.findByUsername(username);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> fetchUserList() {
        return userRepository.findAll();
    }

    public User fetchUserById(Long userId) {
            return userRepository.findById(userId).get();
    }

    public void changePassword(PasswordChangeRequest request) {
        User user = userRepository.findByUsername(request.getUsername());

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (!(request.getCurrentPassword().equals(user.getPassword()))) {
            throw new RuntimeException("current password is incorrect");
        }

        if (!(request.getNewPassword().equals(request.getConfirmNewPassword()))) {
            throw new RuntimeException("new password is not matched");
        }

        user.setPassword((request.getNewPassword()));
        userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
