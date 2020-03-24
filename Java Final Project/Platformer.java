/*
   Author:Rishi
   Purpose:This class starts up the program and creates all the objects needed for the GUI.
*/

import javax.swing.*;
import java.awt.*;

public class Platformer{
    public static void main(String[] args){
      
  
      JFrame arcade = new JFrame();
      arcade.setTitle("Just Jump");
      arcade.setResizable(false);
      arcade.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   
      JPanel panel = new PlatformerUserPanel(800, 450);
       
      Container pane = arcade.getContentPane();
      pane.setLayout(new GridLayout(1, 1));
      panel.setBackground(new Color(221,238,255));
       
       
      pane.add(panel); 

      
      
      panel.requestFocus();
      arcade.pack();
      arcade.setVisible(true);
   }
}