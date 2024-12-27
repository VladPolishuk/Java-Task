package Service;

import DataModel.User;
import Repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Регистрация нового пользователя.
     *
     * @param username         Имя пользователя.
     * @param password         Пароль.
     * @param confirmPassword  Подтверждение пароля.
     * @return true, если регистрация прошла успешно, иначе false.
     */
    public boolean registerUser(String username, String password, String confirmPassword) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Имя пользователя не может быть пустым");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Пароль не может быть пустым");
        }
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Пароли не совпадают");
        }
        if (password.length() < 6) {
            throw new IllegalArgumentException("Пароль должен быть не менее 6 символов");
        }
        if (userRepository.getUserByUsername(username) != null) {
            throw new IllegalArgumentException("Пользователь с таким именем уже существует");
        }

        // Хэширование пароля
        String hashedPassword = hashPassword(password);

        // Создание и сохранение пользователя
        User user = new User(username, hashedPassword);
        userRepository.addUser(user);
        return true;
    }

    /**
     * Аутентификация пользователя.
     *
     * @param username Имя пользователя.
     * @param password Пароль.
     * @return true, если аутентификация прошла успешно, иначе false.
     */
    public boolean authenticateUser(String username, String password) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Имя пользователя не может быть пустым");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Пароль не может быть пустым");
        }

        User user = userRepository.getUserByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("Пользователь не найден");
        }

        // Проверка пароля
        return checkPassword(password, user.getHashedPassword());
    }

    /**
     * Хэширование пароля с использованием BCrypt.
     *
     * @param password Пароль.
     * @return Хэшированный пароль.
     */
    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * Проверка пароля с использованием BCrypt.
     *
     * @param password       Пароль.
     * @param hashedPassword Хэшированный пароль.
     * @return true, если пароль совпадает, иначе false.
     */
    private boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}