import java.util.concurrent.TimeUnit;

public class LiveCoverageMobileApp extends LiveCoverageApp implements LiveCoverageObserver {

    private LiveCoverageService liveCoverageService = MatchUpdaterFactory.getUpdater(MatchType.FOOTBALL);

    @Override
    public void update () {
        System.out.println("Football match state: " +  liveCoverageService.getState().toString());
    }

    @Override
    public void start() {
        liveCoverageService.register(this);
        super.executePlay(liveCoverageService);
    }

    @Override
    public void end() {
        liveCoverageService.unregister(this);
    }
}