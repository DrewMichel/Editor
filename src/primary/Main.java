package primary;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andrew Michel on 12/6/2017.
 */
// TODO: Should keyMappings be used if the inherit key delay would be desired? Change to HashSet?
public class Main extends Application
{
    // Constants
    public static final String DEFAULT_WINDOW_TITLE = "Editor";
    public static final int DEFAULT_WINDOW_WIDTH = 1080, DEFAULT_WINDOW_HEIGHT = 720;
    public static final double DEFAULT_TEXT_WRAPPING_WIDTH = 800;

    // Instance variables
    private HashMap<KeyCode, Boolean> keyMappings;

    private Stage primaryStage;
    private BorderPane primaryPane;
    private Scene primaryScene;

    private MenuBar primaryMenuBar;
    private Menu fileMenu;
    private Menu optionsMenu;

    private Menu helpMenu;

    private MenuItem newMenuItem;
    private MenuItem openMenuItem;
    private MenuItem saveMenuItem;
    private MenuItem saveAsMenuItem;

    private MenuItem helpMenuItem;

    // TODO: use StringBuffer instead?
    private StringBuilder primaryStringBuilder;
    private Text primaryText;
    private TextArea primaryTextArea;
    private HTMLEditor primaryHTMLEditor;

    private File fileSavePath;
    private boolean changeOccured;

    // Enforce generics on declaration and initialization?
    private EventHandler keyPressedListener, keyReleasedListener;
    private EventHandler fileActionListener;

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        // primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setResizable(true);

        this.primaryStage = primaryStage;

        primaryStage.getIcons().add(new Image("file:EditorIcon.png"));

        Platform.setImplicitExit(true);

        // Initializes instance variables
        initializeFields();

        primaryStage.setTitle(DEFAULT_WINDOW_TITLE);
        primaryStage.setScene(primaryScene);

        primaryStage.isFocused();

        // Add to primaryPane or deeper pane?
        //primaryPane.setOnKeyPressed(keyPressedListener);
        //primaryPane.setOnKeyReleased(keyReleasedListener);
        primaryPane.requestFocus();





        primaryStage.show();
    }

    private boolean initializeFields()
    {
        changeOccured = false;
        fileSavePath = null;

        primaryPane = new BorderPane();

        primaryScene = new Scene(primaryPane, DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);

        primaryMenuBar = new MenuBar();
        primaryMenuBar.prefWidthProperty().bind(primaryStage.widthProperty());

        fileMenu = new Menu("File");
        optionsMenu = new Menu("Options");

        helpMenu = new Menu("Help");

        newMenuItem = new MenuItem("New");
        openMenuItem = new MenuItem("Open");
        saveMenuItem = new MenuItem("Save");
        saveAsMenuItem = new MenuItem("Save As");

        fileActionListener = new FileActionListener();

        newMenuItem.setOnAction(fileActionListener);
        openMenuItem.setOnAction(fileActionListener);
        saveMenuItem.setOnAction(fileActionListener);
        saveAsMenuItem.setOnAction(fileActionListener);

        helpMenuItem = new CheckMenuItem("404");
        helpMenuItem.setDisable(true);

        //newMenuItem.setOnAction(actionEvent -> Platform.exit());

        fileMenu.getItems().addAll(newMenuItem, openMenuItem, saveMenuItem, saveAsMenuItem);

        helpMenu.getItems().addAll(helpMenuItem);

        primaryMenuBar.getMenus().addAll(fileMenu, optionsMenu, helpMenu);
        primaryPane.setTop(primaryMenuBar);

        populateKeyMappings();

        primaryStringBuilder = new StringBuilder();

        //primaryText = new Text(100,100, "");
        //primaryText.setWrappingWidth(DEFAULT_TEXT_WRAPPING_WIDTH);
        //HTMLEditor htmlEditor = new HTMLEditor();

        //primaryPane.getChildren().add(primaryText);
        //htmlEditor.setMaxWidth(500);
        //htmlEditor.setHtmlText("TEXT");
        //primaryPane.setCenter(htmlEditor);

        //primaryTextArea = new TextArea();

        primaryHTMLEditor = new HTMLEditor();

        primaryPane.setCenter(primaryHTMLEditor);


        //primaryPane.getChildren().add(primaryText);

        // primaryText.setCursor(...);
        //primaryText.setFont(Font.font("Verdana", FontWeight.BOLD, 70));

        //keyPressedListener = new EditorKeyPressedListener();
        //keyReleasedListener = new EditorKeyReleasedListener();

        return true;
    }

    private boolean populateKeyMappings()
    {
        KeyCode[] keyCodes = KeyCode.values();

        keyMappings = new HashMap<>(keyCodes.length);

        for(int i = 0; i < keyCodes.length; i++)
        {
            keyMappings.put(keyCodes[i], false);
        }

        //displayKeyMappings();

        return true;
    }

