/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obstaclecourse;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author krishna
 */
public class Mouse {
    
    public double x, y;
    public double sx, sy;
    public double vx, vy;
    int size = 4;
    int col = 0;
    int add = 1;
    
    public Mouse(double X, double Y, double sX, double sY){
        x = X;
        y = Y;
        sx = sX;
        sy = sY;
        
        
    }
    
    public void drawSelf(Graphics2D g)
    {
        double ang = getAngle(vx, vy);
        int cx = (int)(x + size * Math.cos(ang));
        int cy = (int)(y + size * Math.sin(ang));
        g.setColor(new Color(col, col, 255 - col));
        g.drawLine((int)(x), (int)(y), cx, cy);
        updateCol();
        // System.out.println("x: " + cx + "y" + cy);
    }
       
    public double getAngle(double vX, double vY)
    {
        double mag = Math.sqrt(vx * vx + vy * vy);
        
        double ang = Math.asin(vy / mag);
        if(mag < 0.001 & mag > -0.001)
        {
            ang = 0;
        }
        
        
        
        if(vx < 0)
        {
            ang = Math.PI - ang;
        }
        return ang;
    }
    
    public void updateCol()
    {
        col += add;
        if(col > 250)
        {
            add = -2;
        }
        if(col < 5)
        {
            add = 2;
        }
    }
            
    public void setVelocity(double vX, double vY)
    {
        vx = vX;
        vy = vY;
    }
    
    public void updateLocation()
    {
        x += vx;
        y += vy;
    }
}
