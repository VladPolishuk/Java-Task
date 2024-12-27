package Repository.InMemory;

import DataModel.User;
import Repository.UserRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryInMemoryImpl implements UserRepository {
    private List<User> users = new ArrayList<>();
    private int nextId = 1;
    private static final String FILE_PATH = "data/users.dat";

    public UserRepositoryInMemoryImpl() {
        loadUsers();
        nextId = users.stream().mapToInt(User::getId).max().orElse(0) + 1;
    }

    @Override
    public void addUser(User user) {
        user.setId(nextId++);
        users.add(user);
        saveUsers();
    }

    @Override
    public void updateUser(User user) {
        Optional<User> existingUser = users.stream().filter(u -> u.getId() == user.getId()).findFirst();
        existingUser.ifPresent(u -> {
            users.remove(u);
            users.add(user);
            saveUsers();
        });
    }

    @Override
    public void deleteUser(User user) {
        users.remove(user);
        saveUsers();
    }

    @Override
    public User getUserById(int userId) {
        return users.stream().filter(u -> u.getId() == userId).findFirst().orElse(null);
    }

    @Override
    public User getUserByUsername(String username) {
        return users.stream().filter(u -> u.getUsername().equalsIgnoreCase(username)).findFirst().orElse(null);
    }

    private void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            users = (List<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            users = new ArrayList<>();
        }
    }
}