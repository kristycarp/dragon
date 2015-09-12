//Kristy Carpenter, Computer Science II, Fall 2014, Section C (4th period)
//Assignment 8--The Dragon Curve
//
//This program outputs a text file and draws in the DrawingPanel a dragon curve fractal of a level
//indicated by the user. Recursion is used in creating the text representation of the dragon
//(including the complement). This program calls a method from the DragonDraw class.

import java.io.*;
import java.util.*;
import java.awt.*;


public class DragonGenerate
{
   /**
     *this class constant is the minimum level for the dragon
     */
   public static final int MIN_LEVEL = 1;
   
   /**
     *this class constant is the maximum level for the dragon
     */
   public static final int MAX_LEVEL = 25;
   
   /**
     *this class constant is the minimum panel height and width
     */
   public static final int MIN_PANEL_SIZE = 100;
   
   /**
     *this class constant is the maximum panel height and width
     */
   public static final int MAX_PANEL_SIZE = 2000;
   
   /**
     *this class constant is the base for the output file name
     */
   public static final String OUTPUT_BASE = "dragon";
   
   /**
     *this class constant is the extension for the output file name
     */
   public static final String OUTPUT_EXTENSION = ".txt";
   
   /**
     *this is the main method, where the program begins
     *
     *@param args
     */
   public static void main(String[] args)
   {
      intro();
      int level = prompt("Enter the level of the fractal you'd like to see", MIN_LEVEL, MAX_LEVEL);
      int panelSize = prompt("Enter the size of your drawing panel, in pixels", MIN_PANEL_SIZE, MAX_PANEL_SIZE);
      String dragon = generate(level);
      //System.out.println(dragon);
      String filename = OUTPUT_BASE + level + OUTPUT_EXTENSION;
      System.out.println("Path generated, writing to file " + filename + "...");
      if (outputFile(dragon, filename))
      {
         System.out.println("Drawing curve...");
         DragonDraw dd = new DragonDraw(panelSize);
         dd.drawCurve(filename, level);
      }
   }
   
   /**
     *this method prints the introductory message
     */
   public static void intro()
   {
      System.out.println("This program will generate a fractal called the Dragon Curve");
      System.out.println("first explored by John Heighway, Bruce Banks, and William Harter");
      System.out.println("at NASA in the 1960's");
      System.out.println("");
   }
   
   /**
     *this method prompts the user for the necessary input, checks to see if it is an integer
     *within range, returns it if it is good input and reprompts the user if it is not
     *
     *@param prompt - the prompt for the user that appears in the console
     *@param min - the minimum value for the input
     *@param max - the maximum value for the input
     *@return number - the input from the user that fits all requirements
     */
   public static int prompt(String prompt, int min, int max)
   {
      Scanner input = new Scanner(System.in);
      boolean good = false;
      int number = -500;
      while (!good)
      {
         System.out.print(prompt + " (" + min + "-" + max + "): ");
         if (!input.hasNextInt())
         {
            System.out.println("Input is not valid, you need to enter a number");
         }
         else
         {
            number = input.nextInt();
            if (number >= min && number <= max)
            {
               good = true;
            }
            else
            {
               System.out.println("Input is not valid, you need to enter a number between " + min + " and " + max);
            }
         }
         input.nextLine();
      }
      return number;
   }
   
   /**
     *this method generates the string version of the dragon recursively, given its level
     *
     *@param level - the level of the dragon
     *@return dragon - the string version of the dragon, generated recursively
     */
   public static String generate(int level)
   {
      if (level == 1)
      {
         return "R";
      }
      else
      {
         return (generate(level - 1) + "R" + complement(generate(level - 1), generate(level - 1).length() - 1));
      }
   }
   
   /**
     *this method generates the complement for a given dragon string through recursion. One use of
     *the method will produce and L if given an R and vice versa, then concatonate it with the
     *previous runs of the method (if needed).
     *
     *@param dragon - the string that needs to be complement-ed
     *@param place - an indicator for which character in the string needs to be processed
     *@return complement - the finished complement
     */
   public static String complement(String dragon, int place)
   {
      String nextTurn = "";
      if (dragon.charAt(place) == 'R')
      {
         nextTurn = "L";
      }
      else
      {
         nextTurn = "R";
      }
      
      if (place == 0) //if all of complement has been generated
      {
         return nextTurn;   
      }
      else
      {
         return (nextTurn + complement(dragon, place - 1));
      }
   }
   
   /**
     *this method takes the dragon string and prints it into a specified output file. If the output
     *file cannot be generated, it indicates this by returning false to the main method.
     *
     *@param dragon - the dragon to be printed into the output file
     *@param filename - the name of the output file
     *@return outputFile - indicates whether or not the output printing was successful
     */
   public static boolean outputFile(String dragon, String filename)
   {
      PrintStream output = null;
      File f = null;
      try
      {
         f = new File(filename);
         output = new PrintStream(f);
      }
      catch (FileNotFoundException e)
      {
         System.out.println("Error opening file, file not written");
         return false;
      }
      output.println(dragon);
      return true;
   }
   
}