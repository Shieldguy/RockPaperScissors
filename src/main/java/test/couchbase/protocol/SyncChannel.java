package test.couchbase.protocol;

import java.util.concurrent.Semaphore;

public class SyncChannel {
    private Semaphore   semaphoreData;
    private Semaphore   semaphoreRsp;
    private Payload      reqPayload;
    private Payload      rspPayload;

    public SyncChannel() throws InterruptedException {
        semaphoreData = new Semaphore(1);
        semaphoreRsp = new Semaphore(1);
        semaphoreData.acquire();
        semaphoreRsp.acquire();
    }

    public void send(DataType type, final Object data) {
        if (type == DataType.DATA) {
            reqPayload = new Payload(type, data);
            semaphoreData.release();
        } else { // Command.RESPONSE:
            rspPayload = new Payload(type, data);
            semaphoreRsp.release();
        }
    }

    public Object receive(DataType type) throws InterruptedException {
        if (type == DataType.DATA) {
            semaphoreData.acquire();
            return reqPayload.getData();
        } else { //case Command.RESPONSE:
            semaphoreRsp.acquire();
            return rspPayload.getData();
        }
    }
}
