/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obstaclecourse;

import javax.swing.JFrame;

/**
 *
 * @author krishna
 */
public class ObstacleCourse {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        
        JFrame window = new JFrame("AI DEMO");
        MazeScreen ms = new MazeScreen();
        try{
            ms.update();
        }catch(Exception e)
        {
            
        }
        window.setContentPane(new Display("maze.png"));
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        
        
        
    }
    
}
