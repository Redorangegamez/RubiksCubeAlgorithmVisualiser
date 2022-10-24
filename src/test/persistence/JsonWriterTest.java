package persistence;

import org.junit.jupiter.api.Test;
import model.AlgorithmApp;
import model.Library;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest{
    @Test
    void testWriterInvalidFile() {
        try {
            AlgorithmApp aa = new AlgorithmApp();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            AlgorithmApp aa = new AlgorithmApp();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyAlgorithmApp.json");
            writer.open();
            writer.write(aa);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyAlgorithmApp.json");
            aa = reader.read();
            assertEquals(0, aa.sizeOfLibraries());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            AlgorithmApp aa = new AlgorithmApp();
            aa.addLibrary("3x3");
            aa.addLibrary("2x2");
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralAlgorithmApp.json");
            writer.open();
            writer.write(aa);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralAlgorithmApp.json");
            aa = reader.read();
            aa.setLibrary("3x3");
            aa.addAlgorithmToLibrary("A");
            aa.addAlgorithmToLibrary("B");
            aa.setAlgorithm("A");
            aa.addAlgorithmMove("R");
            aa.addAlgorithmMove("U");
            aa.addAlgorithmTime(1.1);
            aa.addAlgorithmTime(2.2);
            assertEquals(2, aa.sizeOfLibraries());
            assertEquals(2, aa.sizeOfLibrary());

            List<String> moveList = new ArrayList<>();
            moveList.add("R");
            moveList.add("U");
            List<Double> timesList = new ArrayList<>();
            timesList.add(1.1);
            timesList.add(2.2);
            assertEquals("3x3", aa.getLibrary().getName());
            checkAlgorithm("A", moveList, timesList, aa.getAlgorithmObject());

            aa.setAlgorithm("B");
            aa.addAlgorithmMove("F");
            aa.addAlgorithmMove("D");
            aa.addAlgorithmTime(3.3);
            aa.addAlgorithmTime(4.4);

            moveList = new ArrayList<>();
            moveList.add("F");
            moveList.add("D");
            timesList = new ArrayList<>();
            timesList.add(3.3);
            timesList.add(4.4);
            checkAlgorithm("B", moveList, timesList, aa.getAlgorithmObject());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
