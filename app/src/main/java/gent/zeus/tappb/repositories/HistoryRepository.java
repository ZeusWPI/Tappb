package gent.zeus.tappb.repositories;

public class HistoryRepository {
    private static final HistoryRepository ourInstance = new HistoryRepository();

    public static HistoryRepository getInstance() {
        return ourInstance;
    }

    private HistoryRepository() {
    }
}
