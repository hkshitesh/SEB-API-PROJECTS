package com.api;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/users")
public class UserServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // In-memory storage
    private final Map<Integer, String> users = new HashMap<>();

    // Utility method: read request body
    private String readBody(HttpServletRequest request) throws IOException {
        BufferedReader br = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    // Simple JSON parser (string-based)
    private Map<String, String> parseJson(String json) {
        Map<String, String> map = new HashMap<>();

        json = json.trim();
        json = json.substring(1, json.length() - 1); // remove { }

        String[] pairs = json.split(",");

        for (String pair : pairs) {
            String[] kv = pair.split(":");
            if (kv.length == 2) {
                map.put(
                        kv[0].replace("\"", "").trim(),
                        kv[1].replace("\"", "").trim()
                );
            }
        }
        return map;
    }

    // CREATE (POST)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Map<String, String> json = parseJson(readBody(request));

        int id = Integer.parseInt(json.get("id"));
        String name = json.get("name");
        String email = json.get("email");

        String userJson = "{ \"id\": " + id +
                ", \"name\": \"" + name +
                "\", \"email\": \"" + email + "\" }";

        users.put(id, userJson);

        response.setContentType("application/json");
        response.getWriter().write("{\"message\": \"User created successfully\"}");
    }

    // READ (GET)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        response.setContentType("application/json");

        if (users.containsKey(id)) {
            response.getWriter().write(users.get(id));
        } else {
            response.getWriter().write("{\"error\": \"User not found\"}");
        }
    }

    // UPDATE (PUT)
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        if (!users.containsKey(id)) {
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"User not found\"}");
            return;
        }

        Map<String, String> json = parseJson(readBody(request));
        String name = json.get("name");
        String email = json.get("email");

        String updatedJson = "{ \"id\": " + id +
                ", \"name\": \"" + name +
                "\", \"email\": \"" + email + "\" }";

        users.put(id, updatedJson);

        response.setContentType("application/json");
        response.getWriter().write("{\"message\": \"User updated successfully\"}");
    }

    // DELETE (DELETE)
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        response.setContentType("application/json");

        if (users.containsKey(id)) {
            users.remove(id);
            response.getWriter().write("{\"message\": \"User deleted successfully\"}");
        } else {
            response.getWriter().write("{\"error\": \"User not found\"}");
        }
    }
}
