
package TetrisPackage;


import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import Utils.Vector2D;


/*
 * An object of this class is a grid upon which tetrominos can be displayed.
 * This class can be thought of as a composition of class TetrisTile.
 
 * Composition:
     (a). Enumerations (1)
     (b). Fields (11)
         (i). Static Fields (7) 
     (c). Constructors (4)
     (d). Public Methods (9)
     (e). Auxiliaries (1)
      
*/
public class TetrisGrid
    extends JPanel
{
    
// (a). Enums (1) ======================================================================================================
    
    /*
     * An enum to represent general position 'types' of the grid. 
    */
    public enum DrawPosition
    { CentreMid, CentreLeft, CentreTop, CentreBottom }
    
    
    
// (b). Fields (11) ====================================================================================================
    
    /* The Grid
     * This collection of TetrisTile objects holds all of the tiles which comprise the grid.
     * Note that the origin is the top-left TetrisTile (i.e. the one at '[0][0]').
    */
    private TetrisTile f_tile_grid[][];
    
    /*
     * The dimensions of f_tile_grid: i.e. f_tile_grid[0..f_num_rows-1][0..f_num_columns-1]
    */
    private int f_num_rows;
    private int f_num_columns;

    /*
     * The colour of an empty tile.
    */
    private Color f_colour_empty_tile;
    
    
// (b)(i). Static Fields (7) -------------------------------------------------------------------------------------------
    
    
    // The default number of columns (i.e. the number of tiles in each row).
    private static final int S_NUM_COLUMNS_DEFAULT = 10;
    
    // The default number of rows (i.e. the number of tiles in each column).
    private static final int S_NUM_ROWS_DEFAULT = 22;
    
    /* Padding Factor
         * Each TetrisTile object in f_grid is surrounded by a gap equal to S_PADDING_AMOUNT of it's width/height. Note 
           that because the tiles are adjacent to each other, the overall gap/padding between two tiles is 
           S_PADDING_PROPORTION * 2.
         * Think of a TetrisTile as a castle and the padding around it its moat.
    */
    private static final double S_PADDING_PROPORTION = 0.03;
    
    // The maximum number of rows (max value of f_num_rows).
    private static final int S_MAX_NUM_ROWS = 50;
    
    // The minimum number of rows (min value of f_num_rows).
    private static final int S_MIN_NUM_ROWS = 4;
    
    // The maximum number of columns (max value of f_num_columns).
    private static final int S_MAX_NUM_COLUMNS = 25;
    
    // The minimum number of columns (min value of f_num_columns).
    private static final int S_MIN_NUM_COLUMNS = 4;
    
    private static final long serialVersionUID = 1L;
    
    
    
// (c). Constructors (4) ===============================================================================================
    
    /* Constructor
     
     * Parameters:
         > a_height: the height of the grid (pixels).
         > a_are_top_rows_visible: a flag that, when true, indicates that the top two rows (index 0 and 1) are 
                                   'off-screen'.
    */
    public TetrisGrid(int a_height, boolean a_are_top_rows_visible) 
    {
        // Call constructor of parent.
        super(new GridBagLayout());
        
        super.setBorder(BorderFactory.createLineBorder(Tetris.S_COLOUR_BORDERS_DEFAULT));
        
        // Set dimensions.
        f_num_rows = S_NUM_ROWS_DEFAULT;
        f_num_columns = S_NUM_COLUMNS_DEFAULT;
        
        // Set colours.
        super.setBackground(Tetris.S_COLOUR_BACKGROUNDS_DEFAULT);
        f_colour_empty_tile = Tetris.S_COLOUR_BACKGROUNDS_DEFAULT;
        
        // Create the grid.
        SetGrid(a_height, a_are_top_rows_visible);
    }
    
    /* Constructor
     
     * Parameters:
         > a_height: the height of the grid (pixels).
         > a_are_top_rows_visible: a flag that, when true, indicates that the top two rows (index 0 and 1) are 
                                   'off-screen'.
         > a_colour_background: the background colour of the grid.
         > a_colour_empty_tile: the colour of an empty tile.
    */
    public TetrisGrid(int a_height, boolean a_are_top_rows_visible, Color a_colour_background, Color a_colour_empty_tile) 
    {
        // Call constructor of parent.
        super(new GridBagLayout());
        
        super.setBorder(BorderFactory.createLineBorder(Tetris.S_COLOUR_BORDERS_DEFAULT));
        
        // Set dimensions.
        f_num_rows = S_NUM_ROWS_DEFAULT;
        f_num_columns = S_NUM_COLUMNS_DEFAULT;
        
        // Set colours.
        super.setBackground(a_colour_background);
        f_colour_empty_tile = a_colour_empty_tile;
        
        // Create the grid.
        SetGrid(a_height, a_are_top_rows_visible);
    }
    
    /* Constructor
    
     * Parameters:
         > a_height: the height of the grid (pixels).
         > a_are_top_rows_visible: a flag that, when true, indicates that the top two rows (index 0 and 1) are 
                                   'off-screen'.
         > a_num_rows: the grid's number of rows.
         > a_num_columns: the grid's number of columns.
    */
    public TetrisGrid(int a_height, boolean a_are_top_rows_visible, int a_num_rows, int a_num_columns) 
    {
        // Call constructor of parent.
        super(new GridBagLayout());
        
        super.setBorder(BorderFactory.createLineBorder(Tetris.S_COLOUR_BORDERS_DEFAULT));
        
        
        // Set dimensions.
        
        boolean l_is_num_rows_valid = (a_num_rows <= S_MAX_NUM_ROWS) && (a_num_rows >= S_MIN_NUM_ROWS);
        boolean l_is_num_columns_valid = (a_num_columns <= S_MAX_NUM_COLUMNS) && (a_num_columns >= S_MIN_NUM_COLUMNS);
        
        f_num_rows = l_is_num_rows_valid ? a_num_rows : S_NUM_ROWS_DEFAULT;
        f_num_columns = l_is_num_columns_valid ? a_num_columns : S_NUM_COLUMNS_DEFAULT;
        
        
        // Set colours.
        super.setBackground(Tetris.S_COLOUR_BACKGROUNDS_DEFAULT);
        f_colour_empty_tile = Tetris.S_COLOUR_BACKGROUNDS_DEFAULT;
        
        // Create the grid.
        SetGrid(a_height, a_are_top_rows_visible);
    }
    
    /* Constructor
        
     * Parameters:
         > a_height: the height of the grid (pixels).
         > a_are_top_rows_visible: a flag that, when true, indicates that the top two rows (index 0 and 1) are 
                                   'off-screen'.
         > a_colour_background: the background colour of the grid.
         > a_colour_empty_tile: the colour of an empty tile.
         > a_num_rows: the grid's number of rows.
         > a_num_columns: the grid's number of columns.
    */
    public TetrisGrid(int a_height, boolean a_are_top_rows_visible, int a_num_rows, int a_num_columns, 
                      Color a_colour_background, Color a_colour_empty_tile) 
    {
        // Call constructor of parent.
        super(new GridBagLayout());
        
        super.setBorder(BorderFactory.createLineBorder(Tetris.S_COLOUR_BORDERS_DEFAULT));
        
        
        // Set dimensions.
        
        boolean l_is_num_rows_valid = (a_num_rows <= S_MAX_NUM_ROWS) && (a_num_rows >= S_MIN_NUM_ROWS);
        boolean l_is_num_columns_valid = (a_num_columns <= S_MAX_NUM_COLUMNS) && (a_num_columns >= S_MIN_NUM_COLUMNS);
        
        f_num_rows = l_is_num_rows_valid ? a_num_rows : S_NUM_ROWS_DEFAULT;
        f_num_columns = l_is_num_columns_valid ? a_num_columns : S_NUM_COLUMNS_DEFAULT;
        
        
        // Set colours.
        super.setBackground(a_colour_background);
        f_colour_empty_tile = a_colour_empty_tile;
        
        // Create the grid.
        SetGrid(a_height, a_are_top_rows_visible);
    }
    
    
    
// (d). Public Methods (9) =============================================================================================
    
    /*
     * Sets all of the tiles to the colour f_colour_empty_tile.
    */
    public void Reset()
    {
        for (int row = f_num_rows - 1; row >= 0; --row)
        {   
            for (int col = 0; col < f_num_columns; ++col)
            {
                if (f_tile_grid[col][row].GetColour() == f_colour_empty_tile)
                {
                    continue;
                }
                
                f_tile_grid[col][row].SetColour(f_colour_empty_tile);
            }
            
        }
        
    }
    
    /*
     * This method returns true if the grid is completely empty; false if otherwise.
    */
    public boolean IsEmpty()
    {
        int l_index_bottom_row = f_num_rows - 1;
        
        // If the bottom row is empty, all other rows must also be empty.
        for (int col = 0; col < f_num_columns; ++col)
        {
            if (f_tile_grid[col][l_index_bottom_row].GetColour() != f_colour_empty_tile) // If the tile isn't empty.
            { return false; }
        }
        
        return true;
    }
    
    /*
     * This method clears all rows that are full and also shifts all other (non-full) rows downwards.
     
     * Return Value:
         > The number of full rows that were cleared. 
    */
    public int RemoveFullLines() //throws InterruptedException
    {   
        // The number of full rows found thus far.
        int l_num_full_rows = 0;
        
        for (int row = f_num_rows - 1; row >= 0; --row)
        {   
            boolean l_is_row_full = true;
            boolean l_is_row_empty = true;
            
            for (int col = 0; col < f_num_columns; ++col)
            {
                if (!l_is_row_full && !l_is_row_empty) // If both booleans have been falsified.
                {
                    break;
                }
                else if (f_tile_grid[col][row].GetColour() == f_colour_empty_tile)
                {
                    // If at least one tile is empty, the row can't be full.
                    l_is_row_full = false;
                }
                else
                {
                    // If at least one tile is filled, the row can't be empty.
                    l_is_row_empty = false;
                }
                
            }
            
            if (l_is_row_full) 
            { 
                // Record occurrence of full row.
                ++l_num_full_rows;
                
                // Clear the row.
                for (int col = 0; col < f_num_columns; ++col)
                {
                    if (f_tile_grid[col][row].GetColour() == f_colour_empty_tile)
                    { continue; }
                    
                    f_tile_grid[col][row].SetColour(f_colour_empty_tile);
                    try 
                    { Thread.sleep(25); } 
                    catch (InterruptedException e) 
                    { e.printStackTrace(); }
                }
                
            }
            else if (l_is_row_empty) // If the row is empty, this means that all rows above it are also empty.
            {
                break;
            }
            else if (l_num_full_rows != 0 )// && !l_is_row_full && !l_is_row_empty (if the row isn't full and isn't empty: i.e. semi-filled).
            {
                // Shift the (non-full, non-empty) row down l_num_full_rows rows.
                for (int col = 0; col < f_num_columns; ++col)
                {
                    // n.b. it's '+ l_num_full_rows' not '- l_num_full_rows' because under the current coordinate system
                    // the row index (y-coordinate) increases down the screen.
                    
                    if (f_tile_grid[col][row].GetColour() == f_colour_empty_tile)
                    { continue; }
                    
                    // Copy the colour of the tile at coordinate (col,row) to the appropriate row (row + l_num_full_rows).
                    f_tile_grid[col][row + l_num_full_rows].SetColour(f_tile_grid[col][row].GetColour());
                    
                    // Clear the colour of the tile at coordinate (col,row).
                    f_tile_grid[col][row].SetColour(f_colour_empty_tile);
                    
                    try 
                    { Thread.sleep(25); } 
                    catch (InterruptedException e) 
                    { e.printStackTrace(); }
                }
                
            }
            
        }
        
        // Return the number of full rows that were cleared.
        return l_num_full_rows;
    }
    
    /*
     * This method returns true if the given position/coordinate is both valid (within valid bounds) and empty; false if
       otherwise.
       
     * Parameters:
         > a_position: the position being checked.
    */
    public boolean CanBeMovedTo(Vector2D a_position)
    {
        return IsPositionOnBoard(a_position) && IsPositionEmpty(a_position);
    }
    
    /*
     * This method returns true if the given position/coordinate is valid (within valid bounds); false if otherwise.
     
     * Parameters:
         > a_position: the position being checked.
    */
    public boolean IsPositionOnBoard(Vector2D a_position)
    {
        return (a_position.GetX() >= 0 && a_position.GetX() <= f_num_columns - 1) && 
               (a_position.GetY() >= 0 && a_position.GetY() <= f_num_rows - 1);
    }
    
    /*
     * This method returns true if the given position/coordinate is empty; false if otherwise.
     
     * Parameters:
         > a_position: the position being checked.
    */
    public boolean IsPositionEmpty(Vector2D a_position)
    {
        return f_tile_grid[(int)a_position.GetX()][(int)a_position.GetY()].GetColour() == f_colour_empty_tile;
    }
    
    /*
     * This method draws the given tetromino at the given position 'type'.
     
     * Parameters:
         > a_tetromino: the tetromino to be drawn onto the grid.
         > a_draw_pos: the position 'type' at which the tetromino is to be drawn.
         
      * Return Value:
         > True is returned if the tetromino is successfully drawn; false if otherwise. 
    */
    public boolean DrawTetromino(Tetromino a_tetromino, DrawPosition a_draw_pos)
    {   
        Vector2D l_spawn_location;
        
        if (a_draw_pos == DrawPosition.CentreTop)
        {
            // The tetrominos' centre points should be in the second row (y coordinate is 1)
            // The tetrominos should be centred in the columns (round to the left).
            l_spawn_location = new Vector2D((f_num_columns - 1) / 2, 1);
        }
        else //if (a_spawn_pos == SpawnPosition.CentreMid)
        {
            l_spawn_location = new Vector2D((f_num_columns - 1) / 2, (f_num_rows) / 2);
        }
        
        a_tetromino.SetPosition(l_spawn_location);
        
        return DrawTetromino(a_tetromino);
    }
    
    /*
     * This method draws the given tetromino at its current position.
     
     * Parameters:
         > a_tetromino: the tetromino to be drawn onto the grid.
         
     * Return Value:
         > True is returned if the tetromino is successfully drawn; false if otherwise. 
    */
    public boolean DrawTetromino(Tetromino a_tetromino)
    {
        // Check if the position is invalid.
        boolean l_is_position_valid = true;
        for (Vector2D v : a_tetromino.GetPosition()) 
        {
            if (!CanBeMovedTo(v))
            {
                l_is_position_valid = false;
                break;
            }
        }
        
        // Return if the tetromino's position is invalid.
        if (!l_is_position_valid)
        { return false; }
        
        // Get the tetromino's colour.
        Color l_colour_of_tetromino = a_tetromino.GetColour();
        
        // Draw the tetromino.
        for (Vector2D v : a_tetromino.GetPosition()) 
        {
            f_tile_grid[(int)v.GetX()][(int)v.GetY()].SetColour(l_colour_of_tetromino);
        }
        
        return true;
        
    }
    
    /*
     * This method removes the given tetromino from the grid.
     
     * Parameters:
         > a_tetromino: the tetromino to be drawn onto the grid.
    */
    public void UnDrawTetromino(Tetromino a_tetromino)
    {
        // Check if the position is invalid.
        boolean l_is_position_valid = true;
        for (Vector2D v : a_tetromino.GetPosition()) 
        {
            if (!IsPositionOnBoard(v))
            {
                l_is_position_valid = false;
                break;
            }
        }
        
        // Return if the tetromino's position is invalid.
        if (!l_is_position_valid)
        { return; }
        
        // Draw the tetromino.
        for (Vector2D v : a_tetromino.GetPosition()) 
        {
            f_tile_grid[(int)v.GetX()][(int)v.GetY()].SetColour(f_colour_empty_tile);
        }
        
    }
    
    
    
// (e). Auxiliaries (1) ================================================================================================
    
    /* Auxiliary of Constructors
     * Initialises f_board.
      
     * Parameters:
         > a_height: the height (in pixels) of the grid.
         > a_are_top_rows_visible: a flag that, when true, indicates that the top two rows (index 0 and 1) are 
                                   'off-screen'.
    */
    private void SetGrid(int a_height, boolean a_are_top_rows_visible)
    {
        // The top two rows should not be on the screen, which helps to give the effect that the tiles are falling down
        // from above the screen.
        int l_num_rows_visible = a_are_top_rows_visible ? f_num_rows : f_num_rows - 2;
        
        /*
         * Determine the dimension (x*x) of each tile such that the height of the game's grid/board is approximately.
         
         * Working Out: 
           l_height_max = l_num_rows_visible*x + 2*l_num_rows_visible*(x*S_PADDING_PROPORTION)
               * 'x' is the unknown variable (the dimension of each tile).
               * 'l_num_rows_visible*x' is the total height of the (visible) tiles themselves.
               * 'x*S_PADDING_PROPORTION' is the size of the padding around each tile.
               * '2*l_num_rows_visible*(x*S_PADDING_PROPORTION)' is the total height of the padding.
           => l_height_max = x*l_num_rows_visible + x*2*l_num_rows_visible*S_PADDING_PROPORTION
           => l_height_max = x * (l_num_rows_visible + 2*l_num_rows_visible*S_PADDING_PROPORTION)
           => x = l_height_max / (l_num_rows_visible + 2*l_num_rows_visible*S_PADDING_PROPORTION)
        */
        int l_size_tile = (int)( (float)a_height / (l_num_rows_visible + 2*l_num_rows_visible*S_PADDING_PROPORTION) );
        
        // Calculate the padding size.
        int l_size_padding = (int)( l_size_tile * S_PADDING_PROPORTION );
        
        if (l_size_padding == 0)
        { l_size_padding = 1; }
        
        // Create a constraints object, which allows for the graphical elements to be arranged.
        GridBagConstraints l_constraints = new GridBagConstraints();
        
        // Modify the constraint's object such that there's padding around each tile.
        l_constraints.insets = new Insets(l_size_padding, l_size_padding, l_size_padding, l_size_padding);
        
        // Create the grid's container.
        f_tile_grid = new TetrisTile[f_num_columns][f_num_rows];
        
        // Populate the grid's container with tiles.
        for (int col = 0; col < f_num_columns; ++col)
        {   
            // Set the grid's horizontal coordinate.
            l_constraints.gridx = col;
            
            for (int row = 0; row < f_num_rows; ++row)
            {
                // Set the grid's vertical coordinate.
                l_constraints.gridy = row;
                
                // Instantiate the tile.
                f_tile_grid[col][row] = new TetrisTile(l_size_tile, f_colour_empty_tile);
                
                // Don't add the top two rows (the 0th and 1st row).
                if (!a_are_top_rows_visible && row <= 1)
                { continue; }
                
                // Add the tile to panel.
                super.add(f_tile_grid[col][row], l_constraints);
            }
        }
        
    }
    
    
}

