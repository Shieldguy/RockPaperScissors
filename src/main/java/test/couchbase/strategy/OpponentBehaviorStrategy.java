package test.couchbase.strategy;

import test.couchbase.data.Action;
import test.couchbase.data.Result;

import java.util.List;

public class OpponentBehaviorStrategy implements Strategy {
    @Override
    public Action getNextAction(List<Result> results) {
        if(results == null || results.size() == 0) {
            // get random number
            switch (rand.nextInt(3)) {
                case 1: return Action.PAPER;
                case 2: return Action.SCISSORS;
                default: return Action.ROCK;
            }
        }
        return results.get(rand.nextInt(results.size())).getPlayerAction(Thread.currentThread().getName(), true);
    }
}
