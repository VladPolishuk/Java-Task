package Repository.InMemory;

import DataModel.Journal;
import Repository.JournalRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JournalRepositoryInMemoryImpl implements JournalRepository {
    private List<Journal> journals = new ArrayList<>();
    private int nextId = 1;
    private static final String FILE_PATH = "data/journals.dat";

    public JournalRepositoryInMemoryImpl() {
        loadJournals();
        nextId = journals.stream().mapToInt(Journal::getId).max().orElse(0) + 1;
    }

    @Override
    public void addJournal(Journal journal) {
        try {
            journal.setId(nextId++);
            journals.add(journal);
            saveJournals();
        } catch (Exception e) {
            System.err.println("Ошибка при добавлении журнала: " + e.getMessage());
        }
    }

    @Override
    public void updateJournal(Journal journal) {
        try {
            Optional<Journal> existingJournal = journals.stream().filter(j -> j.getId() == journal.getId()).findFirst();
            existingJournal.ifPresent(j -> {
                journals.remove(j);
                journals.add(journal);
                saveJournals();
            });
        } catch (Exception e) {
            System.err.println("Ошибка при обновлении журнала: " + e.getMessage());
        }
    }

    @Override
    public void deleteJournal(Journal journal) {
        try {
            journals.remove(journal);
            saveJournals();
        } catch (Exception e) {
            System.err.println("Ошибка при удалении журнала: " + e.getMessage());
        }
    }

    @Override
    public Journal getById(int journalId) {
        try {
            return journals.stream().filter(j -> j.getId() == journalId).findFirst().orElse(null);
        } catch (Exception e) {
            System.err.println("Ошибка при получении журнала по ID: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Journal getByTitle(String journalTitle) {
        try {
            return journals.stream().filter(j -> j.getTitle().equalsIgnoreCase(journalTitle)).findFirst().orElse(null);
        } catch (Exception e) {
            System.err.println("Ошибка при получении журнала по названию: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Journal getByNumber(int journalNumber) {
        try {
            return journals.stream().filter(j -> j.getNumber() == journalNumber).findFirst().orElse(null);
        } catch (Exception e) {
            System.err.println("Ошибка при получении журнала по номеру: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Journal> getAll() {
        try {
            return new ArrayList<>(journals);
        } catch (Exception e) {
            System.err.println("Ошибка при получении списка журналов: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void saveJournals() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(journals);
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении журналов: " + e.getMessage());
        }
    }

    private void loadJournals() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            journals = (List<Journal>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Ошибка при загрузке журналов: " + e.getMessage());
            journals = new ArrayList<>();
        }
    }
}