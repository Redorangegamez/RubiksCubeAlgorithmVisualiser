package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import model.AlgorithmApp;

import persistence.JsonReader;
import persistence.JsonWriter;

//Describes the User Interface of the Algorithm app.
public class App {
    private AlgorithmApp algorithmApp;
    private Scanner input;
    private static final String JSON_STORE = "./data/App.json";
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    final String[] moves = {"R", "R'", "R2", "U", "U'", "U2", "L", "L'",
            "L2", "B", "B'", "B2", "D", "D'", "D2", "F", "F'", "F2"};

    //EFFECTS: initiates variables and allows the user to access the libraries.
    public App() {
        algorithmApp = new AlgorithmApp();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        init();
        accessLibraries();
    }

    //MODIFIES: this
    //EFFECTS: processes the user's input
    public void accessLibraries() {
        boolean keepGoing = true;
        String command;

        while (keepGoing) {
            displayLibraries();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                askUserForSave();
                keepGoing = false;
            } else {
                processLibrariesCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    //MODIFIES: this
    //EFFECTS: initiates an array of libraries and the Scanner function for user inputs.
    private void init() {
        input = new Scanner(System.in);
    }

    //EFFECTS: allows user to choose if they want to save their work.
    private void askUserForSave() {
        String command;
        boolean notYesOrNo = true;
        while (notYesOrNo) {
            System.out.println("Would you like to save your work?");
            System.out.println("y -> yes");
            System.out.println("n -> no");
            command = input.next();
            if (command.equals("y")) {
                saveApp();
                notYesOrNo = false;
            } else if (command.equals("n")) {
                notYesOrNo = false;
            }
        }
    }

    //EFFECTS: displays libraries menu for user to choose options.
    private void displayLibraries() {
        System.out.println("\nSelect from:");
        System.out.println("\tc -> create new library");
        System.out.println("\ts -> select an existing library");
        System.out.println("\td -> delete an existing library");
        System.out.println("\tsave -> save App to file");
        System.out.println("\tl -> load App from file");
        System.out.println("\tq -> quit");
    }

    //EFFECTS: displays library menu for user to choose options.
    private void displayLibrary() {
        System.out.println("\nSelect from:");
        System.out.println("\tc -> create new algorithm");
        System.out.println("\ts -> select an existing algorithm");
        System.out.println("\td -> delete an existing algorithm");
        System.out.println("\tq -> return to libraries");
    }

    //EFFECTS: displays algorithm menu for user to choose options.
    private void displayAlgorithm() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add moves to the algorithm");
        System.out.println("\tl -> find the length of the algorithm");
        System.out.println("\tt -> input times for this algorithm");
        System.out.println("\tq -> return to library");
    }

    //MODIFIES: this
    //EFFECTS: processes the user's command
    private void processLibrariesCommand(String command) {
        switch (command) {
            case "c":
                createLibrary();
                break;
            case "s":
                chooseLibrary();
                break;
            case "d":
                deleteLibrary();
                break;
            case "save":
                saveApp();
                break;
            case "l":
                loadApp();
                break;
            default:
                System.out.println("Selection not valid...");
                break;
        }
    }

    //MODIFIES: this
    //EFFECTS: Let's the user create a library.
    private void createLibrary() {
        String command;

        System.out.println("\tWhat will be the name of your new library?");
        command = input.next();

        algorithmApp.addLibrary(command);
        chooseLibrary();
    }

    //MODIFIES: this
    //EFFECTS: Let's the user delete an existing library. If the library does not exist, return.
    private void deleteLibrary() {
        String command;
        int currentNumberOfLibraries = algorithmApp.sizeOfLibraries();
        if (!algorithmApp.isLibrariesEmpty()) {
            System.out.println("\tWhich library would you like to delete?");
            algorithmApp.printLibraries();
            command = input.next();
            algorithmApp.removeLibrary(command);

            if (currentNumberOfLibraries == algorithmApp.sizeOfLibraries()) {
                System.out.println("\tLibrary does not exist");
            }
            return;
        }
        System.out.println("\tThere are no libraries to delete.");
    }

    //MODIFIES: this
    //EFFECTS: Let's the user access an existing library. If the library does not exist, return.
    private void chooseLibrary() {
        String command;

        System.out.println("\tWhich Library would you like to access?");
        algorithmApp.printLibraries();
        command = input.next();

        if (algorithmApp.setLibrary(command)) {
            accessLibrary();
        }

        System.out.println("\tLibrary does not exist.");
    }

    //MODIFIES: this
    //EFFECTS: processes the user's input
    private void accessLibrary() {
        boolean keepGoing = true;
        String command;

        while (keepGoing) {
            displayLibrary();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processLibraryCommand(command);
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: processes the user's command
    private void processLibraryCommand(String command) {
        switch (command) {
            case "c":
                createAlgorithm();
                break;
            case "s":
                chooseAlgorithm();
                break;
            case "d":
                deleteAlgorithm();
                break;
            default:
                System.out.println("Selection not valid...");
                break;
        }
    }

    //MODIFIES: this
    //EFFECTS: Let's the user create an algorithm.
    private void createAlgorithm() {
        String command;

        System.out.println("\tWhat will be the name of your new algorithm?");
        command = input.next();

        algorithmApp.addAlgorithmToLibrary(command);
        chooseAlgorithm();
    }

    //MODIFIES: this
    //EFFECTS: Let's the user delete an existing algorithm. If the algorithm does not exist, return.
    private void deleteAlgorithm() {
        String command;
        int currentNumberOfAlgorithms = algorithmApp.sizeOfLibrary();
        if (!algorithmApp.isLibraryEmpty()) {
            System.out.println("\tWhich algorithm would you like to delete?");
            algorithmApp.printAlgorithms();
            command = input.next();
            algorithmApp.removeAlgorithm(command);

            if (currentNumberOfAlgorithms == algorithmApp.sizeOfLibrary()) {
                System.out.println("\tAlgorithm does not exist");
            }
            return;

        }
        System.out.println("\tThere are no libraries to delete.");
    }

    //MODIFIES: this
    //EFFECTS: Let's the user access an existing algorithm. If the algorithm does not exist, return.
    private void chooseAlgorithm() {
        String command;

        System.out.println("\tWhich Algorithm would you like to access?");
        algorithmApp.printAlgorithms();

        command = input.next();

        if (algorithmApp.setAlgorithm(command)) {
            accessAlgorithm();
        }
        System.out.println("\tAlgorithm is not in Library");

    }

    //MODIFIES: this
    //EFFECTS: processes the user's input
    private void accessAlgorithm() {
        boolean keepGoing = true;
        String command;
        System.out.println("\t The current algorithm is:" + algorithmApp.getAlgorithm());

        while (keepGoing) {
            displayAlgorithm();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processAlgorithmCommand(command);
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: processes the user's command
    private void processAlgorithmCommand(String command) {
        switch (command) {
            case "a":
                addMoves();
                break;
            case "l":
                lengthOfAlgorithm();
                break;
            case "t":
                addTimes();
                break;
            default:
                System.out.println("Selection not valid...");
                break;
        }
    }

    //EFFECTS: returns the length of the current algorithm
    private void lengthOfAlgorithm() {
        int length = algorithmApp.sizeOfAlgorithm();
        String name = algorithmApp.getAlgorithmName();
        System.out.println("\t The length of " + name + " is " + length);

        accessAlgorithm();
    }

    //MODIFIES: this
    //EFFECTS: processes the user's input
    private void addMoves() {
        String command;
        boolean keepGoing = true;
        displaySelections();
        System.out.println(algorithmApp.getAlgorithm());
        command = input.next();

        while (keepGoing) {
            if (moveIsValid(command)) {
                algorithmApp.addAlgorithmMove(command);
                System.out.println(algorithmApp.getAlgorithm());
                command = input.next();
            } else if (command.equals("f")) {
                keepGoing = false;
            } else if (command.equals("d")) {
                algorithmApp.deleteLastAlgorithmMove();
                System.out.println(algorithmApp.getAlgorithm());
                command = input.next();
            } else {
                System.out.println("Selection not valid... please try again");
                command = input.next();
            }
        }
        System.out.println("\tAlgorithm finished!");
    }

    //EFFECTS: displays moves menu for user to choose options.
    private void displaySelections() {
        System.out.println("\tPlease type a move: ");
        System.out.println("\tAcceptable moves are: R, R', L, L', F, F', B, B', U, U', D, D'");
        System.out.println("\td -> delete the last move inputted");
        System.out.println("\tf -> finished");
    }

    //EFFECTS: returns true if the user's input is in the valid list of moves, else returns false.
    private boolean moveIsValid(String command) {
        for (String i : moves) {
            if (i.equals(command)) {
                return true;
            }
        }
        return false;
    }

    //MODIFIES: this
    //EFFECTS: processes the user's input
    private void addTimes() {
        String command;
        boolean keepGoing = true;
        displayTimes();
        displayStatistics();
        command = input.next();

        while (keepGoing) {
            if (isValid(command)) {
                algorithmApp.addAlgorithmTime(Double.parseDouble(command));
                displayStatistics();
                command = input.next();
            } else if (command.equals("d")) {
                algorithmApp.deleteLastAlgorithmTime();
                displayStatistics();
                command = input.next();
            } else if (command.equals("f")) {
                keepGoing = false;
            } else {
                System.out.println("Selection not valid... please try again");
                command = input.next();
            }
        }
        System.out.println("\tInputting times finished!");
        accessAlgorithm();
    }

    //EFFECTS: displays times menu for user to choose options.
    public void displayTimes() {
        System.out.println("\tPlease enter your time: ");
        System.out.println("\td - delete last time");
        System.out.println("\tf -> finished");
    }

    //EFFECTS: displays statistics menu for user to choose options.
    public void displayStatistics() {
        if (algorithmApp.lengthOfAlgorithmTimes() != 0) {
            algorithmApp.displayTimes();
        } else {
            System.out.println("\tThere are no times");
        }
    }

    //EFFECTS: returns true if the user input is a double, else returns false.
    public boolean isValid(String command) {
        try {
            Double.parseDouble(command);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // EFFECTS: saves the App to file
    private void saveApp() {
        try {
            jsonWriter.open();
            jsonWriter.write(algorithmApp);
            jsonWriter.close();
            System.out.println("Saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads App from file
    private void loadApp() {
        try {
            algorithmApp = jsonReader.read();
            System.out.println("Loaded libraries from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}
