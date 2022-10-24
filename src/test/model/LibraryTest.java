package model;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

public class LibraryTest {
    Library test1;
    Library test2;
    Library test3;

    @BeforeEach
    void runBefore() {
        test1 = new Library("3x3");
        test2 = new Library("2x2");
        test3 = new Library("random");
        test2.addAlgorithm(new Algorithm("jPerm"));
        test3.addAlgorithm(new Algorithm("yPerm"));
        test3.addAlgorithm(new Algorithm("mPerm"));
    }

    @Test
    public void addAlgorithmTest() {
        assertEquals(0, test1.sizeOfLibrary());
        assertEquals(1, test2.sizeOfLibrary());
        assertEquals(2, test3.sizeOfLibrary());
        Algorithm algorithm1 = new Algorithm("tPerm");
        test1.addAlgorithm(algorithm1);
        assertEquals(1, test1.sizeOfLibrary());
        test1.addAlgorithm(algorithm1);
        assertEquals(1, test1.sizeOfLibrary());
    }

    @Test
    public void removeAlgorithmTest() {
        test1.removeAlgorithm("jPerm");
        test2.removeAlgorithm("jPerm");
        test3.removeAlgorithm("nPerm");
        assertEquals(0, test1.sizeOfLibrary());
        assertEquals(0, test2.sizeOfLibrary());
        assertEquals(2, test3.sizeOfLibrary());
    }

    @Test
    public void existsAlgorithmTest() {
        assertFalse(test1.existsAlgorithm("jPerm"));
        assertTrue(test2.existsAlgorithm("jPerm"));
        assertFalse(test2.existsAlgorithm("yPerm"));
        assertTrue(test3.existsAlgorithm("yPerm"));
        assertFalse(test3.existsAlgorithm("jPerm"));
    }

    @Test
    public void getNameTest() {
        assertEquals("3x3", test1.getName());
        assertEquals("2x2", test2.getName());
        assertEquals("random", test3.getName());

    }

    @Test
    public void getAlgorithmTest() {
        assertNull(test1.getAlgorithm("jPerm"));
        assertEquals("jPerm", test2.getAlgorithm("jPerm").getName());
        assertNull(test3.getAlgorithm("jPerm"));
    }

    @Test
    public void getAlgorithmsTest() {
        assertEquals(0, test1.getAlgorithms().size());
        assertEquals("jPerm", test2.getAlgorithms().get(0));
        assertEquals("yPerm", test3.getAlgorithms().get(0));
        assertEquals("mPerm", test3.getAlgorithms().get(1));
    }
    @Test
    public void printAlgorithms() {
        PrintStream standardOut = System.out;
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        test1.printAlgorithms();
        test2.printAlgorithms();
        test3.printAlgorithms();
        assertEquals("jPerm\n" +
                "yPerm\n" +
                "mPerm", outputStreamCaptor.toString().trim());
    }
    @Test
    public void getLibraryTest() {
        assertEquals(0, test1.getLibrary().size());
        assertEquals(1, test2.getLibrary().size());
        assertEquals(2, test3.getLibrary().size());
    }

    @Test
    public void sizeOfLibrary() {
        assertEquals(0, test1.sizeOfLibrary());
        assertEquals(1, test2.sizeOfLibrary());
        assertEquals(2, test3.sizeOfLibrary());
    }

    @Test
    public void toJsonTest() {
        JSONObject json = test3.toJson();
        JSONArray jsonArray = json.getJSONArray("algorithms");
        assertEquals("random", json.getString("name"));
        assertEquals("yPerm", jsonArray.getJSONObject(0).getString("name"));
        assertEquals("mPerm", jsonArray.getJSONObject(1).getString("name"));
    }
}
