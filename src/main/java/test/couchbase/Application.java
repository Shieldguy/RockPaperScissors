package test.couchbase;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import test.couchbase.service.Host;

public class Application {
    private Host mainThread;
    private static final Logger logger = LogManager.getLogger(Application.class);

    public Application() {
        startMain();
    }

    private void startMain() {
        // create main thread
        mainThread = new Host();
        mainThread.setName("main");
        mainThread.start();
    }

    public void startGame() {
        // send start game to main thread
        mainThread.setStart();

        // get game end
        try {
            mainThread.join();
        } catch(InterruptedException ie) {
            logger.error("ERROR: main thread join got error - {}", ie.getMessage());
        }
    }

    public static void main(String[] args) {
        Application app = new Application();
        app.startGame();
        logger.info("Exit Application");
    }
}
