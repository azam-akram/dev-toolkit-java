import java.util.ArrayList;
import java.util.List;

public class CricketLiveCoverageServiceImpl implements LiveCoverageService {

    private MatchState state;

    private List<LiveCoverageObserver> observers = new ArrayList<>();

    @Override
    public void register (LiveCoverageObserver sportLiveCoverageObserver) {
        System.out.println("CricketLiveCoverageServiceImpl, registering " + sportLiveCoverageObserver.getClass().getName());
        observers.add(sportLiveCoverageObserver);
    }

    @Override
    public void unregister (LiveCoverageObserver sportLiveCoverageObserver) {
        System.out.println("CricketLiveCoverageServiceImpl, unregistering " + sportLiveCoverageObserver.getClass().getName());
        observers.remove(sportLiveCoverageObserver);
    }

    @Override
    public void notifyObservers() {
        for (LiveCoverageObserver app: observers) {
            app.update();
        }
    }

    @Override
    public MatchState getState() {
        return state;
    }

    @Override
    public void setState(MatchState state) {
        this.state = state;
        notifyObservers();
    }
}
