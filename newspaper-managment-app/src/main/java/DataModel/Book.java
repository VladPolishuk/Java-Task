package DataModel;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class Book extends Publication implements Serializable {
    private static final long serialVersionUID = 1L;
    private String author;
    private String publisher;
    private int pageCounts;

    // Конструктор без quantity (по умолчанию quantity = 0)
    public Book(String title, String author, String publisher, int pageCounts) {
        super(title, 0, null);
        this.author = author;
        this.publisher = publisher;
        this.pageCounts = pageCounts;
    }

    // Конструктор с quantity
    public Book(String title, String author, String publisher, int pageCounts, int quantity) {
        super(title, 0, null, quantity);
        this.author = author;
        this.publisher = publisher;
        this.pageCounts = pageCounts;
    }

    // Конструктор с id и без quantity (по умолчанию quantity = 0)
    public Book(int id, String title, String author, String publisher, int pageCounts) {
        super(id, title, 0, null);
        this.author = author;
        this.publisher = publisher;
        this.pageCounts = pageCounts;
    }

    // Конструктор с id и quantity
    public Book(int id, String title, String author, String publisher, int pageCounts, int quantity) {
        super(id, title, 0, null, quantity);
        this.author = author;
        this.publisher = publisher;
        this.pageCounts = pageCounts;
    }

    @Override
    public String toString() {
        return String.format("Book [%s, Author: %s, Publisher: %s, Pages: %d, Quantity: %d]",
                super.toString(), author, publisher, pageCounts, getQuantity());
    }
}