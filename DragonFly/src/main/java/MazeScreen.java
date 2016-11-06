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
import java.awt.image.WritableRaster;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author krishna
 */
public class MazeScreen extends JPanel{

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
    boolean l, r, u, d;
    
    int map[][] = new int[32][32];
    int startX = 30, startY = 29;
    
    String path = "maze.png";
    String ansPath = "maze_ans.png";
    
    public MazeScreen()
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
        readMap(path);
    }
    
    //get boundary box
    
    public void update() throws Exception
    {
        if(solveMaze(startX, startY))
        {
            JOptionPane.showMessageDialog(null, "PERSON RESCUED!");
        }else
        {
            JOptionPane.showMessageDialog(null, "FAILED!");
        }
        
        //mouse.updateLocation();
        
        /*if(l)
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
*/
        
    }
    
    public void render()
    {
        drawMap();
    }
    
    public boolean solveMaze(int x, int y) throws Exception
    {
        //mouse.absx = x;
        //mouse.absy = y;
        //render();
        if(map[y][x] == 1)
        {
            isRunning = false;
            return false;
        }
        if(map[y][x] == 2)
        {
            isRunning = false;
            System.out.println();
            printMap();
            drawToImage();
            return true;
        }
        if(map[y][x] == 5)
        {
            return false;
        }
        map[y][x] = 5;
        //Thread.sleep(10);
        if(solveMaze(x + 1, y))
        {
            return true;
        }
        if(solveMaze(x - 1, y))
        {
            return true;
        }
        if(solveMaze(x, y + 1))
        {
            return true;
        }
        if(solveMaze(x, y - 1))
        {
            return true;
        }
        map[y][x] = 0;
        return false;
    }
    
    public void drawToImage()
    {
        BufferedImage ans = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        WritableRaster wr = ans.getRaster();
        for(int y = 0; y < 32; y ++)
        {
            for(int x = 0; x < 32; x ++)
            {
                if(map[y][x] == 1)
                {
                    wr.setPixel(x, y, new int[]{0, 0, 0});
                }
                if(map[y][x] == 2)
                {
                    wr.setPixel(x, y, new int[]{255, 0, 0});
                }
                if(map[y][x] == 0)
                {
                    wr.setPixel(x, y, new int[]{0, 180, 0});
                }
                if(map[y][x] == 5)
                {
                    wr.setPixel(x, y, new int[]{255, 255, 255});
                }
                
            }
        }
        
        try{
            File outputfile = new File(("src/obstaclecourse/" + ansPath));
            ImageIO.write(ans, "png", outputfile);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void drawToScreen()
    {
        Graphics g2 = this.getGraphics();
        if(image == null)
        {
            System.out.println("null");
        }
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
                Color c = new Color(bg.getRGB(j, i));
                if(c.equals(Color.BLACK))
                {
                    map[i][j] = 1;
                }else if(c.equals(Color.RED))
                {
                    map[i][j] = 2;
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
        //System.out.println("Drawing");
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        for(int y = 0; y < 32; y++)
        {
            for(int x = 0; x < 32; x ++)
            {
                if(map[y][x] == 1)
                {
                    g.setColor(Color.BLACK);
                }else if(map[y][x] == 2)
                {
                    g.setColor(new Color(255, 0, 0));
                }else if(map[y][x] == -1)
                {
                    g.setColor(new Color(255, 255, 0));
                }else
                {
                    g.setColor(new Color(0, 180, 0));
                }
                
                g.fillRect(x * 4, y * 4, 4, 4);
            }
        }
    }
    
   
}
