package com.alexnv.service;

import com.alexnv.exception.UserNotFoundException;
import com.alexnv.model.User;

import java.util.List;

public interface UserService {
    void addUser(User user);
    List<User> getAllUsers();
    void updateUser(User user) throws UserNotFoundException;
    void deleteUser(Long id) throws UserNotFoundException;
    User getUserById(Long id) throws UserNotFoundException;
    boolean isUserExistsByName(String name);
    boolean isUserExistsByEmail(String email);
}
