package com.cgc.store.service;

import com.cgc.store.entity.User;
import com.cgc.store.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    public User saveUser(User user) {
        ifUserIdNoTNullThrow(user);
        validateUser(user);
        checkIfUserExistByEmailThrow(user);
        return userRepo.save(user);
    }

    private void checkIfUserExistByEmailThrow(User user) {
        User existingUser = getUserByEmail(user.getEmail());
        if (existingUser != null) {
            throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists");
        }
    }

    public void updateUser(User user) {
        checkIfUserExistElseThrow(user.getId());
        validateUser(user);
        userRepo.save(user);
    }

    public User getUserById(Long id) {
        if(id == null){
            throw new IllegalArgumentException("User id cannot be null");
        }
        return userRepo.findById(id).orElse(null);
    }

    public User getUserByIdElseThrow(Long id) {
        User user = getUserById(id);
        if (user == null) {
            throw new IllegalArgumentException("User with id " + id + " not found");
        }
        return user;
    }

    public User getUserByEmail(String email) {
        Optional<User> user = userRepo.findByEmail(email);
        return user.orElse(null);
    }

    public User getUserByEmailELseThrow(String email) {
        User user = getUserByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("User with email " + email + " not found");
        }
        return user;
    }


    public void deleteUser(Long id) {
        checkIfUserExistElseThrow(id);
        userRepo.deleteById(id);
    }

    private void validateUser(User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
    }

    private void ifUserIdNoTNullThrow(User user) {
        if(user.getId() != null){
            throw new IllegalArgumentException("User id must be null");
        }
    }

    public User checkIfUserExistElseThrow(Long user) {
        User existingUser = getUserById(user);
        if (existingUser == null) {
            throw new IllegalArgumentException("User with id " + user + " not found");
        }
        return existingUser;
    }

}
