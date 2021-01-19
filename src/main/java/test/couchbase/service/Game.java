package test.couchbase.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import test.couchbase.common.JsonConverter;
import test.couchbase.data.Action;
import test.couchbase.data.Result;
import test.couchbase.data.ResultException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Game {
    private static final Logger logger = LogManager.getLogger(Game.class);
    private final List<Result> results = new LinkedList<>();

    public Result getLast() {
        if (results.size() == 0)
            return null;
        return results.get(results.size()-1);
    }

    public void addGame(int round, Map<String, Object> playerActions) {
        Result.Builder builder = new Result.Builder(round, getWinner(playerActions));
        for(String player: playerActions.keySet())
            builder.addInput(player, (Action)playerActions.get(player));
        try {
            results.add(builder.build());
        } catch (ResultException re) {
            logger.warn("WARNING: Failed to result build - {}", re.getMessage());
        }
    }

    private String getWinner(Map<String, Object> playerActions) {
        List<String> winners = new LinkedList<>();
        for(String player: playerActions.keySet()) {
            Action action = (Action)playerActions.get(player);
            int winCount = 0;
            for(String other: playerActions.keySet()) {
                if (!other.equals(player)) {
                    winCount += action.winningStatus((Action)playerActions.get(other));
                }
            }
            if(winCount == playerActions.size()-1)
                winners.add(player);
        }
        return winners.size() == 0? null: winners.get(0);
    }

    public void saveHistory() {
        // save json results to file
        String json = JsonConverter.toJson(results, true);
        save(json);
    }

    private void save(String json) {
        File file = new File("result.json");
        if(file.exists())
            file.delete();
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(json);
            fw.flush();
            logger.info("Game history saved : {}", file.getAbsolutePath());
        } catch (IOException ex) {
            logger.error("ERROR: Failed to write result.json file - {}", ex.getMessage());
        }
    }
}
