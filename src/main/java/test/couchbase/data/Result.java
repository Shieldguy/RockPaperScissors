package test.couchbase.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashMap;
import java.util.Map;

public class Result {
    @JsonIgnore
    private final int                 INPUTS_CNT = 2;

    @JsonProperty("Round")
    private final int                 round;
    @JsonProperty("Winner")
    private final String              winner;
    @JsonProperty("Inputs")
    private final Map<String, Action> inputs;

    public Result(int round, String winner) {
        this.round = round;
        this.winner = winner;
        this.inputs = new LinkedHashMap<>();
    }

    public int getRound() {
        return this.round;
    }

    public String getWinner() {
        return this.winner;
    }

    @JsonIgnore
    public Action getPlayerAction(String player) {
        return getPlayerAction(player, false);
    }

    @JsonIgnore
    public Action getPlayerAction(String player, boolean isOpponet) {
        if (!isOpponet) {
            return inputs.get(player);
        } else {
            for(String p: inputs.keySet()) {
                if(!p.equals(player)) {
                    return inputs.get(p);
                }
            }
        }
        return null;
    }

    private Result addInput(String player, Action action) {
        this.inputs.put(player, action);
        return this;
    }

    private boolean isValid() {
        if(inputs.size() != INPUTS_CNT)
            return false;
        return winner == null? true: inputs.containsKey(winner);
    }

    public static class Builder {
        private Result result;

        public Builder(int round, String winner) {
            result = new Result(round, winner);
        }

        public Builder addInput(String player, Action action) {
            result.addInput(player, action);
            return this;
        }

        public Result build() throws ResultException {
            if(!result.isValid())
                throw new ResultException("Invalid result data");
            return result;
        }
    }
}
