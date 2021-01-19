package test.couchbase.common;

import org.junit.Before;
import org.junit.Test;
import test.couchbase.data.Action;
import test.couchbase.data.Result;
import test.couchbase.data.ResultException;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class JsonConverterTests {
    private Result  defaultResult;

    @Before
    public void init() {
        try {
            defaultResult = new Result.Builder(1, "Player2")
                    .addInput("Player1", Action.SCISSORS)
                    .addInput("Player2", Action.ROCK)
                    .build();
        } catch (ResultException re) {
            fail("Shouldn't throw exception");
        }
    }

    @Test
    public void testConvertJson() {
        String json = JsonConverter.toJson(defaultResult, false);
        assertEquals("Json data should match",
                "{\"Round\":1,\"Winner\":\"Player2\",\"Inputs\":{" +
                        "\"Player1\":\"scissors\",\"Player2\":\"rock\"}}",
                json);
    }

    @Test
    public void testConvertPrettyJson() {
        String json = JsonConverter.toJson(defaultResult, true);
        assertEquals("Json data should match",
                "{\n" +
                        "  \"Round\" : 1,\n" +
                        "  \"Winner\" : \"Player2\",\n" +
                        "  \"Inputs\" : {\n" +
                        "    \"Player1\" : \"scissors\",\n" +
                        "    \"Player2\" : \"rock\"\n" +
                        "  }\n" +
                        "}",
                json);
    }

    @Test
    public void testConvertList() {
        List<Result> list = new LinkedList<>();
        list.add(defaultResult);
        list.add(defaultResult);

        String json = JsonConverter.toJson(list, false);
        assertEquals("Json data should match",
                "[{\"Round\":1,\"Winner\":\"Player2\",\"Inputs\":{" +
                        "\"Player1\":\"scissors\",\"Player2\":\"rock\"}}," +
                        "{\"Round\":1,\"Winner\":\"Player2\",\"Inputs\":{" +
                        "\"Player1\":\"scissors\",\"Player2\":\"rock\"}}]",
                json);
    }
}
