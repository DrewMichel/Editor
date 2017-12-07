package primary;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.util.HashMap;

/**
 * Created by Andrew Michel on 12/6/2017.
 */
// TODO: Should keyMappings be used if the inherit key delay would be desired?
public class Main extends Application
{
    public static final int DEFAULT_WINDOW_WIDTH = 1080, DEFAULT_WINDOW_HEIGHT = 720;

    private HashMap<KeyCode, Boolean> keyMappings;

    private Stage primaryStage;
    private Pane primaryPane;
    private Scene primaryScene;

    // Enforce generics on declaration and initialization?
    private EventHandler keyPressedListener, keyReleasedListener;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        // primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setResizable(true);

        this.primaryStage = primaryStage;
        Platform.setImplicitExit(true);

        primaryPane = new Pane();
        primaryScene = new Scene(primaryPane, DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);

        primaryStage.setTitle("Editor");
        primaryStage.setScene(primaryScene);

        primaryStage.isFocused();

        keyPressedListener = new EditorKeyPressedListener();
        keyReleasedListener = new EditorKeyReleasedListener();

        // Add to primaryPane or deeper pane?
        primaryPane.setOnKeyPressed(keyPressedListener);
        primaryPane.setOnKeyReleased(keyReleasedListener);
        primaryPane.requestFocus();

        primaryStage.show();
    }

    private boolean populateKeyMappings(File path)
    {
        return true;
    }

    private boolean populateKeyMappings(String path)
    {
        return populateKeyMappings(new File(path));
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
            System.err.println(event);
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
            System.err.println(event);
        }
    }

}

