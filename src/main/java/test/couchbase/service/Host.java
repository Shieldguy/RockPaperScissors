package test.couchbase.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import test.couchbase.protocol.DataType;
import test.couchbase.protocol.SyncChannel;
import test.couchbase.strategy.OpponentBehaviorStrategy;
import test.couchbase.strategy.RandomStrategy;

import java.util.*;

public class Host extends Thread {
    private static final Logger logger = LogManager.getLogger(Host.class);
    private final int PLAYER_COUNT = 2;
    private final int PLAY_COUNT = 100;
    private List<SyncChannel>   scList;
    private List<Player> players;
    private boolean isStart = false;

    private boolean startPlayers() {
        scList = new LinkedList<>();
        players = new LinkedList<>();

        for(int i = 0; i < PLAYER_COUNT; i++) {
            String name = "Player"+(i+1);
            try {
                SyncChannel channel = new SyncChannel();
                scList.add(channel);
                Player player = new Player(name, (i % 2 == 0) ? new OpponentBehaviorStrategy(): new RandomStrategy(),
                        channel);
                player.start();
                players.add(player);
            } catch (InterruptedException ie) {
                logger.error("ERROR: Failed to create sync channel - {}", ie.getMessage());
                return false;
            }
        }
        return true;
    }

    private void stopPlayers() {
        for(int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            player.setExit();
            player.interrupt();
            try {
                player.join();
            } catch (InterruptedException ie) {
                logger.error("WARNING: Got interrupt during thread join - {}", ie.getMessage());
            }
        }
    }

    public void setStart() {
        isStart = true;
    }

    @Override
    public void run() {
        // wait start flag change
        while(!isStart) {
            try {
                Thread.sleep(500);
            } catch (Exception ex) { }
        }

        logger.info("Game start");

        Game game = new Game();

        // start players
        if(!startPlayers()) {
            return;
        }

        // playing game
        for(int i = 0; i < PLAY_COUNT; i++) {
            Map<String, Object> inputs = new LinkedHashMap<>();
            for(int j = 0; j < players.size(); j++) {
                // send request
                scList.get(j).send(DataType.DATA, game.getLast());

                try {
                    // get response
                    inputs.put("Player" + (j + 1),
                            scList.get(j).receive(DataType.RESPONSE));
                } catch (InterruptedException ie) {
                    logger.warn("WARNING: Got interrupt when receive response from player - {}", ie.getMessage());
                }
            }
            game.addGame(i+1, inputs);
        }

        // save json results to file
        game.saveHistory();

        // end players
        stopPlayers();

        logger.info("Game end");
    }
}
