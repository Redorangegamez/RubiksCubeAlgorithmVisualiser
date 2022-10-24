package model;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class AlgorithmAppTest {
    AlgorithmApp test1;
    AlgorithmApp test2;
    AlgorithmApp test3;

    @BeforeEach
    void runBefore() {
        test1 = new AlgorithmApp();
        test2 = new AlgorithmApp();
        test3 = new AlgorithmApp();
        test2.addLibrary("3x3");
        test3.addLibrary("2x2");
        test3.addLibrary("4x4");
        test2.setLibrary("3x3");
        test2.addAlgorithmToLibrary("J");
        test3.addAlgorithmToLibrary("A");
        test3.addAlgorithmToLibrary(new Algorithm("Y"));
        test2.addAlgorithmMove("R");
        test3.addAlgorithmMove("U");
        test3.addAlgorithmMove("F");
        test2.addAlgorithmTime(3.4);
        test3.addAlgorithmTime(2.3);
        test3.addAlgorithmTime(1.2);
    }

    @Test
    public void addLibraryTest() {
        assertEquals(0, test1.sizeOfLibraries());
        assertEquals(1, test2.sizeOfLibraries());
        assertEquals(2, test3.sizeOfLibraries());
    }

    @Test
    public void printLibrariesTest() {
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        test1.printLibraries();
        test2.printLibraries();
        test3.printLibraries();
        assertEquals("3x3\n2x2\n4x4", outputStreamCaptor.toString().trim());
    }

    @Test
    public void getLibrariresTest() {
        assertEquals(0, test1.getLibraries().size());
        assertEquals("3x3", test2.getLibraries().get(0));
        assertEquals("2x2", test3.getLibraries().get(0));
        assertEquals("4x4", test3.getLibraries().get(1));
    }

    @Test
    public void isLibrariesEmptyTest() {
        assertTrue(test1.isLibrariesEmpty());
        assertFalse(test2.isLibrariesEmpty());
    }

    @Test
    public void removeLibraryTest() {
        test1.removeLibrary("3x3");
        test2.removeLibrary("3x3");
        test3.removeLibrary("3x3");
        assertEquals(0, test1.sizeOfLibraries());
        assertEquals(0, test2.sizeOfLibraries());
        assertEquals(2, test3.sizeOfLibraries());
        test3.removeLibrary("2x2");
        assertEquals(1, test3.sizeOfLibraries());
    }

    @Test
    public void sizeOfLibrariesTest() {
        assertEquals(0, test1.sizeOfLibraries());
        assertEquals(1, test2.sizeOfLibraries());
        assertEquals(2, test3.sizeOfLibraries());
    }

    @Test
    public void setLibraryTest() {
        assertFalse(test1.setLibrary("3x3"));
        assertTrue(test2.setLibrary("3x3"));
        assertTrue(test3.setLibrary("2x2"));
        assertFalse(test3.setLibrary("3x3"));
    }

    @Test
    public void addAlgorithmToLibraryTest() {
        assertEquals(0,test1.sizeOfLibrary());
        assertEquals(1,test2.sizeOfLibrary());
        assertEquals(2,test3.sizeOfLibrary());
    }

    @Test
    public void printAlgorithmsTest() {
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        test1.printAlgorithms();
        test2.printAlgorithms();
        test3.printAlgorithms();
        assertEquals("J\nA\nY", outputStreamCaptor.toString().trim());
    }

    @Test
    public void isLibraryEmptyTest() {
        assertTrue(test1.isLibraryEmpty());
        assertFalse(test2.isLibraryEmpty());
    }

    @Test
    public void removeAlgorithmTest() {
        test1.removeAlgorithm("J");
        test2.removeAlgorithm("J");
        test3.removeAlgorithm("J");
        assertEquals(0, test1.sizeOfLibrary());
        assertEquals(0, test2.sizeOfLibrary());
        assertEquals(2, test3.sizeOfLibrary());
        test3.removeAlgorithm("A");
        assertEquals(1, test3.sizeOfLibrary());
    }

    @Test
    public void sizeOfLibraryTest() {
        assertEquals(0,test1.sizeOfLibrary());
        assertEquals(1,test2.sizeOfLibrary());
        assertEquals(2,test3.sizeOfLibrary());
    }

    @Test
    public void getLibraryTest() {
        test2.setLibrary("3x3");
        assertEquals("3x3", test2.getLibrary().getName());
    }

    @Test
    public void setAlgorithmTest() {
        assertFalse(test1.setAlgorithm("J"));
        assertTrue(test2.setAlgorithm("J"));
        assertTrue(test3.setAlgorithm("A"));
        assertFalse(test3.setAlgorithm("J"));
    }

    @Test
    public void getAlgorithmTest() {
        assertEquals("[]", test1.getAlgorithm());
        assertEquals("[R]", test2.getAlgorithm());
        assertEquals("[U, F]", test3.getAlgorithm());
    }

    @Test
    public void getAlgorithmListTest() {
        assertEquals("R", test2.getAlgorithmList().get(0));
        assertEquals("U", test3.getAlgorithmList().get(0));
        assertEquals("F", test3.getAlgorithmList().get(1));
    }

    @Test
    public void getAlgorithmObject() {
        test2.setLibrary("3x3");
        test2.setAlgorithm("J");
        assertEquals("J", test2.getAlgorithmObject().getName());
    }

    @Test
    public void sizeOfAlgorithmTest() {
        assertEquals(0, test1.sizeOfAlgorithm());
        assertEquals(1, test2.sizeOfAlgorithm());
        assertEquals(2, test3.sizeOfAlgorithm());
    }

    @Test
    public void getAlgorithmNameTest() {
        if (test2.setAlgorithm("J") && test3.setAlgorithm("A")) {
            assertEquals("null", test1.getAlgorithmName());
            assertEquals("J", test2.getAlgorithmName());
            assertEquals("A", test3.getAlgorithmName());
        }
    }

    @Test
    public void addAlgorithmMoveTest() {
        assertEquals(0, test1.sizeOfAlgorithm());
        assertEquals(1, test2.sizeOfAlgorithm());
        assertEquals(2, test3.sizeOfAlgorithm());
    }

    @Test
    public void deleteLastAlgorithmMoveTest() {
        test1.deleteLastAlgorithmMove();
        test2.deleteLastAlgorithmMove();
        test3.deleteLastAlgorithmMove();
        assertEquals(0, test1.sizeOfAlgorithm());
        assertEquals(0, test2.sizeOfAlgorithm());
        assertEquals(1, test3.sizeOfAlgorithm());
    }

    @Test
    public void addAlgorithmTimeTest() {
        assertEquals(0, test1.lengthOfAlgorithmTimes());
        assertEquals(1, test2.lengthOfAlgorithmTimes());
        assertEquals(2, test3.lengthOfAlgorithmTimes());
    }

    @Test
    public void deleteLastAlgorithmTimeTest() {
        test1.deleteLastAlgorithmTime();
        test2.deleteLastAlgorithmTime();
        test3.deleteLastAlgorithmTime();
        assertEquals(0, test1.lengthOfAlgorithmTimes());
        assertEquals(0, test2.lengthOfAlgorithmTimes());
        assertEquals(1, test3.lengthOfAlgorithmTimes());
    }

    @Test
    public void lengthOfAlgorithmTimesTest() {
        assertEquals(0, test1.lengthOfAlgorithmTimes());
        assertEquals(1, test2.lengthOfAlgorithmTimes());
        assertEquals(2, test3.lengthOfAlgorithmTimes());
    }

    @Test
    public void displayTimesTest() {
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        test1.displayTimes();
        test2.displayTimes();
        test3.displayTimes();
        assertEquals("[]\n" +
                "\tThe average time is: 0.0\n" +
                "[3.4]\n" +
                "\tThe average time is: 3.4\n" +
                "[2.3, 1.2]\n" +
                "\tThe average time is: 1.75", outputStreamCaptor.toString().trim());
    }

    @Test
    public void getTimesTest() {
        assertEquals("[]", test1.getTimes());
        assertEquals("[3.4]", test2.getTimes());
        assertEquals("[2.3, 1.2]", test3.getTimes());
    }

    @Test
    public void getAverageTimeTest() {
        assertEquals("The average time is: 0.0", test1.getAverageTime());
        assertEquals("The average time is: 3.4", test2.getAverageTime());
        assertEquals("The average time is: 1.75", test3.getAverageTime());
    }

    @Test
    public void toJsonTest() {
        JSONObject json = test3.toJson();
        JSONArray jsonArray = json.getJSONArray("libraries");
        assertEquals("2x2", jsonArray.getJSONObject(0).getString("name"));
        assertEquals("4x4", jsonArray.getJSONObject(1).getString("name"));
    }
}
