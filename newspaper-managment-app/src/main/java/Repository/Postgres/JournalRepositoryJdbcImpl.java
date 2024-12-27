package Repository.Postgres;

import DataModel.Journal;
import Repository.JournalRepository;
import Repository.Postgres.Tool.PostgresConnectionTool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JournalRepositoryJdbcImpl implements JournalRepository {
    private static final String TABLE_NAME = "journals";

    @Override
    public void addJournal(Journal journal) {
        String sql = "INSERT INTO " + TABLE_NAME + " (title, number, release_date, page_counts, quantity) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = PostgresConnectionTool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, journal.getTitle());
            statement.setInt(2, journal.getNumber());
            statement.setDate(3, Date.valueOf(journal.getReleaseDate()));
            statement.setInt(4, journal.getPageCounts());
            statement.setInt(5, journal.getQuantity());
            statement.executeUpdate();

            // Получаем сгенерированный ID
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    journal.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении журнала: " + e.getMessage());
        }
    }

    @Override
    public void updateJournal(Journal journal) {
        String sql = "UPDATE " + TABLE_NAME + " SET title = ?, number = ?, release_date = ?, page_counts = ?, quantity = ? WHERE id = ?";
        try (Connection connection = PostgresConnectionTool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, journal.getTitle());
            statement.setInt(2, journal.getNumber());
            statement.setDate(3, Date.valueOf(journal.getReleaseDate()));
            statement.setInt(4, journal.getPageCounts());
            statement.setInt(5, journal.getQuantity());
            statement.setInt(6, journal.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка при обновлении журнала: " + e.getMessage());
        }
    }

    @Override
    public void deleteJournal(Journal journal) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
        try (Connection connection = PostgresConnectionTool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, journal.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении журнала: " + e.getMessage());
        }
    }

    @Override
    public Journal getById(int journalId) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
        try (Connection connection = PostgresConnectionTool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, journalId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Journal(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getInt("number"),
                        resultSet.getDate("release_date").toLocalDate(),
                        resultSet.getInt("page_counts"),
                        resultSet.getInt("quantity")
                );
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении журнала по ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Journal getByTitle(String journalTitle) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE title = ?";
        try (Connection connection = PostgresConnectionTool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, journalTitle);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Journal(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getInt("number"),
                        resultSet.getDate("release_date").toLocalDate(),
                        resultSet.getInt("page_counts"),
                        resultSet.getInt("quantity")
                );
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении журнала по названию: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Journal getByNumber(int journalNumber) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE number = ?";
        try (Connection connection = PostgresConnectionTool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, journalNumber);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Journal(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getInt("number"),
                        resultSet.getDate("release_date").toLocalDate(),
                        resultSet.getInt("page_counts"),
                        resultSet.getInt("quantity")
                );
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении журнала по номеру: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Journal> getAll() {
        List<Journal> journals = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME;
        try (Connection connection = PostgresConnectionTool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                journals.add(new Journal(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getInt("number"),
                        resultSet.getDate("release_date").toLocalDate(),
                        resultSet.getInt("page_counts"),
                        resultSet.getInt("quantity")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении списка журналов: " + e.getMessage());
        }
        return journals;
    }
}