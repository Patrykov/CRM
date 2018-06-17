package first.project.service;

import first.project.entity.User;

public interface UserService {

    User findByLogin(String login);

    void saveUser(User user);
}
