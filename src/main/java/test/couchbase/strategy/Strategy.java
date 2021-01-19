package test.couchbase.strategy;

import test.couchbase.data.Action;
import test.couchbase.data.Result;

import java.util.Date;
import java.util.List;
import java.util.Random;

public interface Strategy {
    Random rand = new Random(new Date().getTime());

    public Action getNextAction(List<Result> results);
}
