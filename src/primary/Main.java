package primary;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Andrew Michel on 12/6/2017.
 */
// TODO: Should keyMappings be used if the inherit key delay would be desired? Change to HashSet?
public class Main extends Application
{
    public static final String DEFAULT_WINDOW_TITLE = "Editor";
    public static final int DEFAULT_WINDOW_WIDTH = 1080, DEFAULT_WINDOW_HEIGHT = 720;
    public static final double DEFAULT_TEXT_WRAPPING_WIDTH = 800;

    private HashMap<KeyCode, Boolean> keyMappings;

    private Stage primaryStage;
    private Pane primaryPane;
    private Scene primaryScene;

    // TODO: use StringBuffer instead?
    private StringBuilder primaryStringBuilder;
    private Text primaryText;

    // Enforce generics on declaration and initialization?
    private EventHandler keyPressedListener, keyReleasedListener;

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
        primaryPane.setOnKeyPressed(keyPressedListener);
        primaryPane.setOnKeyReleased(keyReleasedListener);
        primaryPane.requestFocus();

        //primaryPane.getChildren().add(primaryText);

        Pane textPane = new Pane();
        textPane.getChildren().add(primaryText);
        primaryPane.getChildren().add(textPane);

        primaryStage.show();
    }

    private boolean initializeFields()
    {
        primaryPane = new Pane();

        primaryScene = new Scene(primaryPane, DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);

        populateKeyMappings();

        primaryStringBuilder = new StringBuilder();
        primaryText = new Text(100, 100, "");
        primaryText.setWrappingWidth(DEFAULT_TEXT_WRAPPING_WIDTH);

        // primaryText.setCursor(...);
        //primaryText.setFont(Font.font("Verdana", FontWeight.BOLD, 70));

        keyPressedListener = new EditorKeyPressedListener();
        keyReleasedListener = new EditorKeyReleasedListener();

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



    private class EditorKeyPressedListener<T extends KeyEvent> implements EventHandler<T>
    {
        public EditorKeyPressedListener()
        {
            super();
        }

        @Override
        public void handle(T event)
        {
            if(keyMappings.containsKey(event.getCode()))
            {
                primaryStringBuilder.append(event.getText());

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

}

