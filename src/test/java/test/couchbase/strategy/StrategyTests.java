package test.couchbase.strategy;

import org.junit.Before;
import org.junit.Test;
import test.couchbase.data.Action;
import test.couchbase.data.Result;
import test.couchbase.data.ResultException;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StrategyTests {
    private List<Result> history;

    @Before
    public void init() throws ResultException {
        history = new LinkedList<>();
        history.add(new Result.Builder(1, "Test worker")
                .addInput("Test worker", Action.PAPER)
                .addInput("test", Action.ROCK)
                .build());
        history.add(new Result.Builder(2, "test")
                .addInput("Test worker", Action.SCISSORS)
                .addInput("test", Action.ROCK)
                .build());
    }

    @Test
    public void testRandomStrategy() {
        Action action = new RandomStrategy().getNextAction(history);
        assertTrue("Should true",
                action == Action.ROCK || action == Action.PAPER || action == Action.SCISSORS);
    }

    @Test
    public void testOpponentBehaviorStrategy() {
        Action action = new OpponentBehaviorStrategy().getNextAction(history);
        assertEquals("Should same", Action.ROCK, action);
    }

    @Test
    public void testOpponentBehaviorStrategyWithoutHistory() {
        Action action = new OpponentBehaviorStrategy().getNextAction(new LinkedList<Result>());
        assertTrue("Should true",
                action == Action.ROCK || action == Action.PAPER || action == Action.SCISSORS);
    }
}
