package deprecated;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Created by Andrew Michel on 12/8/2017.
 */
/* Deprecated nested class in Main
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
*/