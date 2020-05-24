package web.service;

import web.model.User;

import java.util.List;

public interface UserService {
    boolean addUser(User user);

    List<User> getAllUsers();

    boolean deleteUser(User user);

    User getUserById(long id);

    boolean updateUser(User user);

    User getUserByLogin(String login);

    boolean isExistLogin(String login);
}
