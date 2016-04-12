
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
 
public class Waterbolt extends Ellipse2D
{    
    public double  x, y, width = 20, height = 20;
    Rectangle2D waterFrame;
    boolean visible;
   
    public Waterbolt(double startX, double startY)
    {
        x = startX;
        y = startY;
        Ellipse2D waterbolt = new Ellipse2D.Double(x, y, width, height);
        visible = true;
    }
   
    public Rectangle2D getFrame()
    {
        waterFrame = new Rectangle2D.Double(x, y, width, height);
        return waterFrame;
    }
   
    public boolean getVisible()
    {
        return visible;
    }
   
    public void moveWaterbolt()
    {
        if(x > -25)
        {
            x = x - 9;
        }
        else
        {
            visible = false;
        }
    }
   
    @Override
    public double getX() {
        return x;
    }
 
    @Override
    public double getY() {
        return y;
    }
 
    @Override
    public double getWidth() {
        return width;
    }
 
    @Override
    public double getHeight() {
        return height;
    }
 
    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
 
    @Override
    public void setFrame(double d, double d1, double d2, double d3) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
 
    @Override
    public Rectangle2D getBounds2D() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
   
   
}