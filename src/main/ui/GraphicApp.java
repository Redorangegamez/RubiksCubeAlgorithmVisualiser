package ui;

import model.AlgorithmApp;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluErrorString;

//Describes the Graphic User Interface of the Algorithm app.
public class GraphicApp extends JFrame implements ActionListener {
    private static final String JSON_STORE = "./data/App.json";
    JTextField createLibraryField;
    JTextField createAlgorithmField;
    JTextField addMoveField;
    JTextField addTimeField;
    JLabel moveLabel;
    JLabel moveListLabel;
    JLabel timeLabel;
    JLabel timeListLabel;
    JLabel averageTimeLabel;
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    private JPanel panes;
    private AlgorithmApp algorithmApp;
    private JComboBox<String> librarySelectDropDown;
    private JComboBox<String> libraryDeleteDropDown;
    private JComboBox<String> algorithmSelectDropDown;
    private JComboBox<String> algorithmDeleteDropDown;
    final String[] moves = {"R", "R'", "R2", "U", "U'", "U2", "L", "L'",
            "L2", "B", "B'", "B2", "D", "D'", "D2", "F", "F'", "F2"};

    //EFFECTS: initiates the program.
    public GraphicApp() {
        super("Rubik's cube algorithms");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        init();
    }

    //EFFECTS: initializes all the panels.
    private void init() {
        algorithmApp = new AlgorithmApp();
        initFrame();
        initLibraryMenu();
        initCreateLibraryPanel();
        initSelectLibraryPanel();
        initDeleteLibraryPanel();
        initAlgorithmMenu();
        initCreateAlgorithmPanel();
        initSelectAlgorithmPanel();
        initDeleteAlgorithmPanel();
        initMovesMenu();
        initAddMovePanel();
        initAddTimePanel();
        finishFrame();
    }

