package Repository.Postgres;

import DataModel.Book;
import Repository.BookRepository;
import Repository.Postgres.Tool.PostgresConnectionTool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookRepositoryJdbcImpl implements BookRepository {
    private static final String TABLE_NAME = "books";

    @Override
    public void addBook(Book book) {
        String sql = "INSERT INTO " + TABLE_NAME + " (title, author, publisher, page_counts, quantity) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = PostgresConnectionTool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getPublisher());
            statement.setInt(4, book.getPageCounts());
            statement.setInt(5, book.getQuantity());
            statement.executeUpdate();

            // Получаем сгенерированный ID
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    book.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении книги: " + e.getMessage());
        }
    }

    @Override
    public void updateBook(Book book) {
        String sql = "UPDATE " + TABLE_NAME + " SET title = ?, author = ?, publisher = ?, page_counts = ?, quantity = ? WHERE id = ?";
        try (Connection connection = PostgresConnectionTool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getPublisher());
            statement.setInt(4, book.getPageCounts());
            statement.setInt(5, book.getQuantity());
            statement.setInt(6, book.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка при обновлении книги: " + e.getMessage());
        }
    }

    @Override
    public void deleteBook(Book book) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
        try (Connection connection = PostgresConnectionTool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, book.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении книги: " + e.getMessage());
        }
    }

    @Override
    public Book getById(int bookId) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
        try (Connection connection = PostgresConnectionTool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, bookId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Book(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getString("publisher"),
                        resultSet.getInt("page_counts"),
                        resultSet.getInt("quantity")
                );
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении книги по ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Book getByTitle(String bookTitle) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE title = ?";
        try (Connection connection = PostgresConnectionTool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, bookTitle);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Book(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getString("publisher"),
                        resultSet.getInt("page_counts"),
                        resultSet.getInt("quantity")
                );
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении книги по названию: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Book> getAll() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME;
        try (Connection connection = PostgresConnectionTool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                books.add(new Book(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getString("publisher"),
                        resultSet.getInt("page_counts"),
                        resultSet.getInt("quantity")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении списка книг: " + e.getMessage());
        }
        return books;
    }
}