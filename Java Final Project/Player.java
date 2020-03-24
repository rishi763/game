/*
Author:Rishi
Purpose: This is a class to control and move the player. It checks for collosions and also draws the player
*/
import java.awt.*;

public class Player{
   private double velocity,direction,velocityDown,velocityUp,velocityRight,velocityLeft;
   private final double FRAMES_PER_SECOND=60; //caps the frames per second at 60 to make movement smoother
   private boolean moveUp,moveRight,moveLeft; 
   private int[][][] blocks={{{0,440,2000,10,0},{350,150,50,290,0},{150,400,50,40,0},{0,250,50,190,0},{350,0,50,100,0},{600,0,50,400,0},{400,300,80,50,1},{520,300,80,50,1},{650,120,50,50,0},{650,280,50,50,0},{800,120,50,320,0},{800,0,50,50,1},{1000,200,100,50,0},{850,400,1050,40,1},{1400,100,100,50,0},{1900,400,100,40,2}},{{400,300,50,140,0},{0,440,2000,10,0},{0,250,50,190,0},{200,90,100,50,0},{600,90,100,50,0},{450,400,1550,40,1}}};
   private int limitYMax,limitYMin,limitXMax,limitXMin,gameLength;
   private int x,y,level;
   private int drawX;
   private Color color;
   public static boolean levelComplete;
   private int panelWidth,panelHeight;
   public Player(int l,Color c){//initilizes all variables
      velocityUp=0.0;
      velocityRight=0.0;
      velocityDown=0.0;
      velocityLeft=0.0;
      limitYMax=450;
      limitYMin=0;
      level=l;
      color=c;
      gameLength=2000;
      limitXMax=gameLength-20;
      limitXMin=0;
      velocity=0.0;
      direction=0.0;
      x=300;
      y=420;
      drawX=300;
      moveUp=false;
      moveRight=false;
      moveLeft=false;
      panelHeight=450;
      panelWidth=800;
   }
   public void draw(Graphics g){//draws the player on these coordinates
      g.setColor(color);
      g.drawRoundRect((int)drawX,(int)y,19,19,5,5);
      g.fillRoundRect((int)drawX,(int)y,19,19,5,5);
   }
   public void setColor(Color c){//sets color of player
      color=c;
   }
   public void updateVelocity(){//updates the velocity and finds the vectors to add to the movement
      Block.setGameX(x-drawX);
      PlatformerUserPanel.setGameX(x-drawX);//sets the place to draw on the panal for other classes and above 
      setLimitYMin();
      setXLimitXMax();
      setLimitXMin();//sets restrictions on movement
      if(moveUp&&y==limitYMax){//moves up
         velocityUp=900/FRAMES_PER_SECOND;
         velocity=Math.abs(velocity*Math.cos(direction));
         direction=(velocity*Math.cos(direction)>=0)?0:Math.PI;
         addVector(velocityUp, -Math.PI/2);
      }  
      else{
         velocityUp=0.0;
      }

      if(isFalling()){//moves down
         velocityDown+=1/(FRAMES_PER_SECOND);
         addVector(velocityDown, Math.PI/2);
      }
      else{
         addVector(velocityDown, -Math.PI/2);
         velocityDown=0.0;
      }
      if(moveRight){//moves right
         velocityRight+=0.3/FRAMES_PER_SECOND;
         addVector(velocityRight, 0.0);
      }
      else{
         velocityRight=0.4;
      }
      if(moveLeft){//moves left
         velocityLeft+=0.3/FRAMES_PER_SECOND;
         addVector(velocityLeft, Math.PI);
      }
      else{
         velocityLeft=0.4;
      }
      velocity*=0.95;
   }
   public boolean isDead(){//checks if player is on red block
      for(int[] block:blocks[level]){
         if(block[4]==1&&(((block[0]<=x+20&&x<=block[0]+block[2])&&(y+20==block[1]||y==block[1]+block[3]))||((block[1]<=y+20&&y<=block[1]+block[3])&&(x+20==block[0]||x==block[0]+block[2])))){
            return true;
         }
      }
      return false;
   
   }
   public boolean levelCompleted(){//checks if player is on green block
      for(int[]block:blocks[level]){
         if(block[4]==2&&(((block[0]<=x+20&&x<=block[0]+block[2])&&(y+20==block[1]||y==block[1]+block[3]))||((block[1]<=y+20&&y<=block[1]+block[3])&&(x+20==block[0]||x==block[0]+block[2])))){
            return true;
         }
      }
      return false;
   }
   private void addVector(double v,double d){//adds Movement vectors to each other
      double x1=trunc(velocity*Math.cos(direction));
      double y1=trunc(velocity*Math.sin(direction));
      double tempX=trunc(v*Math.cos(d));
      double tempY=trunc(v*Math.sin(d));
      velocity=Math.sqrt(Math.pow(x1+tempX,2)+Math.pow(y1+tempY,2));
      direction=Math.atan((y1+tempY)/(x1+tempX))+(((x1+tempX)>=0)?0:Math.PI);

   }
   private boolean isFalling(){//checks if player is on black block 
      boolean ans=false;
      int min=450;
      for(int[] block:blocks[level]){
         if(block[0]<x+20&&x<block[0]+block[2]){
            if(y+20<=block[1]&&min>block[1]-20){
               min=block[1]-20;
               ans=true;
            }           
         }
      }
      limitYMax=min;
      return ans;
   }
   private void setLimitYMin(){//sets minimum of y coordinate
      int max=0;
      for(int[] block:blocks[level]){
         if(block[0]<x+20&&x<block[0]+block[2]){
            if(y>=block[1]+block[3]&&max<block[1]+block[3]){
               max=block[1]+block[3];
            }
         }  
      }
      limitYMin=max;
   }
   private void setXLimitXMax(){//sets maximum of x coordinate
      int min=gameLength-20;
      for(int[]block:blocks[level]){
         if(block[1]<y+20&&y<block[1]+block[3]){
            if(x+20<=block[0]&&min>block[0]-20){
               min=block[0]-20;
            }
         }
      }
      limitXMax=min;
   }
   private void setLimitXMin(){//sets minimum of x coordinate
      int max=0;
      for(int[] block:blocks[level]){
         if(block[1]<y+20&&y<block[1]+block[3]){
            if(x>=block[0]+block[2]&&max<block[0]+block[2]){
               max=block[0]+block[2];
            }
         }  
      }
      limitXMin=max;
   }
   private int[] checkDiagonalCollosions(int a,int b,int c,int d){//checks if player is diagonally colliding with block 
      int slope,yIntercept;
      for(int[]block:blocks[level]){
         slope=0;
         yIntercept=0;      
         if(block[0]<c&&c<block[0]+block[2]&&block[1]<d&&d<block[1]+block[3]){
            slope=(d-b)/(c-a);
            yIntercept=d-slope*c;
            int[] arr={block[0]+block[2],slope*(block[0]+block[2])+yIntercept};
            return arr; 
         }
         else if(block[0]<c&&c<block[0]+block[2]&&block[1]<d+20&&d+20<block[1]+block[3]){
            slope=(d-b+20)/(c-a);
            yIntercept=d+20-slope*c;
            int[] arr={block[0]+block[2],slope*(block[0]+block[2])+yIntercept-20};
            return arr; 
         }
         else if(block[0]<c+20&&c+20<block[0]+block[2]&&block[1]<d&&d<block[1]+block[3]){
            slope=(d-b)/(c+20-a);
            yIntercept=d-slope*(c+20);
            int[] arr={block[0]-20,slope*(block[0])+yIntercept};
            return arr;      
         }
         else if(block[0]<c+20&&c+20<block[0]+block[2]&&block[1]<d+20&&d+20<block[1]+block[3]){
            slope=(d-b+20)/(c-a+20);
            yIntercept=d+20-slope*(c+20);
            int[] arr={block[0]-20,slope*(block[0])+yIntercept-20};
            return arr; 
         }     
      }
      
      int[] arr={c,d};
      return arr;
   }

