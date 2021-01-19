package test.couchbase.data;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ResultTests {
    @Test
    public void testBuilder() {
        Result result = null;
        try {
            result = new Result.Builder(1, "player1")
                    .addInput("player1", Action.SCISSORS)
                    .addInput("player2", Action.ROCK)
                    .build();
        } catch (ResultException re) {
            fail("Shouldn't throw exception");
        }
        assertEquals("round should match", 1, result.getRound());
        assertEquals("winner should match", "player1", result.getWinner());
        assertEquals("attender#1's action should match", Action.ROCK, result.getPlayerAction("player2"));
        assertEquals("attender#2's action should match", Action.ROCK, result.getPlayerAction("player1", true));
    }

    @Test
    public void testInvalidPlayer() {
        try {
            new Result.Builder(1, "player1")
                    .addInput("player2", Action.SCISSORS)
                    .addInput("player3", Action.ROCK)
                    .build();
            fail("Should throw exception");
        } catch (ResultException re) {
            // expected
        }
    }

    @Test
    public void testNotEnoughPlayers() {
        try {
            new Result.Builder(1, "player1")
                    .addInput("player1", Action.SCISSORS)
                    .build();
            fail("Should throw exception");
        } catch (ResultException re) {
            // expected
        }
    }
}
