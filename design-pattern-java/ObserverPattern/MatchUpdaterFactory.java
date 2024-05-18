public class MatchUpdaterFactory {

    private static FootballLiveCoverageServiceImpl footballMatchUpdater;

    private static CricketLiveCoverageServiceImpl cricketMatchUpdater;

    public static LiveCoverageService getUpdater(MatchType type) {
        switch (type) {
            case FOOTBALL:
                if (footballMatchUpdater == null) {
                    synchronized (FootballLiveCoverageServiceImpl.class) {
                        footballMatchUpdater = new FootballLiveCoverageServiceImpl();
                    }
                }
                return footballMatchUpdater;
            case CRICKET:
                if (cricketMatchUpdater == null) {
                    synchronized (CricketLiveCoverageServiceImpl.class) {
                        cricketMatchUpdater = new CricketLiveCoverageServiceImpl();
                    }
                }
                return cricketMatchUpdater;
            case TENNIS:
            default:
                    return null;
        }
    }
}
