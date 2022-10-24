package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.ArrayList;
import persistence.Writable;

//Describes the model methods used in the App class.
public class AlgorithmApp implements Writable {
    private List<Library> libraries = new ArrayList<>();
    private Library library = new Library("null");
    private Algorithm algorithm =  new Algorithm("null");

    //MODIFIES: this
    //EFFECTS: adds a library to libraries
    public void addLibrary(String name) {
        libraries.add(new Library(name));
    }

    //EFFECTS: prints all the libraries
    public void printLibraries() {
        for (Library l : libraries) {
            System.out.println(l.getName());
        }
    }

    //EFFECTS: returns a list of all the libraries
    public List<String> getLibraries() {
        List<String> libraryNames = new ArrayList<>();
        for (Library library : libraries) {
            libraryNames.add(library.getName());
        }
        return libraryNames;
    }

    //EFFECTS: returns true if libraries is empty
    public boolean isLibrariesEmpty() {
        return libraries.isEmpty();
    }

    //MODIFIES: this
    //EFFECTS: removes library with given name if it exists, else do nothing.
    public void removeLibrary(String name) {
        libraries.removeIf(l -> l.getName().equals(name));
    }

    //EFFECTS: returns the number of libraries.
    public int sizeOfLibraries() {
        return libraries.size();
    }

    //MODIFIES: this
    //EFFECTS: sets library to the given library name and returns true if it exists.
    public boolean setLibrary(String name) {
        for (Library l : libraries) {
            if (l.getName().equals(name)) {
                library = l;
                return true;
            }
        }
        return false;
    }

    //MODIFIES: this
    //EFFECTS: adds a new algorithm to the library with given name.
    public void addAlgorithmToLibrary(String name) {
        library.addAlgorithm(new Algorithm(name));
    }

    //MODIFIES: this
    //EFFECTS: adds an algorithm to the library.
    public void addAlgorithmToLibrary(Algorithm algo) {
        library.addAlgorithm(algo);
    }

    //EFFECTS: prints all the algorithms in the given library.
    public void printAlgorithms() {
        library.printAlgorithms();
    }

    //EFFECTS: returns true if the library is empty
    public boolean isLibraryEmpty() {
        return library.sizeOfLibrary() == 0;
    }

    //MODIFIES: this
    //EFFECTS: removes algorithm with given name if it exists, else do nothing.
    public void removeAlgorithm(String name) {
        for (Algorithm a : library.getLibrary()) {
            if (a.getName().equals(name)) {
                library.removeAlgorithm(a.getName());
                return;
            }
        }
    }

    //EFFECTS: returns the number of algorithms in the given library.
    public int sizeOfLibrary() {
        return library.sizeOfLibrary();
    }

    //EFFECTS: returns the current library.
    public Library getLibrary() {
        return library;
    }

    //MODIFIES: this
    //EFFECTS: sets algorithm to given name and returns true if it exists.
    public boolean setAlgorithm(String name) {
        if (library.existsAlgorithm(name)) {
            algorithm = library.getAlgorithm(name);
            return true;
        }
        return false;
    }

    //EFFECTS: returns a list (String) of the moves in the algorithm.
    public String getAlgorithm() {
        return algorithm.getAlgorithm();
    }

    //EFFECTS: returns a list of the moves in the algorithm.
    public List<String> getAlgorithmList() {
        return algorithm.getMovesList();
    }

    //EFFECTS: returns the current algorithm.
    public Algorithm getAlgorithmObject() {
        return algorithm;
    }

    //EFFECTS: returns the length of the algorithm.
    public int sizeOfAlgorithm() {
        return algorithm.lengthOfAlgorithm();
    }

    //EFFECTS: returns the name of the algorithm.
    public String getAlgorithmName() {
        return algorithm.getName();
    }

    //MODIFIES: this
    //EFFECTS: adds a move to the algorithm.
    public void addAlgorithmMove(String name) {
        algorithm.addMove(name);
    }

    //MODIFIES: this
    //EFFECTS: deletes the last move that was added. If the algorithm is empty, this function does nothing.
    public void deleteLastAlgorithmMove() {
        algorithm.deleteLastMove();
    }

    //REQUIRES: time must be greater than or equal to 0.
    //MODIFIES: this
    //EFFECTS: Adds a time to the times list.
    public void addAlgorithmTime(Double time) {
        algorithm.addTime(time);
    }

    //MODIFIES: this
    //EFFECTS: Deletes the last time that was added. If the times list is empty, this function does nothing.
    public void deleteLastAlgorithmTime() {
        algorithm.deleteLastTime();
    }

    //MODIFIES: this
    //EFFECTS: returns the number of times entered into the times list.
    public int lengthOfAlgorithmTimes() {
        return algorithm.lengthOfTime();
    }

    //EFFECTS: returns a list of the times and the average time for the algorithm.
    public void displayTimes() {
        System.out.println(algorithm.getTimes());
        System.out.println("\tThe average time is: " + algorithm.averageTime());
    }

    //EFFECTS: returns a String list of the times.
    public String getTimes() {
        return algorithm.getTimes();
    }

    //EFFECTS: returns the average time.
    public String getAverageTime() {
        return "The average time is: " + algorithm.averageTime();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (Library l : libraries) {
            jsonArray.put(l.toJson());
        }
        json.put("libraries", jsonArray);
        return json;
    }
}

