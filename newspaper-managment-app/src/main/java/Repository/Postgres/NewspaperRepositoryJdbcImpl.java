package Repository.Postgres;

import DataModel.Newspaper;
import Repository.NewspaperRepository;
import Repository.Postgres.Tool.PostgresConnectionTool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NewspaperRepositoryJdbcImpl implements NewspaperRepository {
    private static final String TABLE_NAME = "newspapers";

    @Override
    public void addNewspaper(Newspaper newspaper) {
        String sql = "INSERT INTO " + TABLE_NAME + " (title, number, release_date, quantity) VALUES (?, ?, ?, ?)";
        try (Connection connection = PostgresConnectionTool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, newspaper.getTitle());
            statement.setInt(2, newspaper.getNumber());
            statement.setDate(3, Date.valueOf(newspaper.getReleaseDate()));
            statement.setInt(4, newspaper.getQuantity());
            statement.executeUpdate();

            // Получаем сгенерированный ID
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    newspaper.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении газеты: " + e.getMessage());
        }
    }

    @Override
    public void updateNewspaper(Newspaper newspaper) {
        String sql = "UPDATE " + TABLE_NAME + " SET title = ?, number = ?, release_date = ?, quantity = ? WHERE id = ?";
        try (Connection connection = PostgresConnectionTool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newspaper.getTitle());
            statement.setInt(2, newspaper.getNumber());
            statement.setDate(3, Date.valueOf(newspaper.getReleaseDate()));
            statement.setInt(4, newspaper.getQuantity());
            statement.setInt(5, newspaper.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка при обновлении газеты: " + e.getMessage());
        }
    }

    @Override
    public void deleteNewspaper(Newspaper newspaper) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
        try (Connection connection = PostgresConnectionTool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, newspaper.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении газеты: " + e.getMessage());
        }
    }

    @Override
    public Newspaper getById(int newspaperId) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
        try (Connection connection = PostgresConnectionTool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, newspaperId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Newspaper(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getInt("number"),
                        resultSet.getDate("release_date").toLocalDate(),
                        resultSet.getInt("quantity")
                );
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении газеты по ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Newspaper getByTitle(String newspaperTitle) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE title = ?";
        try (Connection connection = PostgresConnectionTool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newspaperTitle);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Newspaper(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getInt("number"),
                        resultSet.getDate("release_date").toLocalDate(),
                        resultSet.getInt("quantity")
                );
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении газеты по названию: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Newspaper getByNumber(int newspaperNumber) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE number = ?";
        try (Connection connection = PostgresConnectionTool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, newspaperNumber);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Newspaper(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getInt("number"),
                        resultSet.getDate("release_date").toLocalDate(),
                        resultSet.getInt("quantity")
                );
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении газеты по номеру: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Newspaper> getAll() {
        List<Newspaper> newspapers = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME;
        try (Connection connection = PostgresConnectionTool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                newspapers.add(new Newspaper(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getInt("number"),
                        resultSet.getDate("release_date").toLocalDate(),
                        resultSet.getInt("quantity")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении списка газет: " + e.getMessage());
        }
        return newspapers;
    }
}