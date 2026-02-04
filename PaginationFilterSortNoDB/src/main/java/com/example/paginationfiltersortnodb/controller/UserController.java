package com.example.paginationfiltersortnodb.controller;

import com.example.paginationfiltersortnodb.model.User;
import com.example.paginationfiltersortnodb.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public Map<String, Object> getUsers(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String order
    ) {

        List<User> users = new ArrayList<>(service.getUsers());

        // Filtering
        if (!name.isEmpty()) {
            users.removeIf(u ->
                    !u.getName().toLowerCase().contains(name.toLowerCase()));
        }

        // Sorting
        users.sort((a, b) -> {
            Comparable v1 = getValue(a, sortBy);
            Comparable v2 = getValue(b, sortBy);
            return v1.compareTo(v2);
        });

        if (order.equalsIgnoreCase("desc")) {
            Collections.reverse(users);
        }

        // Pagination
        int total = users.size();
        int start = page * size;
        int end = Math.min(start + size, total);

        List<User> paginatedList = new ArrayList<>();
        if (start < total) {
            paginatedList = users.subList(start, end);
        }

        // Response
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("page", page);
        response.put("size", size);
        response.put("total", total);
        response.put("data", paginatedList);

        return response;
    }

    private Comparable getValue(User u, String sortBy) {
        return switch (sortBy) {
            case "name" -> u.getName();
            case "age" -> u.getAge();
            default -> u.getId();
        };
    }
}
