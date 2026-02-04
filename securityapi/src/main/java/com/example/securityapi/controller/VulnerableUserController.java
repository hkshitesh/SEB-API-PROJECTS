package com.example.securityapi.controller;

import com.example.securityapi.model.User;
import com.example.securityapi.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1")
public class VulnerableUserController {

    private final UserService svc;

    public VulnerableUserController(UserService svc) {
        this.svc = svc;
    }

    // API1 – BOLA
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        return svc.find(id);
    }

    // API3 – Mass Assignment
    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return svc.save(user);
    }

    // API2 – Broken Authentication
    @PostMapping("/login")
    public String login(@RequestBody User user) {
        User u = svc.find(user.getId());
        if (u != null && u.getPassword().equals(user.getPassword()))
            return "Login Successful!";
        return "Invalid Login!";
    }

    // API5 – Broken Function Level Authorization
    @DeleteMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        svc.delete(id);
        return "User deleted!";
    }

    // API8 – Excessive Data Exposure
    @GetMapping("/debug/info")
    public Collection<User> debug() {
        return svc.findAll();
    }
}
