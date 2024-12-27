package Repository;

import DataModel.Journal;

import java.util.List;

public interface JournalRepository {
    void addJournal(Journal journal);

    void updateJournal(Journal journal);

    void deleteJournal(Journal journal);

    Journal getById(int journalId);

    Journal getByTitle(String journalTitle);

    Journal getByNumber(int journalNumber);

    List<Journal> getAll();
}
