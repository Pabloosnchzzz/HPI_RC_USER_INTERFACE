package Entities;

import java.util.ArrayList;
import java.util.List;

public class AuthManager {

    private List<User> users;

    public AuthManager() {
        users = new ArrayList<>();
    }

    // Register new user
    public boolean registerUser(String name, String email, String password) {

        if (findUserByEmail(email) != null) {
            return false; // user already exists
        }

        users.add(new User(name, email, password));
        return true;
    }

    // Authenticate login
    public User login(String email, String password) {

        User u = findUserByEmail(email);
        //verify that the user function that checks if the password is correct is TRUE
        if (u != null && u.checkPassword(password)) {
            return u;
        }

        return null;
    }

    // Find user by email
    private User findUserByEmail(String email) {

        for (User u : users) {
            if (u != null && u.getName() != null && email.equalsIgnoreCase(email)) {
                return u;
            }
        }

        return null;
    }

    public List<User> getUsers() {
        return users;
    }
}




