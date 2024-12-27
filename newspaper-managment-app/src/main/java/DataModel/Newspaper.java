package DataModel;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Setter
@Getter
public class Newspaper extends Publication implements Serializable {
    private static final long serialVersionUID = 1L;

    // Конструктор без quantity (по умолчанию quantity = 0)
    public Newspaper(String title, int number, LocalDate releaseDate) {
        super(title, number, releaseDate);
    }

    // Конструктор с quantity
    public Newspaper(String title, int number, LocalDate releaseDate, int quantity) {
        super(title, number, releaseDate, quantity);
    }

    // Конструктор с id и без quantity (по умолчанию quantity = 0)
    public Newspaper(int id, String title, int number, LocalDate releaseDate) {
        super(id, title, number, releaseDate);
    }

    // Конструктор с id и quantity
    public Newspaper(int id, String title, int number, LocalDate releaseDate, int quantity) {
        super(id, title, number, releaseDate, quantity);
    }

    @Override
    public String toString() {
        return String.format("Newspaper [%s, Quantity: %d]",
                super.toString(), getQuantity());
    }
}