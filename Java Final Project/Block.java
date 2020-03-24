/*
   Author:Rishi
   Purpose:This class creates a block which interacts with the player
*/
import java.awt.*;

public class Block{
   private int type,width,height,x,y;
   private static int panelWidth,panelHeight,gameX;
   public Block(int a,int b,int w,int h,int t){//constructor
      x=a;
      y=b;
      width=w;
      height=h;
      type=t;
   }
   public void draw(Graphics g){//draws the block with correct color
      switch(type){
         case 0:
            g.setColor(Color.black);
            break;
         case 1:
            g.setColor(Color.red);
            break;
         case 2:
            g.setColor(Color.green);
            break;
      }
      g.fillRect(x-gameX,y,width,height);
   }
   public static void setPanel(int w,int h){//sets panel height and width
      panelWidth=w;
      panelHeight=h;
   }
   public static void setGameX(int a){//sets gameX so the block is drawn properly.
      gameX=a;
   }
   
   
}