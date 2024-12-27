package DataModel;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Comparator;

@Setter
@Getter
public abstract class Publication implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String title;
    private int number;
    private LocalDate releaseDate;
    private int quantity; // Новое поле

    // Конструктор без quantity (по умолчанию quantity = 0)
    public Publication(String title, int number, LocalDate releaseDate) {
        this.title = title;
        this.number = number;
        this.releaseDate = releaseDate;
        this.quantity = 0;
    }

    // Конструктор с quantity
    public Publication(String title, int number, LocalDate releaseDate, int quantity) {
        this.title = title;
        this.number = number;
        this.releaseDate = releaseDate;
        this.quantity = quantity;
    }

    // Конструктор с id и без quantity (по умолчанию quantity = 0)
    public Publication(int id, String title, int number, LocalDate releaseDate) {
        this.id = id;
        this.title = title;
        this.number = number;
        this.releaseDate = releaseDate;
        this.quantity = 0;
    }

    // Конструктор с id и quantity
    public Publication(int id, String title, int number, LocalDate releaseDate, int quantity) {
        this.id = id;
        this.title = title;
        this.number = number;
        this.releaseDate = releaseDate;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Title: %s, Number: %d, Release Date: %s, Quantity: %d",
                id, title, number, releaseDate != null ? releaseDate.toString() : "N/A", quantity);
    }

    // Компараторы (остаются без изменений)
    public static Comparator<Publication> TitleComparator = Comparator.comparing(Publication::getTitle, String::compareToIgnoreCase);
    public static Comparator<Publication> NumberComparator = Comparator.comparingInt(Publication::getNumber);
    public static Comparator<Publication> ReleaseDateComparator = Comparator.comparing(Publication::getReleaseDate);
    public static Comparator<Publication> TitleNumberReleaseDateComparator = Comparator
            .comparing(Publication::getTitle, String::compareToIgnoreCase)
            .thenComparingInt(Publication::getNumber)
            .thenComparing(Publication::getReleaseDate);
}