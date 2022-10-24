package persistence;

import model.AlgorithmApp;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public class JsonReaderTest extends JsonTest{
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            AlgorithmApp aa = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyAlgorithmApp() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyAlgorithmApp.json");
        try {
            AlgorithmApp aa = reader.read();
            assertEquals(0, aa.sizeOfLibraries());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralAlgorithmApp() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralAlgorithmApp.json");
        try {
            AlgorithmApp aa = reader.read();
            assertEquals(2, aa.sizeOfLibraries());
            aa.setLibrary("testLibrary");
            aa.setAlgorithm("testAlgorithm");
            assertEquals("[R, U, R']", aa.getAlgorithm());

            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor));
            aa.displayTimes();
            assertEquals("[1.1, 2.2, 3.3]\n" +
                    "\tThe average time is: 2.1999999999999997", outputStreamCaptor.toString().trim());

            aa.setAlgorithm("testAlgorithm2");
            assertEquals("[F, B, D, U2]", aa.getAlgorithm());

            ByteArrayOutputStream outputStreamCaptor2 = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor2));
            aa.displayTimes();
            assertEquals("[2.0]\n" +
                    "\tThe average time is: 2.0", outputStreamCaptor2.toString().trim());

            aa.setLibrary("testLibrary2");
            aa.setAlgorithm("testAlgorithmA");
            assertEquals("[]", aa.getAlgorithm());

            ByteArrayOutputStream outputStreamCaptor3 = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor3));
            aa.displayTimes();
            assertEquals("[]\n" +
                    "\tThe average time is: 0.0", outputStreamCaptor3.toString().trim());

            aa.setAlgorithm("testAlgorithmB");
            assertEquals("[R, U, R', F', R, U, R', U', R', F, R2, U', R', U']", aa.getAlgorithm());

            ByteArrayOutputStream outputStreamCaptor4 = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor4));
            aa.displayTimes();
            assertEquals("[1.178, 1.313, 1.063, 1.729, 1.146, 0.977, 1.046, 1.096]\n" +
                    "\tThe average time is: 1.1935", outputStreamCaptor4.toString().trim());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
