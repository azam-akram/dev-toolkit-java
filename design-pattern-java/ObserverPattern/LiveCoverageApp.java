import java.util.concurrent.TimeUnit;

public class LiveCoverageApp {

    protected void executePlay(LiveCoverageService liveCoverageService) {
        liveCoverageService.setState(MatchState.START);
        passTime(TimeUnit.SECONDS, 5L);
        liveCoverageService.setState(MatchState.INTERVAL);
        passTime(TimeUnit.SECONDS, 2L);
        liveCoverageService.setState(MatchState.START);
        passTime(TimeUnit.SECONDS, 5L);
        liveCoverageService.setState(MatchState.END);
    }

    private void passTime(TimeUnit unit, long time) {
        try {
            unit.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
