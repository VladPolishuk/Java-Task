package Repository;

import DataModel.User;

public interface UserRepository {
    void addUser(User user);

    void updateUser(User user);

    void deleteUser(User user);

    User getUserById(int userId);

    User getUserByUsername(String username);
}
