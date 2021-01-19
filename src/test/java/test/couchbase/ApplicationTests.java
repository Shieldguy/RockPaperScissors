package test.couchbase;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import test.couchbase.data.Result;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class ApplicationTests {
    private Application application;

    @Before
    public void init() {
        application = new Application();
    }

    @Test
    public void testVerify100Games() {
        application.startGame();
        File file = new File("result.json");
        assertTrue("Should result.json file exist", file.exists());
        StringBuffer sb = new StringBuffer();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException ie) {
            fail("Shouldn't exception");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Result> list = objectMapper.readValue(sb.toString(), LinkedList.class);
            assertEquals("Json data should have 100", 100, list.size());
        } catch (Exception ex) {
            fail("Shouldn't exception during parsing");
        }
    }
}
