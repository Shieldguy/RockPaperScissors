package test.couchbase.protocol;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SyncChannelTests {
    private SyncChannel syncChannel;

    @Before
    public void init() throws InterruptedException {
        syncChannel = new SyncChannel();
    }

    @Test
    public void testNormalData() throws InterruptedException {
        syncChannel.send(DataType.DATA, "abcd");
        String rsp = (String)syncChannel.receive(DataType.DATA);
        assertEquals("Should match string", "abcd", rsp);
    }

    @Test
    public void testNormalResponse() throws InterruptedException {
        syncChannel.send(DataType.RESPONSE, "abcd");
        String rsp = (String)syncChannel.receive(DataType.RESPONSE);
        assertEquals("Should match string", "abcd", rsp);
    }
}
