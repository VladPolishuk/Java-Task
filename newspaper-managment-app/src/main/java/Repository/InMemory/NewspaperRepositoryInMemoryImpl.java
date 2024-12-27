package Repository.InMemory;

import DataModel.Newspaper;
import Repository.NewspaperRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NewspaperRepositoryInMemoryImpl implements NewspaperRepository {
    private List<Newspaper> newspapers = new ArrayList<>();
    private int nextId = 1;
    private static final String FILE_PATH = "data/newspapers.dat";

    public NewspaperRepositoryInMemoryImpl() {
        loadNewspapers();
        nextId = newspapers.stream().mapToInt(Newspaper::getId).max().orElse(0) + 1;
    }

    @Override
    public void addNewspaper(Newspaper newspaper) {
        try {
            newspaper.setId(nextId++);
            newspapers.add(newspaper);
            saveNewspapers();
        } catch (Exception e) {
            System.err.println("Ошибка при добавлении газеты: " + e.getMessage());
        }
    }

    @Override
    public void updateNewspaper(Newspaper newspaper) {
        try {
            Optional<Newspaper> existingNewspaper = newspapers.stream().filter(n -> n.getId() == newspaper.getId()).findFirst();
            existingNewspaper.ifPresent(n -> {
                newspapers.remove(n);
                newspapers.add(newspaper);
                saveNewspapers();
            });
        } catch (Exception e) {
            System.err.println("Ошибка при обновлении газеты: " + e.getMessage());
        }
    }

    @Override
    public void deleteNewspaper(Newspaper newspaper) {
        try {
            newspapers.remove(newspaper);
            saveNewspapers();
        } catch (Exception e) {
            System.err.println("Ошибка при удалении газеты: " + e.getMessage());
        }
    }

    @Override
    public Newspaper getById(int newspaperId) {
        try {
            return newspapers.stream().filter(n -> n.getId() == newspaperId).findFirst().orElse(null);
        } catch (Exception e) {
            System.err.println("Ошибка при получении газеты по ID: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Newspaper getByTitle(String newspaperTitle) {
        try {
            return newspapers.stream().filter(n -> n.getTitle().equalsIgnoreCase(newspaperTitle)).findFirst().orElse(null);
        } catch (Exception e) {
            System.err.println("Ошибка при получении газеты по названию: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Newspaper getByNumber(int newspaperNumber) {
        try {
            return newspapers.stream().filter(n -> n.getNumber() == newspaperNumber).findFirst().orElse(null);
        } catch (Exception e) {
            System.err.println("Ошибка при получении газеты по номеру: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Newspaper> getAll() {
        try {
            return new ArrayList<>(newspapers);
        } catch (Exception e) {
            System.err.println("Ошибка при получении списка газет: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void saveNewspapers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(newspapers);
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении газет: " + e.getMessage());
        }
    }

    private void loadNewspapers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            newspapers = (List<Newspaper>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Ошибка при загрузке газет: " + e.getMessage());
            newspapers = new ArrayList<>();
        }
    }
}