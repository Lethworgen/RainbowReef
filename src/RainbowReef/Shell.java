package RainbowReef;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.Observer;
import java.util.Observable;


public class Shell extends CollidableObj implements Observer{
    //keys for movement
    private final int left, right;
    private boolean LeftPressed, RightPressed;
    private final int LeftBounds, RightBounds;
    
    public Shell(Image img, int x, int y, int leftKey, int rightKey, int leftbounds, int rightbounds)
    {
        super(img, x, y);
        
        this.left = leftKey;
        this.right = rightKey;
        this.LeftPressed =  false;
        this.RightPressed = false;
        this.LeftBounds = leftbounds;
        this.RightBounds = rightbounds;
    }
    
    public void MoveUpdate()
    {
        if(this.LeftPressed)
            this.x -= 9;
        if(this.RightPressed)
            this.x += 9;
        
        if(this.x < this.LeftBounds)
            this.x = this.LeftBounds;
        if(this.x + this.width > this.RightBounds)
            this.x = this.RightBounds -this.width;
    }
    
    @Override
    public void update(Observable obj, Object arg){
        GameEvents ge = (GameEvents) arg;
        KeyEvent e = (KeyEvent) ge.event;
        //Left
        if(e.getKeyCode() == left){
            if(e.getID() == KeyEvent.KEY_RELEASED){
                this.LeftPressed  = false;
            } else if (e.getID() == KeyEvent.KEY_PRESSED){
                this.LeftPressed = true;
            }
        }

        //Right
        if(e.getKeyCode() == right){
            if(e.getID() == KeyEvent.KEY_RELEASED){
                this.RightPressed = false;
            }else if (e.getID() == KeyEvent.KEY_PRESSED){
                this.RightPressed = true;
            }
        }
    }
}
