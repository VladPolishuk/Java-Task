package Repository;

import DataModel.Newspaper;

import java.util.List;

public interface NewspaperRepository {
    void addNewspaper(Newspaper newspaper);

    void updateNewspaper(Newspaper newspaper);

    void deleteNewspaper(Newspaper newspaper);

    Newspaper getById(int newspaperId);

    Newspaper getByTitle(String newspaperTitle);

    Newspaper getByNumber(int newspaperNumber);

    List<Newspaper> getAll();
}