    //EFFECTS: initializes the first frame.
    private void initFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(600,600));
    }

    //MODIFIES: this
    //EFFECTS: initializes the Library Menu.
    private void initLibraryMenu() {
        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        drawLibraryOptions(menu);
        panes = new JPanel(new CardLayout());
        panes.add(menu, "Menu");
        add(panes);
        directToPanel("Menu");
    }

    //MODIFIES: this
    //EFFECTS: initializes the Algorithm Menu.
    private void initAlgorithmMenu() {
        JPanel algorithm = new JPanel();
        algorithm.setLayout(new BoxLayout(algorithm, BoxLayout.Y_AXIS));
        drawAlgorithmOptions(algorithm);
        panes.add(algorithm, "Algorithm");
    }

    //MODIFIES: this
    //EFFECTS: initializes the Moves Menu.
    private void initMovesMenu() {
        JPanel moves = new JPanel();
        moves.setLayout(new BoxLayout(moves, BoxLayout.Y_AXIS));
        drawMovesOptions(moves);
        panes.add(moves, "Moves");
    }

    //EFFECTS: Finishes initializes frames.
    private void finishFrame() {
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: When an action is performed, executes the next bit of code.
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Quit": case "Save App to file": case "Load App from file":
                saveLoadQuit(command);
                break;
            case "Add Library": case "Create new library": case "Select an existing library":
            case "Select Library": case "Delete an existing library": case "Delete Library":
                libraryActionPerformed(command);
                break;
            case "Create new algorithm": case "Add Algorithm Name": case "Select an existing algorithm":
            case "Select Algorithm": case "Delete an existing algorithm": case "Delete Algorithm":
            case "Return to Libraries Menu":
                algorithmActionPerformed(command);
                break;
            case "Add moves to the algorithm": case "Return to Library Menu": case "Add Move": case "Delete Last Move":
            case "Done": case "Find the length of the algorithm": case "Input times for this algorithm":
            case "View Simulation of Algorithm": case "Add Time": case "Delete Last Time":
                movesActionPerformed(command);
                break;
        }
    }

    //MODIFIES: this
    //EFFECTS: When an action is performed, executes the next bit of code.
    private void saveLoadQuit(String command) {
        switch (command) {
            case "Save App to file":
                save();
                break;
            case "Load App from file":
                load();
                break;
            case "Quit":
                JOptionPane pane = new JOptionPane();
                int n = pane.showConfirmDialog(panes,
                        "Would you like to save?");
                if (n == JOptionPane.YES_OPTION) {
                    save();
                    dispose();
                } else if (n == JOptionPane.NO_OPTION) {
                    dispose();
                }
                break;
        }
    }

    //MODIFIES: this
    //EFFECTS: When an action is performed, executes the next bit of code.
    private void libraryActionPerformed(String command) {
        switch (command) {
            case "Add Library": case "Select Library": case "Delete Library":
                libraryButtonActionPerformed(command);
                break;
            case "Create new library":
                directToPanel("AddLibrary");
                break;
            case "Select an existing library":
                if (algorithmApp.getLibraries().size() > 0) {
                    selectLibrary();
                }
                break;
            case "Delete an existing library":
                if (algorithmApp.getLibraries().size() > 0) {
                    deleteLibrary();
                }
                break;
        }
    }

    //MODIFIES: this
    //EFFECTS: When an action is performed, executes the next bit of code.
    private void libraryButtonActionPerformed(String command) {
        switch (command) {
            case "Add Library":
                if (!createLibraryField.getText().equals("")) {
                    createLibrary();
                }
                break;
            case "Select Library":
                algorithmApp.setLibrary(librarySelectDropDown.getSelectedItem().toString());
                directToPanel("Algorithm");
                break;
            case "Delete Library":
                algorithmApp.removeLibrary(libraryDeleteDropDown.getSelectedItem().toString());
                directToPanel("Menu");
                break;
        }
    }

    //MODIFIES: this
    //EFFECTS: When an action is performed, executes the next bit of code.
    private void algorithmActionPerformed(String command) {
        switch (command) {
            case "Add Algorithm Name": case "Select Algorithm": case "Delete Algorithm":
                algorithmButtonActionPerformed(command);
                break;
            case "Create new algorithm":
                directToPanel("AddAlgorithm");
                break;
            case "Select an existing algorithm":
                if (algorithmApp.getLibrary().getAlgorithms().size() > 0) {
                    selectAlgorithm();
                }
                break;
            case "Delete an existing algorithm":
                if (algorithmApp.getLibrary().getAlgorithms().size() > 0) {
                    deleteAlgorithm();
                }
                break;
            case "Return to Libraries Menu":
                directToPanel("Menu");
                break;
        }
    }

    //MODIFIES: this
    //EFFECTS: When an action is performed, executes the next bit of code.
    private void algorithmButtonActionPerformed(String command) {
        switch (command) {
            case "Add Algorithm Name":
                if (!createAlgorithmField.getText().equals("")) {
                    createAlgorithm();
                }
                break;
            case "Select Algorithm":
                algorithmApp.setAlgorithm(algorithmSelectDropDown.getSelectedItem().toString());
                directToPanel("Moves");
                break;
            case "Delete Algorithm":
                algorithmApp.removeAlgorithm(algorithmDeleteDropDown.getSelectedItem().toString());
                directToPanel("Algorithm");
                break;
        }
    }

    //MODIFIES: this
    //EFFECTS: When an action is performed, executes the next bit of code.
    private void movesActionPerformed(String command) {
        switch (command) {
            case "Add Move": case "Delete Last Move": case "Done": case "Add Time": case "Delete Last Time":
                movesButtonsActionPerformed(command);
                break;
            case "Add moves to the algorithm":
                directToPanel("AddMove");
                break;
            case "Return to Library Menu":
                directToPanel("Algorithm");
                break;
            case "Find the length of the algorithm":
                int size = algorithmApp.sizeOfAlgorithm();
                String algorithm = algorithmApp.getAlgorithm();
                JOptionPane.showMessageDialog(panes,
                        "The length of your algorithm is: " + size + "\n" + algorithm);
                break;
            case "Input times for this algorithm":
                directToPanel("AddTime");
                break;
            case "View Simulation of Algorithm":
                displaySimulation();
                break;
        }
    }

    //MODIFIES: this
    //EFFECTS: When an action is performed, executes the next bit of code.
    private void movesButtonsActionPerformed(String command) {
        switch (command) {
            case "Add Move":
                updateMovesLabel("Add");
                break;
            case "Delete Last Move":
                updateMovesLabel("Delete");
                break;
            case "Done":
                directToPanel("Moves");
                break;
            case "Add Time":
                updateTimesLabel("Add");
                break;
            case "Delete Last Time":
                updateTimesLabel("Delete");
                break;
        }
    }

    //MODIFIES: this
    //EFFECTS: initializes the create library panel.
    private void initCreateLibraryPanel() {
        JPanel createLibrary = new JPanel();
        JButton createLibraryButton = new JButton("Add Library");
        createLibraryButton.addActionListener(this);
        createLibraryField = new JTextField(10);
        createLibrary.add(createLibraryField);
        createLibrary.add(createLibraryButton);
        panes.add(createLibrary, "AddLibrary");
    }

    //MODIFIES: this
    //EFFECTS: initializes the select library panel.
    private void initSelectLibraryPanel() {
        JPanel selectLibrary = new JPanel();
        List<String> currentLibraries = algorithmApp.getLibraries();
        String[] libraries = new String[currentLibraries.size()];
        for (int j = 0; j < currentLibraries.size(); j++) {
            libraries[j] = currentLibraries.get(j);
        }
        librarySelectDropDown = new JComboBox<>(libraries);
        librarySelectDropDown.setPreferredSize(new Dimension(150,30));
        JButton selectLibraryButton = new JButton("Select Library");
        selectLibraryButton.addActionListener(this);
        selectLibrary.add(librarySelectDropDown);
        selectLibrary.add(selectLibraryButton);
        panes.add(selectLibrary, "SelectLibrary");
    }

    //MODIFIES: this
    //EFFECTS: initializes the delete library panel.
    private void initDeleteLibraryPanel() {
        JPanel deleteLibrary = new JPanel();
        List<String> currentLibraries = algorithmApp.getLibraries();
        String[] libraries = new String[currentLibraries.size()];
        for (int j = 0; j < currentLibraries.size(); j++) {
            libraries[j] = currentLibraries.get(j);
        }
        libraryDeleteDropDown = new JComboBox<>(libraries);
        JButton deleteLibraryButton = new JButton("Delete Library");
        deleteLibraryButton.addActionListener(this);
        deleteLibrary.add(libraryDeleteDropDown);
        deleteLibrary.add(deleteLibraryButton);
        panes.add(deleteLibrary, "DeleteLibrary");
    }

    //MODIFIES: this
    //EFFECTS: saves library that is created, resets the create library text field, and directs to next panel.
    private void createLibrary() {
        directToPanel("AddLibrary");
        String command = createLibraryField.getText();
        algorithmApp.addLibrary(command);
        createLibraryField.setText("");
        selectLibrary();
    }

    //MODIFIES: this
    //EFFECTS: adjusts the dropdown list for the user to select from.
    private void selectLibrary() {
        directToPanel("SelectLibrary");
        List<String> currentLibraries = algorithmApp.getLibraries();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (String currentLibrary : currentLibraries) {
            model.addElement(currentLibrary);
        }
        librarySelectDropDown.setModel(model);
        librarySelectDropDown.setSelectedIndex(0);
    }

    //MODIFIES: this
    //EFFECTS: adjusts the dropdown list for the user to delete from.
    private void deleteLibrary() {
        directToPanel("DeleteLibrary");
        List<String> currentLibraries = algorithmApp.getLibraries();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (String currentLibrary : currentLibraries) {
            model.addElement(currentLibrary);
        }
        libraryDeleteDropDown.setModel(model);
        libraryDeleteDropDown.setSelectedIndex(0);
    }

    //EFFECTS: changes the panel to the panel that corresponds to the key.
    private void directToPanel(String string) {
        CardLayout c = (CardLayout) panes.getLayout();
        c.show(panes, string);
    }

    //EFFECTS: adds library option buttons to the library menu.
    private void drawLibraryOptions(JPanel panel) {
        JButton b1 = new JButton("Create new library");
        JButton b2 = new JButton("Select an existing library");
        JButton b3 = new JButton("Delete an existing library");
        JButton b4 = new JButton("Save App to file");
        JButton b5 = new JButton("Load App from file");
        JButton b6 = new JButton("Quit");
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        b6.addActionListener(this);
        panel.add(b1);
        panel.add(b2);
        panel.add(b3);
        panel.add(b4);
        panel.add(b5);
        panel.add(b6);
    }

    //EFFECTS: adds algorithm option buttons to the algorithm menu.
    private void drawAlgorithmOptions(JPanel panel) {
        JButton b1 = new JButton("Create new algorithm");
        JButton b2 = new JButton("Select an existing algorithm");
        JButton b3 = new JButton("Delete an existing algorithm");
        JButton b4 = new JButton("Return to Libraries Menu");
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        panel.add(b1);
        panel.add(b2);
        panel.add(b3);
        panel.add(b4);
    }

    //EFFECTS: adds moves option buttons to the moves menu.
    private void drawMovesOptions(JPanel panel) {
        JButton b1 = new JButton("Add moves to the algorithm");
        JButton b2 = new JButton("Find the length of the algorithm");
        JButton b3 = new JButton("Input times for this algorithm");
        JButton b4 = new JButton("View Simulation of Algorithm");
        JLabel simulationLabel = new JLabel();
        simulationLabel.setText("Use the up down left right arrow keys to move around in the simulation.");
        JButton b5 = new JButton("Return to Library Menu");
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        panel.add(b1);
        panel.add(b2);
        panel.add(b3);
        panel.add(b4);
        panel.add(simulationLabel);
        panel.add(b5);
    }

    //MODIFIES: this
    //EFFECTS: initializes the create algorithm panel.
    private void initCreateAlgorithmPanel() {
        JPanel createAlgorithm = new JPanel();
        JButton addAlgorithmButton = new JButton("Add Algorithm Name");
        addAlgorithmButton.addActionListener(this);
        createAlgorithmField = new JTextField(10);
        createAlgorithm.add(createAlgorithmField);
        createAlgorithm.add(addAlgorithmButton);
        panes.add(createAlgorithm, "AddAlgorithm");
    }

    //MODIFIES: this
    //EFFECTS: initializes the select algorithm panel.
    private void initSelectAlgorithmPanel() {
        JPanel selectAlgorithm = new JPanel();
        List<String> currentLibraries = algorithmApp.getLibrary().getAlgorithms();
        String[] libraries = new String[currentLibraries.size()];
        for (int j = 0; j < currentLibraries.size(); j++) {
            libraries[j] = currentLibraries.get(j);
        }
        algorithmSelectDropDown = new JComboBox<>(libraries);
        JButton selectAlgorithmButton = new JButton("Select Algorithm");
        selectAlgorithmButton.addActionListener(this);
        selectAlgorithm.add(algorithmSelectDropDown);
        selectAlgorithm.add(selectAlgorithmButton);
        panes.add(selectAlgorithm, "selectAlgorithm");
    }

    //MODIFIES: this
    //EFFECTS: initializes the delete algorithm panel.
    private void initDeleteAlgorithmPanel() {
        JPanel deleteAlgorithm = new JPanel();
        List<String> currentLibraries = algorithmApp.getLibraries();
        String[] libraries = new String[currentLibraries.size()];
        for (int j = 0; j < currentLibraries.size(); j++) {
            libraries[j] = currentLibraries.get(j);
        }
        algorithmDeleteDropDown = new JComboBox<>(libraries);
        JButton deleteAlgorithmButton = new JButton("Delete Algorithm");
        deleteAlgorithmButton.addActionListener(this);
        deleteAlgorithm.add(algorithmDeleteDropDown);
        deleteAlgorithm.add(deleteAlgorithmButton);
        panes.add(deleteAlgorithm, "deleteAlgorithm");
    }

    //MODIFIES: this
    //EFFECTS: saves algorithm that is created, resets the create algorithm text field, and directs to next panel.
    private void createAlgorithm() {
        directToPanel("AddAlgorithm");
        String command = createAlgorithmField.getText();
        algorithmApp.addAlgorithmToLibrary(command);
        createAlgorithmField.setText("");
        selectAlgorithm();
    }

    //MODIFIES: this
    //EFFECTS: adjusts the dropdown list for the user to select from.
    private void selectAlgorithm() {
        directToPanel("selectAlgorithm");
        List<String> currentLibraries = algorithmApp.getLibrary().getAlgorithms();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (String currentLibrary : currentLibraries) {
            model.addElement(currentLibrary);
        }
        algorithmSelectDropDown.setModel(model);
        algorithmSelectDropDown.setSelectedIndex(0);
    }

    //MODIFIES: this
    //EFFECTS: adjusts the dropdown list for the user to delete from.
    private void deleteAlgorithm() {
        directToPanel("deleteAlgorithm");
        List<String> currentLibraries = algorithmApp.getLibrary().getAlgorithms();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (String currentLibrary : currentLibraries) {
            model.addElement(currentLibrary);
        }
        algorithmDeleteDropDown.setModel(model);
        algorithmDeleteDropDown.setSelectedIndex(0);
    }

    //MODIFIES: this
    //EFFECTS: initializes the add move panel.
    private void initAddMovePanel() {
        JPanel addMove = new JPanel();
        addMove.setLayout(new BoxLayout(addMove, BoxLayout.Y_AXIS));
        JButton addMoveButton = new JButton("Add Move");
        JButton deleteLastMoveButton = new JButton("Delete Last Move");
        JButton done = new JButton("Done");
        addMoveButton.addActionListener(this);
        deleteLastMoveButton.addActionListener(this);
        done.addActionListener(this);
        addMoveField = new JTextField(100);
        addMoveField.setMaximumSize(addMoveField.getPreferredSize());
        moveLabel = new JLabel("AddMoveDescription");
        moveLabel.setText("Acceptable moves are: R, R', R2, L, L', L2, F, F', F2, B, B', B2, U, U', U2, D, D, D2'\"");
        moveListLabel = new JLabel(algorithmApp.getAlgorithm());
        addMove.add(addMoveField);
        addMove.add(addMoveButton);
        addMove.add(deleteLastMoveButton);
        addMove.add(done);
        addMove.add(moveLabel);
        addMove.add(moveListLabel);
        panes.add(addMove, "AddMove");
    }

    //MODIFIES: this
    //EFFECTS: initializes the add time panel.
    private void initAddTimePanel() {
        JPanel addTime = new JPanel();
        addTime.setLayout(new BoxLayout(addTime, BoxLayout.Y_AXIS));
        JButton addTimeButton = new JButton("Add Time");
        JButton deleteLastTimeButton = new JButton("Delete Last Time");
        JButton done = new JButton("Done");
        addTimeButton.addActionListener(this);
        deleteLastTimeButton.addActionListener(this);
        done.addActionListener(this);
        addTimeField = new JTextField(100);
        addTimeField.setMaximumSize(addTimeField.getPreferredSize());
        timeLabel = new JLabel("AddTimeDescription");
        timeLabel.setText("Add a time! Must be in the form of a Double (eg. 3.3, 2.34, 123)");
        timeListLabel = new JLabel(algorithmApp.getTimes());
        averageTimeLabel = new JLabel("The average time is: ");
        addTime.add(addTimeField);
        addTime.add(addTimeButton);
        addTime.add(deleteLastTimeButton);
        addTime.add(done);
        addTime.add(timeLabel);
        addTime.add(timeListLabel);
        addTime.add(averageTimeLabel);
        panes.add(addTime, "AddTime");
    }

    //MODIFIES: this
    //EFFECTS: updates the moves of the algorithm and the label displaying the algorithm.
    private void updateMovesLabel(String s) {
        if (s.equals("Add")) {
            for (String i: moves) {
                if (i.equals(addMoveField.getText())) {
                    algorithmApp.addAlgorithmMove(addMoveField.getText());
                    addMoveField.setText("");
                    moveListLabel.setText(algorithmApp.getAlgorithm());
                }
            }
        } else if (s.equals("Delete")) {
            algorithmApp.deleteLastAlgorithmMove();
            addMoveField.setText("");
            moveListLabel.setText(algorithmApp.getAlgorithm());
        }
    }

    //MODIFIES: this
    //EFFECTS: updates the times of the algorithm and the label displaying the times.
    private void updateTimesLabel(String s) {
        if (s.equals("Add")) {
            try {
                algorithmApp.addAlgorithmTime(Double.parseDouble(addTimeField.getText()));
                addTimeField.setText("");
                timeListLabel.setText(algorithmApp.getTimes());
                averageTimeLabel.setText(algorithmApp.getAverageTime());
            } catch (NumberFormatException e) {
                //don't do anything if not a double.
            }
        } else if (s.equals("Delete")) {
            algorithmApp.deleteLastAlgorithmTime();
            addTimeField.setText("");
            timeListLabel.setText(algorithmApp.getTimes());
            averageTimeLabel.setText(algorithmApp.getAverageTime());
        }
    }

    // EFFECTS: saves the App to file
    private void save() {
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
    private void load() {
        try {
            algorithmApp = jsonReader.read();
            moveListLabel.setText(algorithmApp.getAlgorithm());
            timeListLabel.setText(algorithmApp.getAlgorithm());
            averageTimeLabel.setText(algorithmApp.getAverageTime());
            System.out.println("Loaded libraries from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    //EFFECTS: displays a 3D simulation of the algorithm.
    private void displaySimulation() {
        Display.setTitle("A Rubik's Cube in LWJGL");
        try {
            Display.setDisplayMode(new DisplayMode(640, 480));
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        try {
            Display.create(new PixelFormat(32, 0, 24, 8, 4));
        } catch (LWJGLException e) {
            System.err.println("Failed to create multi-sampled OpenGL context. Creating normal one.");
            try {
                Display.create();
            } catch (LWJGLException ex) {
                ex.printStackTrace();
                Display.destroy();
                System.exit(1);
            }
        }
        runSimulation();
        System.out.println(gluErrorString(glGetError()));
        Display.destroy();
    }

    //EFFECTS: Runs the 3D simulation of the algorithm.
    public void runSimulation() {
        RubixCamera camera = new RubixCamera(18);
        camera.initialise();
        RubixCube cube = new RubixCube(3);
        cube.initialise();
        int[] indices = {0,0};
        boolean running = true;
        while (running) {
            updateCubeGraphics(cube, indices);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            camera.translate();
            camera.handleInput();
            cube.draw();
            running = !Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_RETURN);
            Display.update();
            Display.sync(60);
        }
    }

    //EFFECTS: Updates the graphics of the cube.
    private void updateCubeGraphics(RubixCube cube, int[] indices) {
        if (indices[0] <= 50) {
            indices[0]++;
        } else {
            if (indices[1] >= algorithmApp.getAlgorithmList().size()) {
                indices[1] = 0;
                updateCube(cube, "Reset");
            } else {
                String s = algorithmApp.getAlgorithmList().get(indices[1]);
                updateCube(cube, s);
                indices[1]++;
            }
            cube.initialise();
            indices[0] = 0;
        }
    }

    //EFFECTS: Updates the cube.
    private void updateCube(RubixCube cube, String s) {
        switch (s) {
            case "R": case "R'": case "R2":
                updateCubeRightSide(cube, s);
                break;
            case "L": case "L'": case "L2":
                updateCubeLeftSide(cube, s);
                break;
            case "U": case "U'": case "U2":
                updateCubeTopSide(cube, s);
                break;
            case "D": case "D'": case "D2":
                updateCubeBottomSide(cube, s);
                break;
            case "F": case "F'": case "F2":
                updateCubeFrontSide(cube, s);
                break;
            case "B": case "B'": case "B2":
                updateCubeBackSide(cube, s);
                break;
            case "Reset":
                cube.reset();
                break;
        }
    }

    //EFFECTS: Updates the right side of the cube.
    private void updateCubeRightSide(RubixCube cube, String s) {
        switch (s) {
            case "R":
                cube.rotateRightSide();
                break;
            case "R2":
                cube.rotateRightSide();
                cube.rotateRightSide();
                break;
            case "R'":
                cube.rotateRightSide();
                cube.rotateRightSide();
                cube.rotateRightSide();
                break;
        }
    }

    //EFFECTS: Updates the left side of the cube.
    private void updateCubeLeftSide(RubixCube cube, String s) {
        switch (s) {
            case "L":
                cube.rotateLeftSide();
                break;
            case "L2":
                cube.rotateLeftSide();
                cube.rotateLeftSide();
                break;
            case "L'":
                cube.rotateLeftSide();
                cube.rotateLeftSide();
                cube.rotateLeftSide();
                break;
        }
    }

    //EFFECTS: Updates the top side of the cube.
    private void updateCubeTopSide(RubixCube cube, String s) {
        switch (s) {
            case "U":
                cube.rotateTopSide();
                break;
            case "U2":
                cube.rotateTopSide();
                cube.rotateTopSide();
                break;
            case "U'":
                cube.rotateTopSide();
                cube.rotateTopSide();
                cube.rotateTopSide();
                break;
        }
    }

    //EFFECTS: Updates the bottom side of the cube.
    private void updateCubeBottomSide(RubixCube cube, String s) {
        switch (s) {
            case "D":
                cube.rotateBottomSide();
                break;
            case "D2":
                cube.rotateBottomSide();
                cube.rotateBottomSide();
                break;
            case "D'":
                cube.rotateBottomSide();
                cube.rotateBottomSide();
                cube.rotateBottomSide();
                break;
        }
    }

    //EFFECTS: Updates the front side of the cube.
    private void updateCubeFrontSide(RubixCube cube, String s) {
        switch (s) {
            case "F":
                cube.rotateFrontSide();
                break;
            case "F2":
                cube.rotateFrontSide();
                cube.rotateFrontSide();
                break;
            case "F'":
                cube.rotateFrontSide();
                cube.rotateFrontSide();
                cube.rotateFrontSide();
                break;
        }
    }

    //EFFECTS: Updates the back side of the cube.
    private void updateCubeBackSide(RubixCube cube, String s) {
        switch (s) {
            case "B":
                cube.rotateBackSide();
                break;
            case "B2":
                cube.rotateBackSide();
                cube.rotateBackSide();
                break;
            case "B'":
                cube.rotateBackSide();
                cube.rotateBackSide();
                cube.rotateBackSide();
                break;
        }
    }

}

