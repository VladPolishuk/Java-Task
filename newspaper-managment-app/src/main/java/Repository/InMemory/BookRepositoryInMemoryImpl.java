package Repository.InMemory;

import DataModel.Book;
import Repository.BookRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepositoryInMemoryImpl implements BookRepository {
    private List<Book> books = new ArrayList<>();
    private int nextId = 1;
    private static final String FILE_PATH = "data/books.dat";

    public BookRepositoryInMemoryImpl() {
        loadBooks();
        nextId = books.stream().mapToInt(Book::getId).max().orElse(0) + 1;
    }

    @Override
    public void addBook(Book book) {
        try {
            book.setId(nextId++);
            books.add(book);
            saveBooks();
        } catch (Exception e) {
            System.err.println("Ошибка при добавлении книги: " + e.getMessage());
        }
    }

    @Override
    public void updateBook(Book book) {
        try {
            Optional<Book> existingBook = books.stream().filter(b -> b.getId() == book.getId()).findFirst();
            existingBook.ifPresent(b -> {
                books.remove(b);
                books.add(book);
                saveBooks();
            });
        } catch (Exception e) {
            System.err.println("Ошибка при обновлении книги: " + e.getMessage());
        }
    }

    @Override
    public void deleteBook(Book book) {
        try {
            books.remove(book);
            saveBooks();
        } catch (Exception e) {
            System.err.println("Ошибка при удалении книги: " + e.getMessage());
        }
    }

    @Override
    public Book getById(int bookId) {
        try {
            return books.stream().filter(b -> b.getId() == bookId).findFirst().orElse(null);
        } catch (Exception e) {
            System.err.println("Ошибка при получении книги по ID: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Book getByTitle(String bookTitle) {
        try {
            return books.stream().filter(b -> b.getTitle().equalsIgnoreCase(bookTitle)).findFirst().orElse(null);
        } catch (Exception e) {
            System.err.println("Ошибка при получении книги по названию: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Book> getAll() {
        try {
            return new ArrayList<>(books);
        } catch (Exception e) {
            System.err.println("Ошибка при получении списка книг: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void saveBooks() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(books);
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении книг: " + e.getMessage());
        }
    }

    private void loadBooks() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            books = (List<Book>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Ошибка при загрузке книг: " + e.getMessage());
            books = new ArrayList<>();
        }
    }
}