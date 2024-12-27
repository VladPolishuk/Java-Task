package DataModel;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Setter
@Getter
public class Journal extends Publication implements Serializable {
    private static final long serialVersionUID = 1L;
    private int pageCounts;

    // Конструктор без quantity (по умолчанию quantity = 0)
    public Journal(String title, int number, LocalDate releaseDate, int pageCounts) {
        super(title, number, releaseDate);
        this.pageCounts = pageCounts;
    }

    // Конструктор с quantity
    public Journal(String title, int number, LocalDate releaseDate, int pageCounts, int quantity) {
        super(title, number, releaseDate, quantity);
        this.pageCounts = pageCounts;
    }

    // Конструктор с id и без quantity (по умолчанию quantity = 0)
    public Journal(int id, String title, int number, LocalDate releaseDate, int pageCounts) {
        super(id, title, number, releaseDate);
        this.pageCounts = pageCounts;
    }

    // Конструктор с id и quantity
    public Journal(int id, String title, int number, LocalDate releaseDate, int pageCounts, int quantity) {
        super(id, title, number, releaseDate, quantity);
        this.pageCounts = pageCounts;
    }

    @Override
    public String toString() {
        return String.format("Journal [%s, Pages: %d, Quantity: %d]",
                super.toString(), pageCounts, getQuantity());
    }
}