   public void move(){//moves player
      if(y==limitYMax){
         addVector(velocityDown, -Math.PI/2);
         velocityDown=0.0;
      }
      if(y==limitYMin){
         
         velocityUp=0.0;
      }
      if(x==limitXMax){
         velocityRight=0.0;
      }
      if(x==limitXMin){

         velocityLeft=0.0;
      }

      int oldX=x;
      int oldY=y;
      int[]arr=checkDiagonalCollosions(oldX,oldY,Math.min(Math.max(x+(int)(velocity*Math.cos(direction)),limitXMin),limitXMax),Math.min(Math.max(y+(int)(velocity*Math.sin(direction)),limitYMin),limitYMax));
      x=arr[0];
      y=arr[1];
      if(x<300){
         drawX=x;
      }
      else if(x>=gameLength-320){
         drawX=-gameLength+x+800;
      }
      else{
         if(drawX+x-oldX<300){
            drawX=300;
         }
         else if(drawX+x-oldX>500){
            drawX=500;
         }
         else{
            drawX+=x-oldX;
         }
      }
   }
   private double trunc(double num){//truncates number
      return((int)(100000*num))/100000.0;  
   }
   public void setMoveLeft(boolean bool){//checks if Left arrow is pressed
      moveLeft=bool;
   }
   public void setMoveUp(boolean bool){//checks if Up arrow is pressed
      moveUp=bool;
   }
   public void setMoveRight(boolean bool){//checks if Right arrow is pressed
      moveRight=bool;
   }
}