import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHendler implements KeyListener {


    boolean keyPressed = false;

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e)
    {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_SPACE)
        {
            keyPressed = true;


        }


    }

    @Override
    public void keyReleased(KeyEvent e)
    {
      int code = e.getKeyCode();

        if(code == KeyEvent.VK_SPACE)
        {
            keyPressed = false;
        }
    }
}
