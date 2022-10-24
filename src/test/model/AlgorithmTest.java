package model;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AlgorithmTest {
    Algorithm test1;
    Algorithm test2;
    Algorithm test3;
    @BeforeEach
    void runBefore () {
        test1 = new Algorithm("Perm");
        test2 = new Algorithm("APerm");
        test3 = new Algorithm("JPerm");
        test2.addMove("R");
        test3.addMove("U");
        test3.addMove("F");
        test2.addTime(0.3);
        test3.addTime(0.4);
        test3.addTime(0.5);
    }

    @Test
    void getLibrary() {
        Library library1 = new Library("F");
        test1.setLibrary(library1);
        assertEquals(library1, test1.getLibrary());
    }

    @Test
    void setLibrary() {
        Library library1 = new Library("F");
        test1.setLibrary(library1);
        assertEquals(library1, test1.getLibrary());
        test1.setLibrary(library1);
        assertEquals(library1, test1.getLibrary());
    }

    @Test
    void addMoveTest() {
        assertEquals(0, test1.lengthOfAlgorithm());
        assertEquals(1, test2.lengthOfAlgorithm());
        assertEquals(2, test3.lengthOfAlgorithm());
    }

    @Test
    public void deleteLastMoveTest() {
        test1.deleteLastMove();
        test2.deleteLastMove();
        test3.deleteLastMove();
        assertEquals(0, test1.lengthOfAlgorithm());
        assertEquals(0, test2.lengthOfAlgorithm());
        assertEquals(1, test3.lengthOfAlgorithm());
    }

    @Test
    public void addTimeTest() {
        assertEquals(0, test1.lengthOfTime());
        assertEquals(1, test2.lengthOfTime());
        assertEquals(2, test3.lengthOfTime());
    }

    @Test
    public void deleteLastTimeTest() {
        test1.deleteLastTime();
        test2.deleteLastTime();
        test3.deleteLastTime();
        assertEquals(0, test1.lengthOfTime());
        assertEquals(0, test2.lengthOfTime());
        assertEquals(1, test3.lengthOfTime());
    }

    @Test
    public void lengthOfAlgorithmTest() {
        assertEquals(0, test1.lengthOfAlgorithm());
        assertEquals(1, test2.lengthOfAlgorithm());
        assertEquals(2, test3.lengthOfAlgorithm());
    }

    @Test
    public void averageTimeTest() {
        assertEquals(0, test1.averageTime());
        assertEquals(0.3, test2.averageTime());
        assertEquals(0.45, test3.averageTime());
    }

    @Test
    public void lengthOfTimeTest() {
        assertEquals(0, test1.lengthOfTime());
        assertEquals(1, test2.lengthOfTime());
        assertEquals(2, test3.lengthOfTime());
    }

    @Test
    public void getNameTest() {
        assertEquals("Perm", test1.getName());
        assertEquals("APerm", test2.getName());
        assertEquals("JPerm", test3.getName());
    }

    @Test
    public void getAlgorithmTest() {
        assertEquals("[]", test1.getAlgorithm());
        assertEquals("[R]", test2.getAlgorithm());
        assertEquals("[U, F]", test3.getAlgorithm());
    }

    @Test
    public void getTimesTest() {
        assertEquals("[]", test1.getTimes());
        assertEquals("[0.3]", test2.getTimes());
        assertEquals("[0.4, 0.5]", test3.getTimes());
    }

    @Test
    public void getMovesListTest() {
        assertEquals(0, test1.getMovesList().size());
        assertEquals(1, test2.getMovesList().size());
        assertEquals(2, test3.getMovesList().size());
    }

    @Test
    public void getTimesListTest() {
        assertEquals(0, test1.getTimesList().size());
        assertEquals(1, test2.getTimesList().size());
        assertEquals(2, test3.getTimesList().size());
    }

    @Test
    public void toJsonTest() {
        JSONObject json = test3.toJson();
        assertEquals("U", json.getJSONArray("moves").getString(0));
        assertEquals("F", json.getJSONArray("moves").getString(1));
        assertEquals(0.4, json.getJSONArray("times").getDouble(0));
        assertEquals(0.5, json.getJSONArray("times").getDouble(1));
    }
}