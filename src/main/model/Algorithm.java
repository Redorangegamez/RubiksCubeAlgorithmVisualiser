package model;

import org.json.JSONObject;

import java.util.List;
import java.util.ArrayList;

import persistence.Writable;

//Describes an algorithm for a rubik's cube and also contains a list of times the user inputs.
public class Algorithm implements Writable {
    protected List<String> algo;
    protected List<Double> times;
    protected String name;
    protected Library library;

    //EFFECTS: Algorithm has given name. An empty algorithm and times list are created.
    public Algorithm(String name) {
        algo = new ArrayList<>();
        times = new ArrayList<>();
        this.name = name;
    }

    //EFFECTS: returns the library of the algorithm.
    public Library getLibrary() {
        return library;
    }

    //EFFECTS: sets the library of the current algorithm.
    public void setLibrary(Library library) {
        if (getLibrary() != library) {
            this.library = library;
            library.addAlgorithm(this);
        }
    }

    //MODIFIES: this
    //EFFECTS: Adds a move to the algorithm.
    public void addMove(String move) {
        algo.add(move);
    }

    //MODIFIES: this
    //EFFECTS: Deletes the last move that was added. If the algorithm is empty, this function does nothing.
    public void deleteLastMove() {
        if (algo.size() > 0) {
            algo.remove(algo.size() - 1);
        }
    }

    //REQUIRES: time must be greater than or equal to 0.
    //MODIFIES: this
    //EFFECTS: Adds a time to the times list.
    public void addTime(Double time) {
        times.add(time);
    }

    //MODIFIES: this
    //EFFECTS: Deletes the last time that was added. If the times list is empty, this function does nothing.
    public void deleteLastTime() {
        if (times.size() > 0) {
            times.remove(times.size() - 1);
        }
    }

    //EFFECTS: returns the length of the current algorithm.
    public int lengthOfAlgorithm() {
        return algo.size();
    }

    //EFFECTS: returns the name of algorithm.
    public String getName() {
        return name;
    }

    //EFFECTS: returns a list of the moves in the algorithm.
    public String getAlgorithm() {
        return algo.toString();
    }

    //EFFECTS: returns a list of the times in the times list.
    public String getTimes() {
        return times.toString();
    }

    //EFFECTS: returns a list of the moves in the algorithm.
    public List<String> getMovesList() {
        return algo;
    }

    //EFFECTS: returns a list of the times in the times list.
    public List<Double> getTimesList() {
        return times;
    }

    //EFFECTS: returns the average of all the times in the times list.
    public double averageTime() {
        double total = 0;
        for (double i : times) {
            total = total + i;
        }
        if (times.size() > 0) {
            return total / times.size();
        } else {
            return 0;
        }
    }

    //EFFECTS: returns the number of times entered into the times list.
    public int lengthOfTime() {
        return times.size();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("moves", algo);
        json.put("times", times);
        return json;
    }

}
