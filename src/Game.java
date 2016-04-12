 
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;
 
public class Game extends JPanel implements ActionListener, KeyListener
{
   
    Timer t = new Timer(5,this);
   
    public long p1Last, p2Last, p1Next, p2Next, startTime;
   
    boolean isPlaying = false, isWinner = false, fire = false, cast = false,
            p1Up = false, p2Up = false, p1Down = false, p2Down = false,
            p1Left = false, p2Left = false, p1Right = false, p2Right = false;
    
    String winner;
   
    Rectangle2D player1, player2, p1Health, p2Health;
    public double x1 = 50, y1 = 300, x2 = 1120, y2 = 300, velx1=2, vely1=2, velx2=2, vely2=2;
   
    int p1Ammo = 50, p2Ammo = 50, player1HP = 300, player2HP = 300;
    ArrayList<Fireball> fireballs = new ArrayList<Fireball>();
    ArrayList<Waterbolt> waterbolts = new ArrayList<Waterbolt>();
    public JButton start, playAgain, quit;
   
    public Game (Dimension size)
    {
        setPreferredSize(size);
        setBackground(Color.BLACK);
        t.addActionListener(this);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        start = new JButton("Start");
        start.setSize(100,30);
        start.addActionListener(this);
        add(start, BorderLayout.CENTER);
        playAgain = new JButton("Play Again");
        playAgain.setSize(100, 30);
        playAgain.addActionListener(this);
        playAgain.setLocation(550,350);
        quit = new JButton("Quit");
        quit.setSize(100,30);
        quit.addActionListener(this);
        quit.setLocation(550,400);
        p1Last = System.currentTimeMillis();
        p2Last = System.currentTimeMillis();
    }
   
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        checkHealth();
        if(isWinner)
        {
            Font font = new Font("TimesRoman", Font.PLAIN, 56);
            
            g2.setColor(Color.WHITE);
            g2.setFont(font);
           
            String whoWon = winner + " wins.";
            g2.drawString(whoWon, this.getWidth()/2-(g.getFontMetrics(font).stringWidth(whoWon)/2), this.getHeight()/2);
            
            add(playAgain);
            add(quit);
        }
        else
        {
            displayTimer(g2,g);
            displayHealth(g2);
            checkIntersection();
            makeBoundaries(g2);
            makePlayers(g2);
            showAttacks(g2);
            t.start();
        }
    }
    
    public void displayTimer(Graphics2D g2, Graphics g)
    {
        String currentTime;
        if(isPlaying)
        {
            currentTime = String.valueOf((System.currentTimeMillis() - startTime) / 1000);
        }
        else
        {
            currentTime = "0";
        }
            Font font = new Font("TimesRoman", Font.PLAIN, 32);
            g2.setColor(Color.WHITE);
            g2.setFont(font);
            g2.drawString(currentTime, 600-(g.getFontMetrics(font).stringWidth(currentTime)/2), 630);
        
    }
   
    public void checkHealth()
    {
        if(player1HP <= 0)
        {
            winner = "Blue";
            isWinner = true;
        }
        if(player2HP <= 0)
        {
            winner = "Red";
            isWinner = true;
        }
    }
   
    public void displayHealth(Graphics2D g2)
    {
        g2.setColor(Color.RED);
        p1Health = new Rectangle2D.Double(10, 610, player1HP, 30);
        g2.fill(p1Health);
        g2.setColor(Color.BLUE);
        p2Health = new Rectangle2D.Double(1190 - player2HP,  610, player2HP, 30);
        g2.fill(p2Health);
    }
   
    public void checkIntersection()
    {
        for(int i = 0; i < fireballs.size(); i++)
        {
            if(player2.intersects(fireballs.get(i).getFrame()) || fireballs.get(i).getFrame().intersects(player2))
            {
                player2HP = player2HP - 5;
            }
        }
        for(int q = 0; q < waterbolts. size(); q++)
        {
            if(player1.getBounds2D().intersects(waterbolts.get(q).getFrame()) || waterbolts.get(q).getFrame().intersects(player1.getBounds2D()))
            {
                player1HP = player1HP - 5;
            }
        }
    }
   
    public void makePlayers(Graphics2D g2)
    {
        player1 = new Rectangle2D.Double(x1,y1,30,30);
        player2 = new Rectangle2D.Double(x2,y2,30,30);
       
        g2.setColor(Color.RED);
        g2.fill(player1);
        g2.setColor(Color.BLUE);
        g2.fill(player2);
    }
   
    public void makeBoundaries(Graphics2D g2)
    {
        g2.setColor(Color.white);
        g2.drawLine(600,0,600,600);
        g2.drawLine(0,600,1200,600);
        g2.drawLine(1200,0,1200,600);
        g2.drawLine(0,0,0,600);
        g2.drawLine(0,0,1200,0);
    }
   
    public void doLogic()
    {
        if(p1Right && x1 + velx1 < 570)
        {
            x1 = x1 + velx1;
        }
        if(p1Left && x1 - velx1 > 0)
        {
            x1 = x1 - velx1;
        }
        
        if(p1Down && y1 + vely1 < 570)
        {
            y1 = y1 + vely1;
        }
        if(p1Up && y1 - vely1 > 0)
        {
            y1 = y1 - vely1;
        }
        
        if(p2Right && x2 + velx2 < 1170)
        {
            x2 = x2 + velx2;
        }
        if(p2Left && x2 - velx2 > 600)
        {
            x2 = x2 - velx2;
        }
        
        if(p2Down && y2 + vely2 < 570)
        {
            y2 = y2 + vely2;
        }
        if(p2Up && y2 - vely2 > 0)
        {
            y2 = y2 - vely2;
        }
    }
   
    public void showAttacks(Graphics2D g2)
    {
        for(int i = 0; i < fireballs.size(); i++)
        {
            Fireball f = (Fireball) fireballs.get(i);
            if(f.getVisible())
            {
                g2.setColor(Color.RED);
                g2.fill(f);
                f.moveFireball();
            }
        }
        for(int q = 0; q < waterbolts.size(); q++)
        {
            Waterbolt w = (Waterbolt) waterbolts.get(q);
            if(w.getVisible())
            {
                g2.setColor(Color.BLUE);
                g2.fill(w);
                w.moveWaterbolt();
            }
        }
    }            
   
    public void fire()
    {
        p1Next = System.currentTimeMillis();
        if(fire && (p1Next - p1Last >= 300))
        {
            Fireball newFireball = new Fireball(player1.getX()+30, player1.getY());
            fireballs.add(newFireball);
            p1Last = p1Next;
        }
    }
   
    public void cast()
    {
        p2Next = System.currentTimeMillis();
        if(cast && (p2Next - p2Last >= 300))
        {
            Waterbolt newWaterbolt = new Waterbolt(player2.getX()-20, player2.getY());
            waterbolts.add(newWaterbolt);
            p2Last = p2Next;
        }
    }
   
    public void restartGame()
    {
        System.out.println("Restarting Game");
        remove(playAgain);
        remove(quit);
        add(start);
        
        x1 = 50;
        y1 = 300;
        x2 = 1120;
        y2 = 300;
//        velx1=0;
//        vely1=0;
//        velx2=0;
//        vely2=0;
        
        player1HP = 300;
        player2HP = 300;
        
        isPlaying = false;
        isWinner = false;
        
        fireballs.clear();
        waterbolts.clear();
    }
    
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        if(ae.getSource() == start)
        {
            this.remove(start);
            isPlaying = true; 
            startTime = System.currentTimeMillis();
        }
        if(isPlaying)
        {
            repaint();
            doLogic();
           
            fire();
            cast();
        }
        if(ae.getSource() == playAgain)
        {
            restartGame();
        }
        if(ae.getSource() == quit)
        {
            System.exit(0);
        }
        
    }
   
    public void moveP1(KeyEvent e)
    {
        int input = e.getKeyCode();
        if(input == KeyEvent.VK_D)
        {
            //velx1 = 2;
            p1Right =  true;
        }
        if(input == KeyEvent.VK_W)
        {
            //vely1 = -2;
            p1Up = true;
        }
        if(input == KeyEvent.VK_S)
        {
            //vely1 = 2;
            p1Down = true;
        }
        if(input == KeyEvent.VK_A)
        {
            //velx1 = -2;
            p1Left = true;
        }
        if(input == KeyEvent.VK_SPACE)
        {
            fire = true;
        }
    }
   
    public void moveP2(KeyEvent e)
    {
        int input = e.getKeyCode();
        if(input == KeyEvent.VK_RIGHT)
        {
            //velx2 = 2;
            p2Right = true;
        }
        if(input == KeyEvent.VK_UP)
        {
            //vely2 = -2;
            p2Up = true;
        }
        if(input == KeyEvent.VK_DOWN)
        {
            //vely2 = 2;
            p2Down = true;
        }
        if(input == KeyEvent.VK_LEFT)
        {
            //velx2 = -2;
            p2Left = true;
        }
        if(input == KeyEvent.VK_SHIFT)
        {
            cast = true;
        }
    }
   
    public void stopP1(KeyEvent e)
    {
        int input = e.getKeyCode();
        if(input == KeyEvent.VK_D)
        {
            //velx1 = 0;
            p1Right = false;
        }
        if(input == KeyEvent.VK_W)
        {
            //vely1 = 0;
            p1Up = false;
        }
        if(input == KeyEvent.VK_S)
        {
            //vely1 = 0;
            p1Down = false;
        }
        if(input == KeyEvent.VK_A)
        {
            //velx1 = 0;
            p1Left = false;
        }
        if(input == KeyEvent.VK_SPACE)
        {
            fire = false;
        }
    }
   
    public void stopP2(KeyEvent e)
    {
        int input = e.getKeyCode();        
        if(input == KeyEvent.VK_RIGHT)
        {
            //velx2 = 0;
            p2Right = false;
        }
        if(input == KeyEvent.VK_UP)
        {
            //vely2 = 0;
            p2Up = false;
        }
        if(input == KeyEvent.VK_DOWN)
        {
            //vely2 = 0;
            p2Down = false;
        }
        if(input == KeyEvent.VK_LEFT)
        {
            //velx2 = 0;
            p2Left = false;
        }
        if(input == KeyEvent.VK_SHIFT)
        {
            cast = false;
        }
    }
   
    @Override
    public void keyPressed(KeyEvent ke)
    {
        moveP1(ke);
        moveP2(ke);
    }
 
    @Override
    public void keyReleased(KeyEvent ke)
    {    
        stopP1(ke);
        stopP2(ke);
    }
   
    @Override
    public void keyTyped(KeyEvent ke)
    {
       
    }
}
