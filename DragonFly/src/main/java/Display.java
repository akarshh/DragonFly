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
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import static jdk.nashorn.internal.objects.NativeArray.map;

/**
 *
 * @author krishna
 */
public class Display extends JPanel implements Runnable{

    //related to dimensions
    public static final int WIDTH = 512 * 2;
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
    BufferedImage i1, i2;
    BufferedImage bg;
    Graphics2D g1, g2;
    Graphics g;
    
    //Actual logic
    int map[][] = new int[32][32];
    
    String path;
    
    public Display(String p)
    {
        setPreferredSize(new Dimension((int)(WIDTH * SCALE), (int)(HEIGHT * SCALE)));
        setFocusable(true);
        setVisible(true);
        requestFocus();
        
        init();
        path = p;
        
    }
    
    
    public void init()
    {
        try{
            i1 = ImageIO.read(getClass().getResourceAsStream("maze.png"));
            i2 = ImageIO.read(getClass().getResourceAsStream("maze_ans.png"));
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
    //get boundary box
    
    
    public void render()
    {
        
    }
    
    
    public void drawToScreen()
    {
        Graphics g2 = this.getGraphics();
        g2.drawImage(i1, 0, 0, (int)(WIDTH * SCALE / 2), (int)(HEIGHT * SCALE), this);
        g2.drawImage(i2, (int)(WIDTH * SCALE / 2), 0, (int)(WIDTH * SCALE / 2), (int)(HEIGHT * SCALE), this);
        g2.dispose();
    }
    
    @Override
    public void addNotify()
    {
        super.addNotify();
        if(thread == null){
            thread = new Thread(this);
            isRunning = true;
            thread.start();
        }
    }
    
    @Override
    public void run()
    {
        try
        {
            //render();
            //drawToScreen();
            //update();
                    
            while(isRunning)
            {
                currTime = System.currentTimeMillis();
                
                if(!paused)
                {
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
    
   
}

