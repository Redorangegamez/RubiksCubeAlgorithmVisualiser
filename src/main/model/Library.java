package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.ArrayList;

import persistence.Writable;

//Describes a library that contains multiple algorithms.
public class Library implements Writable {
    protected String name;
    protected List<Algorithm> library;

    //EFFECT: A library has a given name. An empty library is created.
    public Library(String name) {
        this.name = name;
        library = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: Adds an algorithm to the library
    public void addAlgorithm(Algorithm algo) {
        if (!library.contains(algo)) {
            library.add(algo);
            algo.setLibrary(this);
        }
    }

    //MODIFIES: this
    //EFFECTS: removes a given algorithm (by name) if it is in the library, else do nothing.
    public void removeAlgorithm(String nameOfAlgo) {
        int position = -1;
        for (Algorithm algo : library) {
            if (algo.getName().equals(nameOfAlgo)) {
                position = library.indexOf(algo);
            }
        }
        if (position != -1) {
            library.remove(library.get(position));
        }
    }

    //EFFECTS: returns true if the given algorithm (name) is in the library, else returns false.
    public boolean existsAlgorithm(String nameOfAlgo) {
        for (Algorithm algo : library) {
            if (algo.getName().equals(nameOfAlgo)) {
                return true;
            }
        }
        return false;
    }

    //EFFECTS: returns the name of the library.
    public String getName() {
        return name;
    }

    //REQUIRES: Library contains given Algorithm (name)
    //EFFECTS: returns Algorithm corresponding to given name.
    public Algorithm getAlgorithm(String nameOfAlgo) {
        for (Algorithm algo:library) {
            if (algo.getName().equals(nameOfAlgo)) {
                return algo;
            }
        }
        return null;
    }

    //Returns an list of all the algorithm names in the library
    public List<String> getAlgorithms() {
        List<String> algoNames = new ArrayList<>();
        for (Algorithm algo:library) {
            algoNames.add(algo.getName());
        }
        return algoNames;
    }

    //EFFECTS: prints all the algorithms in the library.
    public void printAlgorithms() {
        for (Algorithm a : library) {
            System.out.println(a.getName());
        }
    }

    //EFFECTS: returns the library.
    public List<Algorithm> getLibrary() {
        return library;
    }

    //EFFECTS: returns the number of algorithms in the library.
    public int sizeOfLibrary() {
        return library.size();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("algorithms", algorithmsToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray algorithmsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Algorithm algo: library) {
            jsonArray.put(algo.toJson());
        }

        return jsonArray;
    }

}
