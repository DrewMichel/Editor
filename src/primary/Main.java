package primary;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyBooleanPropertyBase;
import javafx.beans.property.adapter.JavaBeanBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
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
    private MenuItem printMenuItem;

    private MenuItem helpMenuItem;

    private CheckMenuItem wrapTextItem;
    private CheckMenuItem limitWidthItem;
    private MenuItem fullScreenItem;

    // TODO: use StringBuffer instead?
    private StringBuilder primaryStringBuilder;
    private TextArea primaryTextArea;

    private File fileSavePath;
    private boolean changeOccured;

    // Enforce generics on declaration and initialization?
    private EventHandler fileActionListener, optionsActionListener;

    //GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
    //Font[] allFonts = e.getAllFonts();

    // Font defaultFont = Font.getDefault();

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
        //primaryPane.requestFocus();

        primaryStage.show();
    }

    private boolean initializeFields()
    {
        changeOccured = false;
        fileSavePath = null;

        primaryPane = new BorderPane();

        primaryStage.setMaximized(true);

        //primaryScene = new Scene(primaryPane, DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
        //primaryScene = new Scene(primaryPane, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight() - 65);
        primaryScene = new Scene(primaryPane, DEFAULT_WINDOW_WIDTH, Screen.getPrimary().getBounds().getHeight() - 65);

        primaryMenuBar = new MenuBar();
        primaryMenuBar.prefWidthProperty().bind(primaryStage.widthProperty());

        fileMenu = new Menu("File");
        optionsMenu = new Menu("Options");

        helpMenu = new Menu("Help");

        newMenuItem = new MenuItem("New");
        openMenuItem = new MenuItem("Open...");
        saveMenuItem = new MenuItem("Save");
        saveAsMenuItem = new MenuItem("Save As...");
        printMenuItem = new MenuItem("Print...");

        fileActionListener = new FileActionListener();

        newMenuItem.setOnAction(fileActionListener);
        openMenuItem.setOnAction(fileActionListener);
        saveMenuItem.setOnAction(fileActionListener);
        saveAsMenuItem.setOnAction(fileActionListener);

        //optionsActionListener = new OptionsActionListener();

        wrapTextItem = new CheckMenuItem("Wrap Text");
        limitWidthItem = new CheckMenuItem("Limit Width");
        fullScreenItem = new MenuItem("Full Screen");

        wrapTextItem.setSelected(true);
        limitWidthItem.setSelected(true);

        fullScreenItem.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                primaryStage.setFullScreen(!primaryStage.isFullScreen());
            }
        });

        //wrapTextItem.setOnAction(optionsActionListener);
        //limitWidthItem.setOnAction(optionsActionListener);

        helpMenuItem = new CheckMenuItem("404");
        helpMenuItem.setDisable(true);

        //newMenuItem.setOnAction(actionEvent -> Platform.exit());

        fileMenu.getItems().addAll(newMenuItem, openMenuItem, saveMenuItem, saveAsMenuItem, printMenuItem);

        optionsMenu.getItems().addAll(wrapTextItem, limitWidthItem, fullScreenItem);

        helpMenu.getItems().addAll(helpMenuItem);

        primaryMenuBar.getMenus().addAll(fileMenu, optionsMenu, helpMenu);
        //primaryPane.setTop(primaryMenuBar);

        MenuBar menu = new MenuBar();

        //menu.setPadding(new Insets(0,0,20,0));
        menu.prefWidthProperty().bind(primaryStage.widthProperty());
        menu.setLayoutY(25);

        Menu m = new Menu("TESTER");

        menu.getMenus().addAll(m);

        //primaryPane.setTop(menu);

        Pane topPane = new Pane();

        topPane.getChildren().addAll(primaryMenuBar, menu);

        primaryPane.setTop(topPane);

        populateKeyMappings();

        primaryStringBuilder = new StringBuilder();

        //primaryText = new Text(100,100, "");
        //primaryText.setWrappingWidth(DEFAULT_TEXT_WRAPPING_WIDTH);
        //HTMLEditor htmlEditor = new HTMLEditor();

        //primaryPane.getChildren().add(primaryText);
        //htmlEditor.setMaxWidth(500);
        //htmlEditor.setHtmlText("TEXT");
        //primaryPane.setCenter(htmlEditor);

        primaryTextArea = new TextArea();

        //primaryTextArea.setMaxHeight(500);
        //primaryTextArea.setLayoutY(-200);

        //primaryTextArea.setDisable(true);
        primaryTextArea.setWrapText(true);


        //primaryTextArea.setPadding(new Insets(-20,0,0,0));


        primaryTextArea.setMaxWidth(800);

        //primaryHTMLEditor = new HTMLEditor();

        Pane centerPane = new Pane();

       // centerPane.getChildren().addAll( primaryTextArea);

        //primaryPane.setCenter(primaryTextArea);
        primaryPane.setCenter(primaryTextArea);

        wrapTextItem.selectedProperty().bindBidirectional(primaryTextArea.wrapTextProperty());

        primaryTextArea.requestFocus();

        //MenuBar footer = new MenuBar();
        //footer.prefWidthProperty().bind(primaryStage.widthProperty());

        //footer.getMenus().add(new Menu("FOOTER"));

        Pane footerPane = new Pane();
        Label footerLabel = new Label();


        footerPane.getChildren().add(footerLabel);

        footerPane.setStyle("-fx-background: #665A57");

        footerPane.prefWidthProperty().bind(primaryStage.widthProperty());

        footerLabel.setStyle("-fx-background: #665A57");

        primaryPane.setStyle("-fx-background: #363535");

        primaryPane.setBottom(footerPane);

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

    private void displayKeyMappings()
    {
        for(Map.Entry<KeyCode, Boolean> entry : keyMappings.entrySet())
        {
            System.err.println(entry.getKey().getName() + " " + entry.getValue());
        }

        System.err.println("NUMBER OF KEYS: " + keyMappings.size());
    }


    /*
    private class OptionsActionListener implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        {
            // TODO: Change to CheckMenuItem?
            String actionCommand = ((MenuItem)event.getSource()).getText();
            // ((CheckMenuItem) event.getSource()).isSelected().isEqualTo(); // equals(true);
            // ((CheckMenuItem) event.getSource()).selectedProperty().bindBidirectional(primaryTextArea.wrapTextProperty()); //TODO: Move to initialize method DOUBLE CHECK

            System.err.println(actionCommand);

            if(actionCommand.equals("Full Screen"))
            {

                primaryStage.setMaximized(((CheckMenuItem)event.getSource()).selectedProperty().get());
            }
        }
    }
    */


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
            else if(actionCommand.equals("Open..."))
            {
                readFile(openFile());
                clearPrimaryStringBuilder();
            }
            else if(actionCommand.equals("Save"))
            {
                saveFile(fileSavePath);
            }
            else if(actionCommand.equals("Save As..."))
            {
                saveFile(selectSaveAsFile());
            }
            else if(actionCommand.equals("Print..."))
            {

            }
            else
            {
                throw new IllegalActionListenerException();
            }
        }
    }

    private File selectSaveAsFile()
    {
        File selected = null;

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text File",".txt"), new FileChooser.ExtensionFilter("Java File", ".java"));
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Text File", ".txt"));

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
                textReader = new BufferedReader(new StringReader(primaryTextArea.getText()));

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

        final String newLine = "\n";

        String line = null;

        StringBuilder sb = new StringBuilder();

        if(path != null)
        {
            try
            {
                openFileReader = new BufferedReader(new FileReader(path));

                primaryTextArea.clear();

                while((line = openFileReader.readLine()) != null)
                {
                    primaryTextArea.appendText(line + newLine);
                }

                openFileReader.close();

                fileSavePath = path;

                // TODO: Replace with String
                //primaryTextArea.setText(sb.toString());
                //primaryTextArea.setText(sb.toString());

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

