package test.couchbase.protocol;

public class Payload {
    private final DataType  type;
    private final Object    data;

    public Payload(DataType type, final Object data) {
        this.type = type;
        this.data = data;
    }

    public DataType getDataType() {
        return type;
    }

    public Object getData() {
        return data;
    }
}