//    private boolean populateKeyMappings(File path)
//    {
//        Scanner fileReader = null;
//
//        keyMappings = new HashMap<>();
//
//        KeyCode current = null;
//
//        try
//        {
//            fileReader = new Scanner(new FileInputStream(path));
//
//            while(fileReader.hasNext())
//            {
//                current = KeyCode.getKeyCode(fileReader.next());
//
//                System.out.println(current + " " + fileReader.next());
//
//                if(current != null)
//                {
//                    keyMappings.put(current, false);
//                }
//
//                //fileReader.next();
//            }
//
//            fileReader.close();
//        }
//        catch(FileNotFoundException e)
//        {
//            e.printStackTrace();
//        }
//
//        displayKeyMappings();
//
//        return true;
//    }
//
//    private boolean populateKeyMappings(String path)
//    {
//        return populateKeyMappings(new File(path));
//    }

    private void displayKeyMappings()
    {
        for(Map.Entry<KeyCode, Boolean> entry : keyMappings.entrySet())
        {
            System.err.println(entry.getKey().getName() + " " + entry.getValue());
        }

        System.err.println("NUMBER OF KEYS: " + keyMappings.size());
    }

    private class FileActionListener implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        {
            //System.err.println(event.toString());
            //System.err.println(((MenuItem)event.getSource()).getText());

            String actionCommand = ((MenuItem)event.getSource()).getText();

            if(actionCommand.equals("New"))
            {

            }
            else if(actionCommand.equals("Open"))
            {
                readFile(openFile());
                clearPrimaryStringBuilder();
            }
            else if(actionCommand.equals("Save"))
            {
                saveFile(fileSavePath);
            }
            else if(actionCommand.equals("Save As"))
            {
                saveFile(selectSaveAsFile());
            }
            else
            {
                throw new IllegalActionListenerException();
            }
        }
    }

    private class EditorKeyPressedListener implements EventHandler<KeyEvent>
    {
        public EditorKeyPressedListener()
        {
            super();
        }

        @Override
        public void handle(KeyEvent event)
        {
            if(keyMappings.containsKey(event.getCode()))
            {
                if(event.getCode() == KeyCode.BACK_SPACE)
                {
                    if(primaryStringBuilder.length() > 0)
                    {
                        primaryStringBuilder.setLength(primaryStringBuilder.length() - 1);
                    }
                }
                else
                {
                    primaryStringBuilder.append(event.getText());
                }

                primaryText.setText(primaryStringBuilder.toString());

                System.err.println(event);
                System.err.println(primaryStringBuilder.length());
                System.err.println(primaryText.getText().length());
            }
        }
    }

    private class EditorKeyReleasedListener<T extends KeyEvent> implements EventHandler<T>
    {
        public EditorKeyReleasedListener()
        {
            super();
        }

        @Override
        public void handle(T event)
        {
            if(keyMappings.containsKey(event.getCode()))
            {
                System.err.println(event);
            }
        }
    }

    private File selectSaveAsFile()
    {
        File selected = null;

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");

        if(fileSavePath != null)
        {
            fileChooser.setInitialDirectory(fileSavePath.getParentFile());
        }

        selected = fileChooser.showSaveDialog(primaryStage);

        return selected;
    }

    private boolean saveFile(File path)
    {
        PrintWriter saveFileWriter = null;

        BufferedReader textReader = null;

        String current = null;

        if(path != null)
        {
            try
            {
                // TODO: Change to Text area
                textReader = new BufferedReader(new StringReader(primaryHTMLEditor.getHtmlText()));

                saveFileWriter = new PrintWriter(path);

                while((current = textReader.readLine()) != null)
                {
                    saveFileWriter.println(current);
                }

                saveFileWriter.close();

                textReader.close();

                fileSavePath = path;
            }
            catch(FileNotFoundException e)
            {
                e.printStackTrace();

                return false;
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }

        return true;
    }

    private File openFile()
    {
        File opened = null;

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        opened = fileChooser.showOpenDialog(primaryStage);

        return opened;
    }

    private StringBuilder clearPrimaryStringBuilder()
    {
        StringBuilder sb = new StringBuilder(primaryStringBuilder);

        primaryStringBuilder.setLength(0);

        return sb;
    }

    private boolean readFile(File path)
    {
        BufferedReader openFileReader = null;

        final String newLine = "<br>";

        String line = null;

        StringBuilder sb = new StringBuilder();

        if(path != null)
        {
            try
            {
                openFileReader = new BufferedReader(new FileReader(path));

                while((line = openFileReader.readLine()) != null)
                {
                    sb.append(line + newLine);
                }

                openFileReader.close();

                fileSavePath = path;

                // TODO: Replace with String
                //primaryTextArea.setText(sb.toString());
                primaryHTMLEditor.setHtmlText(sb.toString());

                return true;
            }
            catch(FileNotFoundException e)
            {
                e.printStackTrace();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }

        return false;
    }
}

