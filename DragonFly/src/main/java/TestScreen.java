/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obstaclecourse;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author krishna
 */
public class TestScreen extends JPanel implements Runnable, KeyListener{

    //related to dimensions
    public static final int WIDTH = 512;
    public static final int HEIGHT = 512;
    public static final double SCALE = 1.5;
    
    //related to thread
    Thread thread;
    public static final int FPS = 25;
    public static final int REFRESH_TIME = 1000 / FPS;
    public boolean isRunning = false;
    private long currTime = 0;
    private long diff;
    boolean paused = false;
    
    //related to graphics
    BufferedImage image;
    BufferedImage bg;
    Graphics2D g;
    
    //Actual logic
    Mouse mouse;
    boolean l, r, u, d;
    
    int map[][] = new int[32][32];
    
    public TestScreen()
    {
        setPreferredSize(new Dimension((int)(WIDTH * SCALE), (int)(HEIGHT * SCALE)));
        setFocusable(true);
        setVisible(true);
        requestFocus();
        
        init();
        
    }
    
    
    public void init()
    {
        
        image = new BufferedImage(128, 128, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D)image.getGraphics();
        readMap("maze.png");
        mouse = new Mouse(120, 120, 1, 1);
        mouse.setVelocity(0, 0);
    }
    
    
    public void update()
    {
        mouse.updateLocation();
        
        if(l)
        {
            mouse.vx -= 0.01;
        }
        if(r)
        {
            mouse.vx += 0.01;
        }
        if(u)
        {
            mouse.vy -= 0.01;
        }
        if(d)
        {
            mouse.vy += 0.01;
        }
        
    }
    
    public void render()
    {
        drawMap();
        mouse.drawSelf(g);
        
    }
    
    public void drawToScreen()
    {
        Graphics g2 = this.getGraphics();
        g2.drawImage(image, 0, 0, (int)(WIDTH * SCALE), (int)(HEIGHT * SCALE), this);
        g2.dispose();
    }
    
    public void readMap(String url)
    {
        // how to screw yourself
        try
        {
            bg = ImageIO.read(getClass().getResourceAsStream(url));
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        
        for(int i = 0; i < 32; i ++)
        {
            for(int j = 0; j < 32; j ++)
            {
                Color c = new Color(bg.getRGB(i, j));
                if(c.equals(Color.BLACK))
                {
                    map[i][j] = 1;
                }
            }
        }
        printMap();
    }
    
    public void printMap()
    {
        for(int i = 0; i < 32; i ++)
        {
            for(int j = 0; j < 32; j ++)
            {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }
    
    public void drawMap()
    {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        for(int i = 0; i < 32; i ++)
        {
            for(int j = 0; j < 32; j ++)
            {
                if(map[i][j] == 1)
                {
                    g.setColor(Color.BLACK);
                }else if(map[i][j] == 0)
                {
                    g.setColor(new Color(0, 255, 0));
                }
                g.fillRect(i * 4, j * 4, 4, 4);
            }
        }
    }
    
    @Override
    public void addNotify()
    {
        super.addNotify();
        if(thread == null){
            thread = new Thread(this);
            isRunning = true;
            addKeyListener(this);
            thread.start();
        }
    }
    
    @Override
    public void run()
    {
        try
        {
            while(isRunning)
            {
                currTime = System.currentTimeMillis();
                
                if(!paused)
                {
                    update();
                    render();
                    drawToScreen();
                }
                
                diff = System.currentTimeMillis() - currTime;
                if(diff < REFRESH_TIME)
                {
                    Thread.sleep(REFRESH_TIME - diff);
                }
                
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
        
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        if(e.getKeyCode() == KeyEvent.VK_P)
        {
            paused = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            l = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            r = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_UP)
        {
            u = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN)
        {
            d = true;
        }
        
        
    }
 
    @Override
    public void keyReleased(KeyEvent e) {
        
        if(e.getKeyCode() == KeyEvent.VK_P)
        {
            paused = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            l = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            r = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_UP)
        {
            u = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN)
        {
            d = false;
        }
    }
    
}
