package Entities;


import Entidades.User;
import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

public class AuthManager {

    private List<User> users;

    public AuthManager() {
        users = new ArrayList<>();
    }

    // Register new user
    public boolean registerUser(String name, String password) {

        if (findUser(name) != null) {
            return false; // user already exists
        }

        users.add(new User(name, password));
        return true;
    }

    // Authenticate login
    public User login(String name, String password) {

        User u = findUser(name);

        if (u != null && u.getPassword().equals(password)) {
            return u;
        }

        return null;
    }

    // Find user by name
    private User findUser(String name) {

        for (User u : users) {
            if (u.getName().equalsIgnoreCase(name)) {
                return u;
            }
        }

        return null;
    }

    // Get list of users (optional)
    public List<User> getUsers() {
        return users;
    }
}





