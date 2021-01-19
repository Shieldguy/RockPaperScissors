package test.couchbase.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import test.couchbase.data.Action;
import test.couchbase.protocol.DataType;
import test.couchbase.protocol.SyncChannel;
import test.couchbase.strategy.RandomStrategy;

import static org.junit.Assert.assertTrue;

public class PlayerTests {
    private Player player;
    private SyncChannel channel;

    @Before
    public void init() throws InterruptedException {
        channel = new SyncChannel();
        player = new Player("player1", new RandomStrategy(), channel);
        player.start();
    }

    @After
    public void deInit() throws InterruptedException {
        player.setExit();
        if(player.isAlive()) {
            player.interrupt();
            player.join();
        }
    }

    @Test
    public void test() throws InterruptedException {
        channel.send(DataType.DATA, null);
        Action action = (Action)channel.receive(DataType.RESPONSE);
        assertTrue("Response should true", action == Action.ROCK || action == Action.SCISSORS ||
                action == Action.PAPER);
    }
}
