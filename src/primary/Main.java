package primary;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Created by Andrew Michel on 12/6/2017.
 */
public class Main extends Application
{
    public static final int DEFAULT_WINDOW_WIDTH = 1080, DEFAULT_WINDOW_HEIGHT = 720;

    private Stage primaryStage;
    private Pane primaryPane;
    private Scene primaryScene;

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

        primaryStage.show();
    }
}
