
package TetrisPackage;


import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import java.util.Scanner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import Utils.TextValuePanel;


/*
 * An object of this class can be used to display several key pieces of information relating to a game of Tetris, such
   as the score and the next tetromino piece.
    
 * Composition:
     (a). Fields (9)
         (i). Static Fields (4) 
     (b). Constructors (1)
     (c). Public Methods (12)
         (i). Accessors (4)
         (ii). Mutators (7)
*/
public class TetrisInformation
    extends JPanel
{
    
// (a). Fields (9) =====================================================================================================
    
    // A label that displays the number of lines the player has cleared.
    private TextValuePanel f_tvp_lines_cleared;
    
    // A label that displays the player's score.
    private TextValuePanel f_tvp_score;
    
    // A label that displays the high score.
    private TextValuePanel f_tvp_high_score;
    
    // A label that displays the current level.
    private TextValuePanel f_tvp_level;
    
    // A grid that shows the next tetromino.
    private TetrisGrid f_grid_next_tetromino;
    
    
// (b)(i). Static Fields (4) -------------------------------------------------------------------------------------------
    
    // The size of the padding between the sides of the panel and its content (as a proportion of the panel's width).
    private static final float s_padding_proportion_horizontal = 0.1f;
    
    // The size of the padding between the top and bottom of the panel and its content (as a proportion of the panel's height).
    private static final float s_padding_proportion_vertical = 0.1f;
    
    // The size of the gap between each element (as a proportion of the panel's height).
    private static final float s_element_gap_proportion = 0.05f;
    
    // The number of elements in he panel.
    private static final int s_num_elements = 5;
    
    private static final long serialVersionUID = 1L;
    
    
    
// (b). Constructors (1) ===============================================================================================
    
    /* Constructor
     
     * Parameters
         > a_width: the panel's width.
         > a_height: the panel's height.
         > a_colour_background: the panel's background colour.
    */
    public TetrisInformation(int a_width, int a_height, Color a_colour_background)
    {
        super(new GridBagLayout());
        
        // Set the panel's background colour.
        super.setBackground(a_colour_background);
        
        super.setBorder(BorderFactory.createLineBorder(Tetris.S_COLOUR_BORDERS_DEFAULT));
        
        // The horizontal and vertical padding.
        int l_padding_horizontal = (int)( a_width * s_padding_proportion_horizontal );
        int l_padding_vertical = (int)( a_height * s_padding_proportion_vertical );
        
        // The gap between each element.
        int l_element_gap = (int)( a_height * s_element_gap_proportion );
        
        // The width of the TextValueCanvas elements.
        int l_width_elements = a_width - 2 * l_padding_horizontal;
        
        // The height of all the elements.
        int l_height_elements = (a_height - 2 * l_padding_vertical - (s_num_elements - 1) * l_element_gap) / s_num_elements;
        
        
        // Get the current high score.
        
        int l_high_score = 0;
        File l_file_high_score = new File(Tetris.s_file_high_score);
        Scanner l_scanner = null;
        
        try 
        {
            l_scanner = new Scanner(l_file_high_score);
        
            l_high_score = l_scanner.nextInt();
        }
        catch (Exception e)
        { e.printStackTrace(); }
        finally
        { l_scanner.close(); }
        
        
        // Create the TextValueCanvas objects.
        f_tvp_lines_cleared = new TextValuePanel("Lines", 0, l_width_elements, l_height_elements, 
                                                 Tetris.S_COLOUR_BORDERS_DEFAULT, Tetris.S_COLOUR_BACKGROUNDS_DEFAULT,
                                                 Tetris.S_COLOUR_FOREGROUNDS_DEFAULT);
        f_tvp_score = new TextValuePanel("Score", 0, l_width_elements, l_height_elements, Tetris.S_COLOUR_BORDERS_DEFAULT,
                                          Tetris.S_COLOUR_BACKGROUNDS_DEFAULT, Tetris.S_COLOUR_FOREGROUNDS_DEFAULT);
        f_tvp_high_score = new TextValuePanel("High Score", l_high_score, l_width_elements, l_height_elements, 
                                              Tetris.S_COLOUR_BORDERS_DEFAULT, Tetris.S_COLOUR_BACKGROUNDS_DEFAULT,
                                              Tetris.S_COLOUR_FOREGROUNDS_DEFAULT);
        f_tvp_level = new TextValuePanel("Level", 0, l_width_elements, l_height_elements, Tetris.S_COLOUR_BORDERS_DEFAULT,
                                         Tetris.S_COLOUR_BACKGROUNDS_DEFAULT, Tetris.S_COLOUR_FOREGROUNDS_DEFAULT);
        
        // Create the grid.
        f_grid_next_tetromino = new TetrisGrid(l_height_elements, true, 6, 6);
        
        // Create a constraints object, which allows for the graphical elements to be arranged.
        GridBagConstraints l_constraints = new GridBagConstraints();
        
        // Add f_tvc_lines_cleared to the panel.
        l_constraints.gridx = 0; l_constraints.gridy = 0; // (0,0).
        l_constraints.insets = new Insets(l_padding_vertical, l_padding_horizontal, l_element_gap, l_padding_horizontal);
        super.add(f_tvp_lines_cleared, l_constraints);
        
        // Add f_tvc_score to the panel.
        l_constraints.gridx = 0; l_constraints.gridy = 1; // (0,1).
        l_constraints.insets = new Insets(0, l_padding_horizontal, l_element_gap, l_padding_horizontal);
        super.add(f_tvp_score, l_constraints);
        
        // Add f_tvc_high_score to the panel.
        l_constraints.gridx = 0; l_constraints.gridy = 2; // (0,2).
        l_constraints.insets = new Insets(0, l_padding_horizontal, l_element_gap, l_padding_horizontal);
        super.add(f_tvp_high_score, l_constraints);
        
        // Add f_tvc_level to the panel.
        l_constraints.gridx = 0; l_constraints.gridy = 3; // (0,3).
        l_constraints.insets = new Insets(0, l_padding_horizontal, l_element_gap, l_padding_horizontal);
        super.add(f_tvp_level, l_constraints); 
        
        // Add f_grid_next_tetromino to the panel.
        l_constraints.gridx = 0; l_constraints.gridy = 4; // (0,4).
        l_constraints.insets = new Insets(0, l_padding_horizontal, l_padding_vertical, l_padding_horizontal);
        l_constraints.anchor = GridBagConstraints.WEST;
        super.add(f_grid_next_tetromino, l_constraints);
    }
    
    
    
// (c). Public Methods (12) ============================================================================================
    
    /*
     * This method sets the values of the labels (and grid) to initial/empty values.
    */
    public void Reset()
    {
        f_tvp_lines_cleared.SetValue(0);
        f_tvp_score.SetValue(0);
        f_tvp_level.SetValue(1);
        f_grid_next_tetromino.Reset();
    }
    
    
// (c)(i). Accessors (4) -----------------------------------------------------------------------------------------------
    
    /* Accessor of f_tvp_lines_cleared
     * This method returns the value associated with f_tvp_lines_cleared.
    */
    public int GetNumLinesCleared()
    {
        return f_tvp_lines_cleared.GetValue();
    }
    
    /* Accessor of f_tvp_score
     * This method returns the value associated with f_tvp_score.
    */
    public int GetScore()
    {
        return f_tvp_score.GetValue();
    }
    
    /* Accessor of f_tvp_high_score
     * This method returns the value associated with f_tvp_high_score.
    */
    public int GetHighScore()
    {
        return f_tvp_high_score.GetValue();
    }
    
    /* Accessor of f_tvp_level
     * This method returns the value associated with f_tvp_level.
    */
    public int GetLevel()
    {
        return f_tvp_level.GetValue();
    }
    
    
// (c)(ii). Mutators (7) -----------------------------------------------------------------------------------------------
    
    /* Mutator of f_tvp_level
     * This method increments the value associated with f_tvp_level by 1.
    */
    public void IncrementLevel()
    {
        f_tvp_level.Increment();
    }
    
    /* Mutator of f_tvp_score
     * This method increments the value associated with f_tvp_score by the given amount.
     
     * Parameters:
         > a_score: the amount by which f_tvp_score's value is to be increased.
    */
    public void IncrementScore(int a_score)
    {
        int l_score_current = f_tvp_score.GetValue();
        
        l_score_current += a_score;
        
        f_tvp_score.SetValue(l_score_current);
    }
    
    /* Mutator of f_tvp_score
     * This method sets the value associated with f_tvp_score with the given value.
     
     * Parameters:
         > a_score: the new value of f_tvp_score.
    */
    public void SetScore(int a_score)
    {
        f_tvp_score.SetValue(a_score);
    }
    
    /* Mutator of f_tvp_lines_cleared
     * This method increments the value associated with f_tvp_lines_cleared by the given amount.
     
     * Parameters:
         > a_line_clears: the amount by which f_tvp_lines_cleared value is to be increased.
    */
    public void IncrementLinesCleared(int a_line_clears)
    {
        int l_num_line_clears = f_tvp_lines_cleared.GetValue();
        
        l_num_line_clears += a_line_clears;
        
        f_tvp_lines_cleared.SetValue(l_num_line_clears);
    }
    
    /* Mutator of f_tvp_lines_cleared
     * This method sets the value associated with f_tvp_lines_cleared with the given value.
     
     * Parameters:
         > a_num_lines: the new value of f_tvp_lines_cleared.
    */
    public void SetLinesCleared(int a_num_lines)
    {
        f_tvp_lines_cleared.SetValue(a_num_lines);
    }
    
    /* Mutator of f_tvp_score
     * This method sets the value associated with f_tvp_high_score with that of f_tvp_score and also updates the file in
       which the high score is stored.
    */
    public void UpdateHighScore()
    {
        int l_high_score = f_tvp_score.GetValue();
        
        FileWriter l_writer = null;
        try
        {
            l_writer = new FileWriter(Tetris.s_file_high_score);
            
            l_writer.write(String.valueOf(l_high_score));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try 
            { l_writer.close(); }
            catch (IOException e) 
            { e.printStackTrace(); }
        }
        
        f_tvp_high_score.SetValue(l_high_score);
    }
    
    
    /* Mutator of f_tvp_lines_cleared
     * This method changes the tetromino displayed on f_grid_next_tetromino.
     
     * Parameters:
         > a_tetromino: the new tetromino to be displayed.
    */
    public void SetNextTetromino(Tetromino a_tetromino)
    {
        // Remove the 'former next' tetromino.
        f_grid_next_tetromino.Reset();
        
        // Draw the 'current next' tetromino.
        f_grid_next_tetromino.DrawTetromino(a_tetromino, TetrisGrid.DrawPosition.CentreMid);
    }
    
    
}