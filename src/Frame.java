import java.awt.Dimension;
import javax.swing.JFrame;
 
public class Frame extends JFrame
{
    static int x = 1200;
    static int y = 650;
   
    public Frame(String title)
    {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension size = new Dimension(x+7,y+30);
        setSize(size);
        setResizable(false);
        setAlwaysOnTop(true);
    }
   
    public static void main (String[] args){
        Frame frame = new Frame("Wizard Duel");
        Game game = new Game(new Dimension(x,y));            
        frame.add(game);
       
        frame.setLocationRelativeTo(null);
       
        frame.setVisible(true);
    }
}