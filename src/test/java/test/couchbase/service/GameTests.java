package test.couchbase.service;

import org.junit.Before;
import org.junit.Test;
import test.couchbase.data.Action;
import test.couchbase.data.Result;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class GameTests {
    private Game game;

    @Before
    public void init() {
        game = new Game();
    }

    @Test
    public void testGetLastReturnNull() {
        assertNull("Should return null", game.getLast());
    }

    @Test
    public void testAddGameAndGetLast() {
        Map<String, Object> inputs = new HashMap<>();
        inputs.put("Test worker", Action.ROCK);
        inputs.put("test", Action.SCISSORS);
        game.addGame(1, inputs);
        Result result = game.getLast();
        assertEquals("Should return same round", 1, result.getRound());
        assertEquals("Should return same winner", "Test worker", result.getWinner());
        assertEquals("Should return player1's action", Action.ROCK, result.getPlayerAction("Test worker"));
    }

    @Test
    public void testHistoryFile() {
        Map<String, Object> inputs = new HashMap<>();
        inputs.put("Test worker", Action.ROCK);
        inputs.put("test", Action.SCISSORS);
        game.addGame(1, inputs);
        game.saveHistory();
        File file = new File("result.json");
        assertTrue("File should exit", file.exists());
        StringBuffer sb = new StringBuffer();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String read;
            while((read = reader.readLine()) != null) {
                sb.append(read);
                sb.append("\n");
            }
        } catch (IOException ie) {
            fail("It shouldn't throw exception");
        }
        assertEquals("Should same json",
                "[ {\n" +
                "  \"Round\" : 1,\n" +
                "  \"Winner\" : \"Test worker\",\n" +
                "  \"Inputs\" : {\n" +
                "    \"test\" : \"scissors\",\n" +
                "    \"Test worker\" : \"rock\"\n" +
                "  }\n" +
                "} ]\n", sb.toString());
    }
}
