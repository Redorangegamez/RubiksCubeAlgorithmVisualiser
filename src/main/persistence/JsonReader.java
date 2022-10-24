package persistence;

import model.Algorithm;
import model.AlgorithmApp;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads AlgorithmApp from JSON data stored in file.
// Modeled from CPSC 210 JSONSerialization-Demo.
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads AlgorithmApp from file and returns it;
    // throws IOException if an error occurs reading data from file
    public AlgorithmApp read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAlgorithmApp(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses AlgorithmApp from JSON object and returns it
    private AlgorithmApp parseAlgorithmApp(JSONObject jsonObject) {
        AlgorithmApp aa = new AlgorithmApp();
        addLibraries(aa, jsonObject);
        return aa;
    }

    // MODIFIES: aa
    // EFFECTS: parses libraries from JSON object and adds them to AlgorithmApp
    private void addLibraries(AlgorithmApp aa, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("libraries");
        for (Object json : jsonArray) {
            JSONObject childLibrary = (JSONObject) json;
            addLibrary(aa, childLibrary);
        }
    }

    // MODIFIES: aa
    // EFFECTS: parses an individual library from JSON object and adds it to algorithmApp
    private void addLibrary(AlgorithmApp aa, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        aa.addLibrary(name);
        aa.setLibrary(name);
        JSONArray jsonArray = jsonObject.getJSONArray("algorithms");
        for (Object json : jsonArray) {
            JSONObject childAlgorithm = (JSONObject) json;
            aa.addAlgorithmToLibrary(addAlgorithm(childAlgorithm));
        }
    }

    // EFFECTS: parses an individual algorithm from JSON object and adds it to a library
    private Algorithm addAlgorithm(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Algorithm algo = new Algorithm(name);
        addMoves(algo, jsonObject.getJSONArray("moves"));
        addTimes(algo, jsonObject.getJSONArray("times"));
        return algo;
    }

    //MODIFIES: algo
    // EFFECTS: parses the moves from JSON object and adds it to an algorithm
    private void addMoves(Algorithm algo, JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            algo.addMove(jsonArray.getString(i));
        }
    }

    //MODIFIES: algo
    // EFFECTS: parses the times from JSON object and adds it to an algorithm
    private void addTimes(Algorithm algo, JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            algo.addTime(jsonArray.getDouble(i));
        }
    }
}
