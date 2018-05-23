package RainbowReef;
import java.awt.Image;
import java.math.*;

public class Star extends CollidableObj{
    private double VerticalSpeed;
    private double HorizontalSpeed;
    //use level difficulty to adjust max speed of pop
    private int difficulty;
    //angle is used purely for rotating the image; horizontal+vertical speed
    //control direction of the star.
    private double angle;
    private int LeftBounds, RightBounds, UpperBounds;
    
    public Star(Image img, int x, int y, int level, int leftbounds, int rightbounds, int upperbounds)
    {
        super(img, x, y);
        VerticalSpeed = 0;
        HorizontalSpeed = 0;
        this.difficulty = level;
        this.angle = 0;
        this.LeftBounds = leftbounds;
        this.RightBounds = rightbounds;
        this.UpperBounds = upperbounds;
    }
    
    public double getVertSpeed()
    {
        return this.VerticalSpeed;
    }
    
    public double getHoriSpeed()
    {
        return this.HorizontalSpeed;
    }
    
    public double getAngle()
    {
        return angle;
    }
    
    public void update(boolean north, boolean east, boolean south, boolean west)
    {
        //reverse direction for collisions
        if (north)
        {
            VerticalSpeed *= -1;
        }
        if (south)
        {
            VerticalSpeed *= -1;
        }
        if (east)
        {
            HorizontalSpeed *= -1;
        }
        if (west)
        {
            HorizontalSpeed *= -1;
        }
        //if all edges are true we hit a corner, need to reverse both speeds
        if(south && north && east && west)
        {
            HorizontalSpeed *= -1;
            VerticalSpeed *= -1;
        }
        //cheat and use boundaries instead of testing for collisions with more walls
        if(this.x < this.LeftBounds)
            HorizontalSpeed *= -1;
        if(this.x + this.width > this.RightBounds)
            HorizontalSpeed *= -1;
        if(this.y < this.UpperBounds)
            VerticalSpeed *= -1;
        
        //Gravity; slightly increase fall speed
        VerticalSpeed += .1;
        
        x += (int)Math.round(HorizontalSpeed);
        y += (int)Math.round(VerticalSpeed);
        
        //adjust angle based on direction of star
        if(HorizontalSpeed < 0)
        {
            this.angle -= 3;
        }
        else
        {
            this.angle += 3;
        }
    }
    
    //HitShellLeft/Right are for collsions on side of shell, not on top
    public void hitShellLeft()
    {
        this.HorizontalSpeed = -10;
    }
    
    public void hitShellRight()
    {
        this.HorizontalSpeed = 10;
    }
    
    public void hitShellTop(double adjust)
    {
        this.VerticalSpeed =  -Math.abs(VerticalSpeed);
        //slightly increase vertical speed for each hit
        this.VerticalSpeed -= .5;
        //adjust horizontalspeed based on where the hit on the shell was
        this.HorizontalSpeed = .085 * adjust;
        
        //put a cap on the max speed of star based on current level
        //hard cap should be 20, so star cant miss collisions and go through objects
        if(this.VerticalSpeed > 7 + this.difficulty*2)
        {
            this.VerticalSpeed = 7 + this.difficulty*2;
        }
        if(this.HorizontalSpeed > 4 + this.difficulty*2)
        {
            this.HorizontalSpeed = 4 + this.difficulty*2;
        }
        if(this.VerticalSpeed < -(7 + this.difficulty*2))
        {
            this.VerticalSpeed = -(7 + this.difficulty*2);
        }
    }
}
