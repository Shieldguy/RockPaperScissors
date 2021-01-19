package test.couchbase.strategy;

import test.couchbase.data.Action;
import test.couchbase.data.Result;

import java.util.List;

public class RandomStrategy implements Strategy {
    @Override
    public Action getNextAction(List<Result> results) {
        // randomly choose one of rock, paper, and scissor
        switch (rand.nextInt(3)) {
            case 1: return Action.PAPER;
            case 2: return Action.SCISSORS;
            default: return Action.ROCK;
        }
    }
}
