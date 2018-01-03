package test;

import main.Application;
import org.testng.annotations.BeforeClass;

import static main.SettingsProvider.startXAMPP;


public class TestBase {

    public static ThreadLocal<Application> tlApp = new ThreadLocal<>();
    public Application app;

    @BeforeClass
    public void start() {
        startXAMPP();
        if (tlApp.get() != null) {
            app = tlApp.get();
            return;
        }

        app = new Application();
        tlApp.set(app);

        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> { app.quit(); app = null; }));
    }

}
