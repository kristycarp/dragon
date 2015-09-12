import java.awt.*;
import java.util.*;
import java.io.*;

public class DragonDraw
{
   public final int SIZE = 500;
   private DrawingPanel panel = null;
   //private int panelSize = null;
   
   public DragonDraw()
   {
      panel = new DrawingPanel(SIZE, SIZE);
   }
   
   public DragonDraw(int size)
   {
      panel = new DrawingPanel(size, size);
   }
   
   public void drawCurve(String filename, int level)
   {
      String dragon = readFile(filename);
      if (dragon != null)
      {
         int xStart = (int) (panel.getHeight() * (2.0  / 3.0));
         int yStart = (int) (panel.getHeight() * (2.0  / 3.0));
         //System.out.println(panel.getHeight());
         //System.out.println(xStart + ", " + yStart);
         int length = SIZE / level / 10;
         Graphics g = panel.getGraphics();
         int xNew = xStart + length;
         int yNew = yStart;
         g.drawLine(xStart, yStart, xNew, yNew);
         String direction = "down"; //relative to the viewer
         for (int ii = 1; ii < dragon.length(); ii++)
         {
            xStart = xNew;
            yStart = yNew;
            if (direction.equals("up") || direction.equals("down"))
            {
               yNew = yStart;
               if (dragon.charAt(ii) == 'R' && direction.equals("up") || dragon.charAt(ii) == 'L' && direction.equals("down"))
               {
                  xNew = xStart + length;
                  direction = "right";
               }
               else
               {
                  xNew = xStart - length;
                  direction = "left";
               }
            }
            else
            {
               xNew = xStart;
               if (dragon.charAt(ii) == 'R' && direction.equals("right") || dragon.charAt(ii) == 'L' && direction.equals("left"))
               {
                  yNew = yStart + length;
                  direction = "down";
               }
               else
               {
                  yNew = yStart - length;
                  direction = "up";
               }
            }
            g.drawLine(xStart, yStart, xNew, yNew);
         }
         
      }
      else
      {
         System.out.println("An error ocurred.");
      }  
   }
   
   private static String readFile(String filename)
   {
      Scanner fileScan = null;
      File file = null;
      try
      {
         file = new File(filename);
         fileScan = new Scanner(file);
      }
      catch (FileNotFoundException e) //this shouldn't happen because file was already tested
      {
         System.out.print("File not found. Try again: ");
         return null;
      }
      return fileScan.next();
   }
}