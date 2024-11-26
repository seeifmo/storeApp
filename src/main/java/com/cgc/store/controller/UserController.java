package com.cgc.store.controller;

import com.cgc.store.dto.AppResponse;
import com.cgc.store.service.UserService;
import com.cgc.store.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<AppResponse> saveUser(@RequestBody User user) {
        return ResponseEntity.ok(new AppResponse(201, userService.saveUser(user), "User created successfully"));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<AppResponse> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(new AppResponse(200, userService.getUserByEmailELseThrow(email), "User retrieved successfully"));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<AppResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(new AppResponse( 200, userService.getUserByIdElseThrow(id), "User retrieved successfully"));
    }


    @PutMapping
    public ResponseEntity<AppResponse> updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return ResponseEntity.ok(new AppResponse(200, null, "User updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AppResponse> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok( new AppResponse(200, null, "User deleted successfully"));
    }
}
