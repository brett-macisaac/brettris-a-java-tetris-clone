
package TetrisPackage;


/*
 * Description:
     > This program is a java clone of the game Tetris, which I - with the help of another's implementation - coded in 
       order to extend my programming skills and to assist others' learning by making it publicly available. 
     > There are other Tetris clone implementations available online, such as the one listed in the credits, yet the 
       comments are typically insufficient for most beginner or intermediate programmers to readily understand, 
       particularly when they're made using a game engine such as Unity, which requires a plethora of prerequisite
       knowledge; thus, I decided to create a more beginner-friendly implementation in java. 
     > Note: this Tetris clone is somewhat different to other implementations, particularly as regards the scoring system.
  
  
  * Controls:
  
      > The controls can be found in the TetrisFrame class, but below is a readable summary:
          * Move Left -----------> [left arrow]
          * Move Right ----------> [right arrow]
          * Soft Drop -----------> [down arrow]
          * Hard Drop -----------> [up arrow]
          * Rotate Clockwise ----> a
          * Rotate Antilockwise -> d
          * 
      > The above controls are for the 'game' mode, but the program also offers a testing mode, the controls of which 
        are as follows:
          * Move Left -----------> [left arrow]
          * Move Right ----------> [right arrow]
          * Move Down -----------> [down arrow]
          * Move Up -------------> [up arrow]
          * Clear Grid ----------> c
          * Remove Full Lines ---> r
          * Delete Tetromino ----> [delete]
          * Spawn I piece -------> i
          * Spawn J piece -------> j
          * Spawn L piece -------> l
          * Spawn O piece -------> o
          * Spawn S piece -------> s
          * Spawn T piece -------> t
          * Spawn Z piece -------> z
          * Spawn random piece --> [space]
        
        
 * Credits: 
     > https://github.com/JohnnyTurbo/LD43/tree/master/Assets/Scripts : C# (UNITY) implementation.
     
 * Author: Brett J Macisaac.
 
*/


import java.awt.Color;


/* Top-level Class
 * The entry-point class of the program.
 
 * Class Composition:
     (a). Static Fields (4) 
     (b). Public Methods (1)
     (c). Auxiliaries (1)
 
*/
public class Tetris
{
    
// (a). Static Fields (4)  =============================================================================================
    
    /*
     * The file in which the game's high-score is stored. 
    */
    public static final String s_file_high_score = "high_score.txt";
    
    /*
     * The (default) colour of the borders used by the graphical elements. 
    */
    public static final Color S_COLOUR_BORDERS_DEFAULT = new Color(148,0,211);
    
    /*
     * The (default) colour of the backgrounds used by the graphical elements. 
    */
    public static final Color S_COLOUR_BACKGROUNDS_DEFAULT = Color.BLACK;
    
    /*
     * The (default) colour of the foregrounds (i.e. text colour) used by the graphical elements.
    */
    public static final Color S_COLOUR_FOREGROUNDS_DEFAULT = Color.WHITE;
    
    
    
// (b). Public Methods (1) =============================================================================================
    
    /* Entry-point Method
     * This method is the program's entry-point function.
     
     * Notes:
         (a). The main purpose of the welcome message is to open the console before the game's frame opens; if the 
              console opens after the frame is opened, the frame will be force-minimised by the console.
 
    */
    public static void main(String[] args) 
    {
        // (a). Display a welcome message in the console.
        System.out.println("Welcome to Bretris, a Tetris clone made by a guy named Brett!");
        
        // This code schedules a job on the event-dispatching thread, which creates and shows the application's GUI.
        javax.swing.SwingUtilities.invokeLater(
                new Runnable() 
                {
                    public void run() 
                    {
                        CreateAndShowGUI();
                    }
                    
                });
        
    }
    
    
    
// (c). Auxiliaries (1) ================================================================================================
    
    /* Auxiliary of main
     * main uses this method to create the frame.
     
    */
    private static void CreateAndShowGUI() 
    {
        // Create the game's frame object and set it to visible..
        (new TetrisFrame("Tetris Game")).setVisible(true);
    }
    
}
