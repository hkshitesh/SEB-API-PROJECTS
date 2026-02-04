package com.example.paginationfiltersortnodb.service;

import com.example.paginationfiltersortnodb.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final List<User> users = new ArrayList<>();

    public UserService() {
        // Sample in-memory data
        for (int i = 1; i <= 20; i++) {
            users.add(new User(i, "User " + i, 20 + (i % 5)));
        }
    }

    public List<User> getUsers() {
        return users;
    }
}
