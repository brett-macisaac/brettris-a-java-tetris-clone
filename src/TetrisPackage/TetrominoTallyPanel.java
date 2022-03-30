
package TetrisPackage;


import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import TetrisPackage.TetrisGrid.DrawPosition;

import Utils.Label;


/*
 * An object of this class can be used to display how many of each tetromino have spawned thus far in a Tetris game.
    
 * Composition:
     (a). Fields (6)
         (i). Static Fields (4) 
     (b). Constructors (1)
     (c). Public Methods (2)
*/
public class TetrominoTallyPanel
    extends JPanel
{

// (a). Fields (6) =====================================================================================================
    
    // Each grid displays one tetromino.
    private TetrisGrid f_grids[];
    
    // Each Label stores the tally associated with the tetromino in a TetrisGrid object of f_tetrominos.
    private Label f_labels_tallies[];
    
    
// (a)(i). Static Fields (4) -------------------------------------------------------------------------------------------
    
    // The size of the padding between the sides of the panel and its content (as a proportion of the panel's width).
    private static final float s_padding_proportion_horizontal = 0.05f;
    
    // The size of the padding between the top and bottom of the panel and its content (as a proportion of the panel's height).
    private static final float s_padding_proportion_vertical = 0.05f;
    
    // The size of the gap between the f_tetrominos objects and their associated f_tallies Labels (as a proportion of the panel's width).
    private static final float s_element_gap_horizontal = 0.02f;
    
    // The size of the gap between consecutive f_tetrominos objects (as a proportion of the panel's height).
    private static final float s_element_gap_vertical = 0.02f;
    
    private static final long serialVersionUID = 1L;
    
    
    
// (b). Constructors (1) ===============================================================================================
    
    /* Constructor
     
     * Parameters:
         > a_width: the panel's width (pixels).
         > a_height: the panel's height (pixels).
         > a_colour_background: the panel's background colour.
    */
    public TetrominoTallyPanel(int a_width, int a_height, Color a_colour_background)
    {
        super(new GridBagLayout());
        
        super.setBackground(a_colour_background);
        
        super.setBorder(BorderFactory.createLineBorder(Tetris.S_COLOUR_BORDERS_DEFAULT));
        
        // The horizontal and vertical padding.
        int l_padding_horizontal = (int)( a_width * s_padding_proportion_horizontal );
        int l_padding_vertical = (int)( a_height * s_padding_proportion_vertical );
        
        // The gaps between the elements.
        int l_element_gap_horizontal = (int)( a_width * s_element_gap_horizontal );
        int l_element_gap_vertical = (int)( a_height * s_element_gap_vertical );
        
        // The number of tetrominos.
        int l_num_tetrominos = Tetromino.Type.values().length;
        
        // The height of the elements (labels and grids).
        // note: this is also the width of the grids, which is because the grids will have the same number of rows and columns.
        int l_height_elements = (a_height - 2*l_padding_vertical - (l_num_tetrominos - 1)*l_element_gap_vertical) / l_num_tetrominos;
        
        // The total width available for a TetrisGrid and Label element.
        int l_width_available = a_width - 2*l_padding_horizontal - l_element_gap_horizontal;
        
        // The width of each Label object.
        int l_width_label = l_width_available - l_height_elements;
        
        // Create the grids.
        f_grids = new TetrisGrid[l_num_tetrominos];
        Tetromino f_tetromino;
        for (int i = 0; i < f_grids.length; ++i)
        {
            // Create the grid
            f_grids[i] = new TetrisGrid(l_height_elements, true, 4, 4);
            
            // Create the tetromino that will be displayed on the grid.
            f_tetromino = new Tetromino(Tetromino.Type.values()[i]);
            
            // Draw the tetromino upon the grid.
            f_grids[i].DrawTetromino(f_tetromino, DrawPosition.CentreMid);
        }
        
        // Create the labels.
        f_labels_tallies = new Label[l_num_tetrominos];
        for (int i = 0; i < l_num_tetrominos; ++i)
        {
            f_labels_tallies[i] = new Label("x0", Label.Alignment.Left, l_width_label, l_height_elements);
        }
        
        // Constraints object to help position the elements.
        GridBagConstraints l_constraints = new GridBagConstraints();
        
        // Add the grids and labels to the panel.
        l_constraints.gridy = 0;
        for (int i = 0; i < f_grids.length; ++i)
        {
            // Set constraints for the grid.
            l_constraints.gridx = 0; l_constraints.gridy = i;
            l_constraints.insets = new Insets((i != 0) ? l_element_gap_vertical : l_padding_vertical, l_padding_horizontal, 
                                              (i != f_grids.length - 1) ? 0 : l_padding_vertical, l_element_gap_horizontal);
            l_constraints.anchor = GridBagConstraints.CENTER;
            
            // Add grid.
            super.add(f_grids[i], l_constraints);
            
            // Set constraints for the label.
            l_constraints.gridx = 1;
            l_constraints.insets = new Insets((i != 0) ? l_element_gap_vertical : l_padding_vertical, 0, 
                                              (i != f_grids.length - 1) ? 0 : l_padding_vertical, l_element_gap_horizontal);
            l_constraints.anchor = GridBagConstraints.WEST;
            
            // Add the label.
            super.add(f_labels_tallies[i], l_constraints);
        }

    }
    
    
    
// (c). Public Methods (2) ============================================================================================
    
    /*
     * This method resets the tallies of each tetromino.
    */
    public void Reset()
    {
        for (Label l : f_labels_tallies)
        {
            l.SetText("x0");
        }
        
    }
    
    /*
     * This method increments the tally associated with the given tetromino by 1. 
    */
    public void IncrementTally(Tetromino a_tetromino)
    {
        // Get the index of the label to increment.
        int l_index_tetromino = a_tetromino.GetType().ordinal();
        
        // An 'x' followed by the number tallied so far: e.g. "x12".
        String l_tally_string = f_labels_tallies[l_index_tetromino].GetText();
        
        // Just the integer part of l_tally_string: e.g. if it's "x12", l_value_string will be "12".
        String l_value_string = l_tally_string.substring(1);
        
        // The current tally.
        int l_value = Integer.parseInt(l_value_string);
        
        // Increment the tally.
        ++l_value;
        
        // Form the new string.
        String l_tally_string_updated = "x" + String.valueOf(l_value);
        
        // Update the label with the new tally.
        f_labels_tallies[l_index_tetromino].SetText(l_tally_string_updated);
    }
  
    
}
