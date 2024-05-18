public interface LiveCoverageService {

    void register (LiveCoverageObserver sportLiveCoverageObserver);

    void unregister (LiveCoverageObserver sportLiveCoverageObserver);

    void notifyObservers();

    MatchState getState();

    void setState(MatchState state);

}
