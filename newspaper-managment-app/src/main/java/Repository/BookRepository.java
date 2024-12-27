package Repository;

import DataModel.Book;

import java.util.List;

public interface BookRepository {
    void addBook(Book book);

    void updateBook(Book book);

    void deleteBook(Book book);

    Book getById(int bookId);

    Book getByTitle(String bookTitle);

    List<Book> getAll();
}
