package Repository.Postgres;

import DataModel.User;
import Repository.UserRepository;
import Repository.Postgres.Tool.PostgresConnectionTool;

import java.sql.*;

public class UserRepositoryJdbcImpl implements UserRepository {
    private static final String TABLE_NAME = "users";

    @Override
    public void addUser(User user) {
        String sql = "INSERT INTO " + TABLE_NAME + " (username, hashed_password) VALUES (?, ?)";
        try (Connection connection = PostgresConnectionTool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getHashedPassword());
            statement.executeUpdate();

            // Получаем сгенерированный ID
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении пользователя: " + e.getMessage());
        }
    }

    @Override
    public void updateUser(User user) {
        String sql = "UPDATE " + TABLE_NAME + " SET username = ?, hashed_password = ? WHERE id = ?";
        try (Connection connection = PostgresConnectionTool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getHashedPassword());
            statement.setInt(3, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка при обновлении пользователя: " + e.getMessage());
        }
    }

    @Override
    public void deleteUser(User user) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
        try (Connection connection = PostgresConnectionTool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении пользователя: " + e.getMessage());
        }
    }

    @Override
    public User getUserById(int userId) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
        try (Connection connection = PostgresConnectionTool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("hashed_password")
                );
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении пользователя по ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE username = ?";
        try (Connection connection = PostgresConnectionTool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("hashed_password")
                );
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении пользователя по имени: " + e.getMessage());
        }
        return null;
    }
}