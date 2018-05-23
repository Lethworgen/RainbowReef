package RainbowReef;
import java.awt.Image;


public class Brick extends CollidableObj{
    private int points;
    private int type;
    
    public Brick(Image img, int x, int y, int type, int score)
    {
        super(img, x, y);
        this.points = score;
        this.type = type;
    }
    
    public int getPoints()
    {
        return points;
    }
    
    //0 = Wall
    //1 = breakable brick
    //2 = double stars
    //3 = extra life
    //4 = Octo
    //5 = big octo
    public int getType()
    {
        return type;
    }
}
