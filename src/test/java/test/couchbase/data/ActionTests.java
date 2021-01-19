package test.couchbase.data;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ActionTests {
    @Test
    public void testWinner() {
        Action actA = Action.ROCK;
        Action actB = Action.SCISSORS;
        assertEquals("Should return 1", 1, actA.winningStatus(actB));
        assertEquals("Should return -1", -1, actB.winningStatus(actA));
    }

    @Test
    public void testSame() {
        Action actA = Action.ROCK;
        Action actB = Action.ROCK;
        assertEquals("Should return 0", 0, actA.winningStatus(actB));
        assertEquals("Should return 0", 0, actB.winningStatus(actA));
    }
}
