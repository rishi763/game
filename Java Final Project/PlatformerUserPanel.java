/*
   Author:Rishi
   Purpose:This class implements the GUI and unites the player, block,  and platformer classes to create the screen 
*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.*;

public class PlatformerUserPanel extends JPanel implements KeyListener,ActionListener
{
   private int level,mode;
   public static int gameX;
   private BufferedImage imgBack,imgTitle;
   private Color[] skins={Color.blue,Color.magenta,Color.lightGray,Color.gray,Color.darkGray,Color.black,Color.white,Color.pink,Color.red,Color.orange,Color.yellow,Color.green,Color.cyan};
   private int skinIndex;
   private Player player;
   private Block[][] blocks={{new Block(0,440,2000,10,0),new Block(350,150,50,300,0),new Block(150,400,50,50,0),new Block(0,250,50,200,0),new Block(350,0,50,100,0),new Block(600,0,50,400,0),new Block(400,300,80,50,1),new Block(520,300,80,50,1),new Block(650,120,50,50,0),new Block(650,280,50,50,0),new Block(800,120,50,320,0),new Block(800,0,50,50,1),new Block(1000,200,100,50,0),new Block(850,400,1050,40,1),new Block(1400,100,100,50,0),new Block(1900,400,100,40,2)},{new Block(400,300,50,140,0),new Block(0,440,2000,10,0),new Block(0,250,50,190,0),new Block(200,90,100,50,0),new Block(600,90,100,50,0),new Block(450,400,1550,40,1)}};
   private Block block,block1;
   private javax.swing.Timer timer;
         
    
   public PlatformerUserPanel (int width, int height) {//constructor
      timer = new javax.swing.Timer(16, new FrameUpdater());
      timer.setRepeats(true);
      timer.start();
      level=0;
      mode=0;
      try{//reads images
         imgBack=ImageIO.read(new File("background.png"));
         imgTitle=ImageIO.read(new File("title.png"));
         }
      catch(IOException e){
         e.printStackTrace();
      }
      skinIndex=0;
      addMouseListener(new PanelListener());
      addMouseMotionListener(new PanelMotionListener());
      
    	
    
   
      addKeyListener(this);
       
      setFocusable(true);
      setFocusTraversalKeysEnabled(false);      
      setPreferredSize(new Dimension(width, height));
   
      
   }
   public static void setGameX(int a){//finds the gameX to draw level screen
      gameX=a;
   }
   public void actionPerformed (ActionEvent e){//needed for implementation
      
   }
   private void setMode(int m){//sets type of screen being used
      mode=m;
      if(mode==1){
         player=new Player(level,skins[skinIndex]);
      }
   }
   public void keyTyped(KeyEvent e) {//needed for KeyListener
   }
   public void keyReleased(KeyEvent e) {//used for movement   
      if(mode==1){
         switch(e.getKeyCode()){
            case KeyEvent.VK_UP:
               player.setMoveUp(false);
               break;
            case KeyEvent.VK_RIGHT:
               player.setMoveRight(false);
               break;
            case KeyEvent.VK_LEFT:
               player.setMoveLeft(false);
               break;
         }
      }   
   }  
   public void keyPressed(KeyEvent e){//used for movement 
      if(mode==1){
         switch(e.getKeyCode()){
            case KeyEvent.VK_UP:
               player.setMoveUp(true);
               break;
            case KeyEvent.VK_RIGHT:
               player.setMoveRight(true);
               break;
            case KeyEvent.VK_LEFT:
               player.setMoveLeft(true);
               break;
            case KeyEvent.VK_R:
               player=new Player(level,skins[skinIndex]);
               break;
            case KeyEvent.VK_ESCAPE:
               setMode(0);
               break;
         }
      }
      if(mode==2){
         switch(e.getKeyCode()){
            case KeyEvent.VK_RIGHT:
               skinIndex=(skinIndex+1)%13;
               break;
            case KeyEvent.VK_LEFT:
               if(skinIndex>0)
                  skinIndex=(skinIndex-1)%13;
               else
                  skinIndex=12;
               break;
         }
      }
      
   }
   public void paintComponent(Graphics g){//draws everything needed for screen
      
      super.paintComponent(g);
      if(mode==0){
         g.drawImage(imgBack,0,0,getWidth(),getHeight(),this);
         g.drawImage(imgTitle,200,-10,400,125,this);
         g.setColor(Color.blue);
         g.fillRect(325,200,150,50);
         g.setColor(new Color(105,178,252));
         g.fillRect(335,210,130,30);
         g.setColor(Color.black);
         g.setFont(new Font("sansserif", Font.BOLD, 24));
         g.drawString("Start Game", 336, 233);
         g.setColor(Color.blue);
         g.fillRect(315,275,170,50);
         g.setColor(new Color(105,178,252));
         g.fillRect(325,285,150,30);
         g.setColor(Color.black);
         g.drawString("Change Skin", 328, 308);
         g.setColor(Color.blue);
         g.fillRect(315,350,170,50);
         g.setColor(new Color(105,178,252));
         g.fillRect(325,360,150,30);
         g.setColor(Color.black);
         g.drawString("How To Play", 328, 383);
      }
      else if(mode==1){
         updateFrame();
         player.draw(g);
         for(Block block:blocks[level]){
            block.draw(g);
         }
         g.setColor(Color.black);
         g.setFont(new Font("sansserif", Font.BOLD, 48));
         g.drawString("Level "+(level+1),50-gameX,50);
      }
      else if(mode==2){
         g.setColor(skins[skinIndex]);
         g.fillRoundRect(350,100,95,95,25,25);
         g.setColor(Color.black);
         g.setFont(new Font("sansserif", Font.PLAIN, 18));
         g.drawString("Press Right and Left to change colors",250,250);
         g.setColor(Color.blue);
         g.fillRect(340,275,120,50);
         g.setColor(new Color(105,178,252));
         g.fillRect(350,285,100,30);
         g.setColor(Color.black);
         g.setFont(new Font("sansserif", Font.BOLD, 24));
         g.drawString("Go Back", 351, 308);
      }
      else if(mode==3){
         g.setColor(Color.black);
         g.setFont(new Font("sansserif", Font.PLAIN, 48));
         g.drawString("How to Play",300,100);
         g.setFont(new Font("sansserif", Font.PLAIN, 18));
         g.drawString("In this game your goal will be to avoid all red objects",200,150);
         g.drawString("and navigate around the level to reach the green block.",200,170);
         g.drawString("You will use the arrow keys to move around and",200,190);
         g.drawString("the black blocks to stay on. Good luck and have fun.",200,210);
         g.setColor(Color.blue);
         g.fillRect(340,275,120,50);
         g.setColor(new Color(105,178,252));
         g.fillRect(350,285,100,30);
         g.setColor(Color.black);
         g.setFont(new Font("sansserif", Font.BOLD, 24));
         g.drawString("Go Back", 351, 308);
      }
      
   } 
	 

    
   private class FrameUpdater implements ActionListener{//updates frame and repaints
      public void actionPerformed (ActionEvent e){ 
         repaint();
      }
   }
   public void updateFrame(){//updates frame by changing location and checks for death or completion
      player.updateVelocity();
      player.move();
      if(player.levelCompleted()){
         level++;
         player=new Player(level,skins[skinIndex]);
      }
      if(player.isDead()){
         player=new Player(level,skins[skinIndex]);
      }
   }  
   private class PanelListener extends MouseAdapter//checks for clicks for menu
   {
      public void mousePressed(MouseEvent e)
      {
       if(mode==0){
         if(e.getX()>=325&&e.getX()<=475&&e.getY()>=200&&e.getY()<=250){
            setMode(1);
         }
         if(e.getX()>=315&&e.getX()<=485&e.getY()>=275&&e.getY()<=325){
            setMode(2);
         }
         if(e.getX()>=315&&e.getX()<=485&&e.getY()>=350&&e.getY()<=400){
            setMode(3);
         }
       }
       else if(mode==2){
         if(e.getX()>=340&&e.getX()<=460&e.getY()>=275&&e.getY()<=325){
            setMode(0);
         }
       }
       else if(mode==3){
         if(e.getX()>=340&&e.getX()<=460&e.getY()>=275&&e.getY()<=325){
            setMode(0);
         }
       }
      }
      public void mouseReleased(MouseEvent e)
      { }
      
   }
   private class PanelMotionListener extends MouseMotionAdapter
   {
      public void mouseDragged(MouseEvent e){ }
   }
}