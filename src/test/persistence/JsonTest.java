package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import model.Library;
import model.Algorithm;
import java.util.List;

public class JsonTest {
    protected void checkAlgorithm(String name, List<String> moves,
                                  List<Double> times, Algorithm algo) {
        assertEquals(name, algo.getName());
        List<String> moveList = algo.getMovesList();
        List<Double> timesList = algo.getTimesList();
        for (String s : moveList) {
            assertEquals(moves.get(moveList.indexOf(s)), s);
        }

        for (Double d: timesList) {
            assertEquals(times.get(timesList.indexOf(d)), d);
        }
    }
}
