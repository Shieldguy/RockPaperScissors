package test.couchbase.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import test.couchbase.protocol.DataType;
import test.couchbase.protocol.SyncChannel;
import test.couchbase.data.Result;
import test.couchbase.strategy.Strategy;

import java.util.LinkedList;
import java.util.List;

public class Player extends Thread {
    private static final Logger logger = LogManager.getLogger(Player.class);
    private final Strategy          strategy;
    private final SyncChannel       channel;
    private List<Result>            results;
    private boolean isExit = false;

    public Player(String name, Strategy strategy, SyncChannel channel) {
        this.strategy = strategy;
        this.channel = channel;
        setName(name);
        init();
    }

    private void init() {
        results = new LinkedList<>();
    }

    public void setExit() {
        isExit = true;
    }

    @Override
    public void run() {
        while(!isExit) {
            try {
                // read my turn
                Result result = (Result)channel.receive(DataType.DATA);
                if (result != null)
                    results.add(result);

                // send back action
                channel.send(DataType.RESPONSE, strategy.getNextAction(results));
            } catch (InterruptedException ie) {
                // nothing to do.
            }
        }
        //logger.info("Thread:{} died by request", currentThread().getName());
    }
}
