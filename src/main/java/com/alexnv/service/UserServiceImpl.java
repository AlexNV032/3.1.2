package com.alexnv.service;

import com.alexnv.dao.UserDAO;
import com.alexnv.exception.UserNotFoundException;
import com.alexnv.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @Transactional
    public void addUser(User user) {
        userDAO.addUser(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        if (userDAO.getUserById(user.getId()) == null) {
            throw new UserNotFoundException("User with ID " + user.getId() + " not found.");
        }
        userDAO.updateUser(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (userDAO.getUserById(id) == null) {
            throw new UserNotFoundException("User with ID " + id + " not found.");
        }
        userDAO.deleteUser(id);
    }

    @Override
    public User getUserById(Long id) {
        if (userDAO.getUserById(id) == null) {
            throw new UserNotFoundException("User with ID " + id + " not found.");
        }
        return userDAO.getUserById(id);
    }

    @Override
    public boolean isUserExistsByName(String name) {
        return userDAO.getAllUsers().stream()
                .anyMatch(user -> user.getName().equalsIgnoreCase(name));
    }

    @Override
    public boolean isUserExistsByEmail(String email) {
        return userDAO.getAllUsers().stream()
                .anyMatch(user -> user.getEmail().equalsIgnoreCase(email));
    }
}
