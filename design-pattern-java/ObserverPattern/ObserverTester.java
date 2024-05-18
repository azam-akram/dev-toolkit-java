import java.util.concurrent.TimeUnit;

public class ObserverTester {

    public static void main(String[] args) {

        System.out.println("ObserverTester: main()");

        LiveCoverageObserver mobileApp = new LiveCoverageMobileApp();
        mobileApp.start();
        mobileApp.end();

        LiveCoverageWebApp webApp = new LiveCoverageWebApp();
        webApp.start();
        webApp.end();
    }



    static void passTime(TimeUnit unit, long time) {
        try {
            unit.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}