package Service;

import DataModel.Book;
import DataModel.Journal;
import DataModel.Newspaper;
import DataModel.Publication;
import Repository.BookRepository;
import Repository.JournalRepository;
import Repository.NewspaperRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ProductService {
    private BookRepository bookRepository;
    private JournalRepository journalRepository;
    private NewspaperRepository newspaperRepository;

    public ProductService(BookRepository bookRepository, JournalRepository journalRepository, NewspaperRepository newspaperRepository) {
        this.bookRepository = bookRepository;
        this.journalRepository = journalRepository;
        this.newspaperRepository = newspaperRepository;
    }

    // === Методы для работы с книгами ===

    public void addBook(Book book) {
        validatePublication(book);
        validateBook(book);
        bookRepository.addBook(book);
    }

    public void updateBook(Book book) {
        validatePublication(book);
        validateBook(book);
        bookRepository.updateBook(book);
    }

    public void deleteBook(Book book) {
        if (book == null || book.getId() <= 0) {
            throw new IllegalArgumentException("Некорректные данные книги");
        }
        bookRepository.deleteBook(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.getAll();
    }

    public Book getBookById(int bookId) {
        if (bookId <= 0) {
            throw new IllegalArgumentException("ID книги должен быть положительным");
        }
        return bookRepository.getById(bookId);
    }

    // === Методы для работы с журналами ===

    public void addJournal(Journal journal) {
        validatePublication(journal);
        validateJournal(journal);
        journalRepository.addJournal(journal);
    }

    public void updateJournal(Journal journal) {
        validatePublication(journal);
        validateJournal(journal);
        journalRepository.updateJournal(journal);
    }

    public void deleteJournal(Journal journal) {
        if (journal == null || journal.getId() <= 0) {
            throw new IllegalArgumentException("Некорректные данные журнала");
        }
        journalRepository.deleteJournal(journal);
    }

    public List<Journal> getAllJournals() {
        return journalRepository.getAll();
    }

    public Journal getJournalById(int journalId) {
        if (journalId <= 0) {
            throw new IllegalArgumentException("ID журнала должен быть положительным");
        }
        return journalRepository.getById(journalId);
    }

    // === Методы для работы с газетами ===

    public void addNewspaper(Newspaper newspaper) {
        validatePublication(newspaper);
        validateNewspaper(newspaper);
        newspaperRepository.addNewspaper(newspaper);
    }

    public void updateNewspaper(Newspaper newspaper) {
        validatePublication(newspaper);
        validateNewspaper(newspaper);
        newspaperRepository.updateNewspaper(newspaper);
    }

    public void deleteNewspaper(Newspaper newspaper) {
        if (newspaper == null || newspaper.getId() <= 0) {
            throw new IllegalArgumentException("Некорректные данные газеты");
        }
        newspaperRepository.deleteNewspaper(newspaper);
    }

    public List<Newspaper> getAllNewspapers() {
        return newspaperRepository.getAll();
    }

    public Newspaper getNewspaperById(int newspaperId) {
        if (newspaperId <= 0) {
            throw new IllegalArgumentException("ID газеты должен быть положительным");
        }
        return newspaperRepository.getById(newspaperId);
    }

    // === Общие методы для всех товаров ===

    public List<Publication> getAllProducts() {
        List<Publication> products = new ArrayList<>();
        products.addAll(getAllBooks());
        products.addAll(getAllJournals());
        products.addAll(getAllNewspapers());
        return products;
    }

    public List<Publication> getAllProductsSortedByTitle() {
        List<Publication> products = getAllProducts();
        products.sort(Publication.TitleComparator);
        return products;
    }

    public List<Publication> getAllProductsSortedByNumber() {
        List<Publication> products = getAllProducts();
        products.sort(Publication.NumberComparator);
        return products;
    }

    public List<Publication> getAllProductsSortedByReleaseDate() {
        List<Publication> products = getAllProducts();
        products.sort(Publication.ReleaseDateComparator);
        return products;
    }

    public List<Publication> getAllProductsSortedByTitleNumberReleaseDate() {
        List<Publication> products = getAllProducts();
        products.sort(Publication.TitleNumberReleaseDateComparator);
        return products;
    }

    public Publication getProductById(int publicationId) {
        if (publicationId <= 0) {
            throw new IllegalArgumentException("ID товара должен быть положительным");
        }
        Publication publication = bookRepository.getById(publicationId);
        if (publication == null) {
            publication = journalRepository.getById(publicationId);
        }
        if (publication == null) {
            publication = newspaperRepository.getById(publicationId);
        }
        return publication;
    }

    public void updateProduct(Publication publication) {
        validatePublication(publication);
        if (publication instanceof Book) {
            validateBook((Book) publication);
            bookRepository.updateBook((Book) publication);
        } else if (publication instanceof Journal) {
            validateJournal((Journal) publication);
            journalRepository.updateJournal((Journal) publication);
        } else if (publication instanceof Newspaper) {
            validateNewspaper((Newspaper) publication);
            newspaperRepository.updateNewspaper((Newspaper) publication);
        }
    }

    // === Валидация ===

    private void validatePublication(Publication publication) {
        if (publication == null) {
            throw new IllegalArgumentException("Товар не может быть null");
        }
        if (publication.getTitle() == null || publication.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Название товара не может быть пустым");
        }
        if (publication.getQuantity() < 0) {
            throw new IllegalArgumentException("Количество товара не может быть отрицательным");
        }
    }

    private void validateBook(Book book) {
        if (book.getAuthor() == null || book.getAuthor().isEmpty()) {
            throw new IllegalArgumentException("Автор книги не может быть пустым");
        }
        if (book.getPublisher() == null || book.getPublisher().isEmpty()) {
            throw new IllegalArgumentException("Издатель книги не может быть пустым");
        }
        if (book.getPageCounts() <= 0) {
            throw new IllegalArgumentException("Количество страниц книги должно быть положительным");
        }
    }

    private void validateJournal(Journal journal) {
        if (journal.getNumber() <= 0) {
            throw new IllegalArgumentException("Номер журнала должен быть положительным");
        }
        if (journal.getPageCounts() <= 0) {
            throw new IllegalArgumentException("Количество страниц журнала должно быть положительным");
        }
    }

    private void validateNewspaper(Newspaper newspaper) {
        if (newspaper.getNumber() <= 0) {
            throw new IllegalArgumentException("Номер газеты должен быть положительным");
        }
    }
}