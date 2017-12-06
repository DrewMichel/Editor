package primary;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

import java.io.File;
import java.util.HashMap;

/**
 * Created by Andrew Michel on 12/6/2017.
 */
// TODO: Make this an inner class of Main so both KeyPressed and KeyReleased have access to same keyMappings instance variable
public class EditorKeyPressedListener<T extends KeyEvent> implements EventHandler<T>
{
    private HashMap<T, Boolean> keyMappings;

    public EditorKeyPressedListener(File path)
    {
        super();

        populateKeyMappings(path);
    }

    public EditorKeyPressedListener(String path)
    {
        super();

        populateKeyMappings(new File(path));
    }

    @Override
    public void handle(T event)
    {

    }

    private boolean populateKeyMappings(File path)
    {
        keyMappings = new HashMap<T, Boolean>();

        // open inputstream to path

        // while has next

        //      key = KeyCode.getKeyCode
        //      if key != null
        //          put KeyCode.getKeyCode, false

        return true;
    }
}
