package test.couchbase.data;

public enum Action {
    ROCK(0),
    PAPER(1),
    SCISSORS(2);

    private int num;
    Action(int num) {
        this.num = num;
    }

    public int winningStatus(final Action action) {
        if(this.num == action.num) {
            return 0;   // same
        } else if(Math.abs(this.num - action.num) == 1) {
            return this.num > action.num? 1: -1;
        } else {
            return this.num == 0? 1: -1;
        }
    }
}
