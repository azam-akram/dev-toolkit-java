public class LiveCoverageWebApp extends LiveCoverageApp implements LiveCoverageObserver {

    private LiveCoverageService liveCoverageService = MatchUpdaterFactory.getUpdater(MatchType.CRICKET);

    @Override
    public void update () {
        System.out.println("Cricker match state: " +  liveCoverageService.getState().toString());
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
