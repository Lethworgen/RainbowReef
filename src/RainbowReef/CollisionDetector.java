package RainbowReef;

import java.awt.Rectangle;

public class CollisionDetector {
    
    public boolean ShellCol(Star star, Shell shell)
    {
        Rectangle StarRec = new Rectangle(star.getX(), star.getY(), star.getWidth(), star.getHeight());
        Rectangle ShellRec = new Rectangle(shell.getX(), shell.getY(), shell.getWidth(), shell.getHeight());
        
        if(StarRec.intersects(ShellRec))
            return true;
        else
            return false;
    }
    
    //make rectangles around the star on all 4 sides to detect where collision occurs
    //Return an int val to find the side w/ the biggest collsion
    public int SouthCol(Star star, CollidableObj obj)
    {
        Rectangle StarRec = new Rectangle(star.getX()+ 2 + (int)Math.round(star.getHoriSpeed()), 
                star.getY() + star.getHeight() - 4 + (int)Math.round(star.getVertSpeed()),
                star.getWidth() - 4, 2);
        Rectangle ObjRec = new Rectangle(obj.getX(), obj.getY(), obj.getWidth(), obj.getHeight());
        
        if(!StarRec.intersects(ObjRec))
            return 0;
        else if((star.getX() >= obj.getX()) && (star.getWidth()+star.getX() <= obj.getX() + obj.getWidth()))
            return star.getWidth();
        else if(star.getX() < obj.getX())
            return star.getWidth() - (obj.getX() - star.getX());
        else
            return obj.getWidth() - (star.getX() - obj.getX());
    }
    
    
    public int NorthCol(Star star, CollidableObj obj)
    {
        Rectangle StarRec = new Rectangle(star.getX()+ 2 + (int)Math.round(star.getHoriSpeed()), 
                star.getY() + (int)Math.round(star.getVertSpeed()),
                star.getWidth() - 4, 2);
        Rectangle ObjRec = new Rectangle(obj.getX(), obj.getY(), obj.getWidth(), obj.getHeight());
        
        if(!StarRec.intersects(ObjRec))
            return 0;
        else if((star.getX() >= obj.getX()) && (star.getWidth()+star.getX() <= obj.getX() + obj.getWidth()))
            return star.getWidth();
        else if(star.getX() < obj.getX())
            return star.getWidth() - (obj.getX() - star.getX());
        else
            return obj.getWidth() - (star.getX() - obj.getX());
    }
    
    public int WestCol(Star star, CollidableObj obj)
    {
        Rectangle StarRec = new Rectangle(star.getX() + (int)Math.round(star.getHoriSpeed()), 
                star.getY()+ 2 + (int)Math.round(star.getVertSpeed()),
                2, star.getHeight() - 4);
        Rectangle ObjRec = new Rectangle(obj.getX(), obj.getY(), obj.getWidth(), obj.getHeight());
        
        if(!StarRec.intersects(ObjRec))
            return 0;
        else if((star.getY() >= obj.getY()) && (star.getHeight()+star.getY() <= obj.getY() + obj.getHeight()))
            return star.getHeight();
        else if(star.getY() < obj.getY())
            return star.getHeight() - (obj.getY() - star.getY());
        else
            return obj.getHeight() - (star.getY() - obj.getY());
    }
    
    public int EastCol(Star star, CollidableObj obj)
    {
        Rectangle StarRec = new Rectangle(star.getX() + star.getWidth() -2 + (int)Math.round(star.getHoriSpeed()), 
                star.getY()+ 2 + (int)Math.round(star.getVertSpeed()),
                2, star.getHeight() - 4);
        Rectangle ObjRec = new Rectangle(obj.getX(), obj.getY(), obj.getWidth(), obj.getHeight());
        
        if(!StarRec.intersects(ObjRec))
            return 0;
        else if((star.getY() >= obj.getY()) && (star.getHeight()+star.getY() <= obj.getY() + obj.getHeight()))
            return star.getHeight();
        else if(star.getY() < obj.getY())
            return star.getHeight() - (obj.getY() - star.getY());
        else
            return obj.getHeight() - (star.getY() - obj.getY());
    }
}
