
package TetrisPackage;


import java.awt.Color;

import Utils.Vector2D;


/* Tetromino Class
 * Each object of this class is a Tetris tetromino piece.
 
 * Composition:
     (a). Enums (1)
     (b). Fields (17)
         (i). Static Fields (14)
     (c). Constructors (1)
     (d). Public Methods (9)
         (i). Accessors (2)
         (ii). Mutators (1)
     (e). Auxiliaries (3)
*/
public class Tetromino
{
    
// (a). Enums (1) ======================================================================================================
    
    // An enum for the seven types of tetrominos.
    public enum Type
    { I, J, L, O, S, T, Z }
    
    
    
// (b). Fields (17) ====================================================================================================
    
    // The tetromino's type.
    private Type f_type;
    
    /*
     * The positions of each tile.
     * f_positions[0] is the position of the tile around which the others rotate.
    */
    Vector2D f_positions[];
    
    /*
     * The value that indicates the current rotation state of the tetromino.
     * There are four rotation states, represnted by the indexes 0, 1, 2, and 3: i.e. a tetromino can only be rotated by
       90 degrees at a time.
    */
    private int f_index_rotation;
    
    
// (b)(i). Static Fields (14) ------------------------------------------------------------------------------------------
    
    // Each tetromino is composed of four tiles (size of f_positions).
    public static final int S_NUM_TILES = 4;
    
    // The number of rotation indexes (0 to 3) (i.e. the number of values f_index_rotation can take).
    private static final int S_NUM_ROTATION_INDEXES = 4;
    
    // The colours corresponding to each Type of tetromino.
    private static final Color S_COLOUR_I = new Color(0,235,235); 
    private static final Color S_COLOUR_J = new Color(0,0,255); 
    private static final Color S_COLOUR_L = new Color(255,128,0); 
    private static final Color S_COLOUR_O = new Color(235,235,0); 
    private static final Color S_COLOUR_S = new Color(0,235,0); 
    private static final Color S_COLOUR_T = new Color(235,0,235); 
    private static final Color S_COLOUR_Z = new Color(235,0,0);
    
    // Offsets for the J, L, S, T, and Z tetrominos.
    private static final Vector2D s_offset_data_JLSTZ[][] = new Vector2D[][]
    {  
        { new Vector2D(0,0), new Vector2D(0,0),  new Vector2D(0,0), new Vector2D(0,0) },  // Offset 1
        { new Vector2D(0,0), new Vector2D(1,0),  new Vector2D(0,0), new Vector2D(-1,0) }, // Offset 2
        { new Vector2D(0,0), new Vector2D(1,1), new Vector2D(0,0), new Vector2D(-1,1) }, // Offset 3
        { new Vector2D(0,0), new Vector2D(0,-2),  new Vector2D(0,0), new Vector2D(0,-2) }, // Offset 4
        { new Vector2D(0,0), new Vector2D(1,-2),  new Vector2D(0,0), new Vector2D(-1,-2) } // Offset 5
    };
    
    // Offsets for the I tetromino.
    private static final Vector2D s_offset_data_I[][] = new Vector2D[][]
    {  
        { new Vector2D(0,0),  new Vector2D(-1,0), new Vector2D(-1,-1), new Vector2D(0,-1) }, // Offset 1
        { new Vector2D(-1,0), new Vector2D(0,0),  new Vector2D(1,-1),  new Vector2D(0,-1) }, // Offset 2
        { new Vector2D(2,0),  new Vector2D(0,0),  new Vector2D(-2,-1), new Vector2D(0,-1), }, // Offset 3
        { new Vector2D(-1,0), new Vector2D(0,-1),  new Vector2D(1,0),  new Vector2D(0,1) }, // Offset 4
        { new Vector2D(2,0),  new Vector2D(0,2), new Vector2D(-2,0), new Vector2D(0,-2) }  // Offset 5
    };
    
    // Offset for the O tetromino.
    private static final Vector2D s_offset_data_O[][] = new Vector2D[][]
    { 
        //{new Vector2D(0,0), new Vector2D(0,-1), new Vector2D(-1,-1), new Vector2D(-1,0) } // Offset 1
        {new Vector2D(0,0), new Vector2D(0,1), new Vector2D(-1,1), new Vector2D(-1,0) }
    };
    
    // Clockwise rotation matrix.
    private static final Vector2D s_matrix_rot_ccw[] = new Vector2D[]
    { 
        new Vector2D( 0, -1), // Column 1
        new Vector2D( 1,  0)  // Column 2
    };
    
    // Anti-clockwise rotation matrix.
    private static final Vector2D s_matrix_rot_cw[] = new Vector2D[]
    { 
        new Vector2D( 0, 1), // Column 1
        new Vector2D(-1, 0)  // Column 2
    };
    
    
    
// (c). Constructors (1) ===============================================================================================
    
    /* Constructor
     
     * Parameters:
         > a_type: the tetromino's type.
    */
    public Tetromino(Type a_type)
    {
        f_positions = new Vector2D[S_NUM_TILES];
        
        f_index_rotation = 0;
        
        f_type = a_type;
    }
    
    
    
// (d). Public Methods (9) =============================================================================================
    
    /* Rotation Method
     * This method rotates the tetromino in the given direction.
     * The Super Rotation System (SRS) is used. For more information on how this system works, particularly the offsets 
       element, see https://tetris.fandom.com/wiki/SRS.
    
     * Parameters:
         > a_clockwise: the direction of rotation.
         > a_try_offsets: a flag that, when true, indicates that the piece should be offset in the event that it cannot 
                          be directly rotated into a valid position.
         > a_grid: the grid on which the tetromino is displayed.
         
     * Return Value:
         > A boolean indicating whether or not the tetromino was successfully rotated. 
                           
    */
    public boolean Rotate(boolean a_clockwise, boolean a_try_offsets, TetrisGrid a_grid)
    {
        if (a_try_offsets)
        { a_grid.UnDrawTetromino(this); }
        
        // The current rotation index (pre-rotation).
        int l_index_rotation_old = f_index_rotation;
        
        // Increment/decrement f_index_rotation according to a_clockwise.
        if (a_clockwise)
        {
            f_index_rotation = (f_index_rotation + 1) % S_NUM_ROTATION_INDEXES;
        }
        else
        {
            f_index_rotation = (f_index_rotation == 0) ? S_NUM_ROTATION_INDEXES - 1 : f_index_rotation - 1;
        }
        
        for (int i = 0; i < f_positions.length; ++i)
        {
            RotateTilePosition(0, i, a_clockwise);
        }
        
        if (!a_try_offsets)
        { return false; }
        
        // Try to find a valid placement for the tetromino by using the offset data (record result in l_is_rotation_possible).
        boolean l_is_rotation_possible = OffSet(l_index_rotation_old, f_index_rotation, a_grid);
        
        // If the tetromino can't be rotated (even after trying all available offsets), rotate back to the original 
        // position.
        if (!l_is_rotation_possible)
        {
            Rotate(!a_clockwise, false, a_grid);
        }
        
        a_grid.DrawTetromino(this);
        
        return l_is_rotation_possible;
    }
    
    /* Movement Method
     * This method moves the tetromino by the given movement vector.
     * Note that, assuming that the tetromino can be moved, the position on the grid is updated.
     
     * Parameters:
         > a_movement: the vector that defines the movement.
         > a_grid: the grid on which the tetromino is to be moved.
         > a_update_grid: a flag that, when true, indicates that the grid is to be updated: i.e. the tetromino should 
                          actually be moved on the grid, as opposed to just its position array being altered.

     * Return Value:
         > This method returns true if the tetromino was successfully moved; false if otherwise.
    */
    public boolean Move(Vector2D a_movement, TetrisGrid a_grid, boolean a_update_grid)
    {
        if (a_update_grid)
        { a_grid.UnDrawTetromino(this); }
        
        boolean l_can_move = true;
        
        for (int i = 0; i < f_positions.length; ++i)
        {
            if (!CanMoveTilePosition(i, a_movement, a_grid))
            {
                //System.out.println("Invalid movement!");
                l_can_move = false;
                break;
            }
        }
        
        if (l_can_move)
        {
            for (int i = 0; i < f_positions.length; ++i)
            {
                f_positions[i].PlusEquals(a_movement);
            }
        }
        
        if (a_update_grid)
        { a_grid.DrawTetromino(this); }
        
        return l_can_move;
    }
    
    /*
     * This method returns true if the tetromino can be moved in the given direction; false if otherwise. 
    */
    public boolean CanMove(Vector2D a_movement, TetrisGrid a_grid)
    {
        for (int i = 0; i < f_positions.length; ++i)
        {
            if (!CanMoveTilePosition(i, a_movement, a_grid))
            {
                //System.out.println("Invalid movement!");
                return false;
            }
        }
        
        return true;
    }
    
    /*
     * This method returns the colour associated with the tetromino's type. 
    */
    public Color GetColour()
    {
        switch (f_type)
        {
            case I :
                return S_COLOUR_I;
                
            case J :
                return S_COLOUR_J;
                
            case L :
                return S_COLOUR_L;
                
            case O :
                return S_COLOUR_O;
                
            case S :
                return S_COLOUR_S;
                
            case T :
                return S_COLOUR_T;
                
            case Z :
                return S_COLOUR_Z;
                
            default:
                return Color.WHITE;
        }
        
    }
    
    
// (d)(i). Accessors (2) -----------------------------------------------------------------------------------------------
    
    /* Accessor of f_type
    */
    public Type GetType()
    {
        return f_type;
    }
    
    /* Accessor of f_position.
    */
    public Vector2D[] GetPosition()
    {
        return f_positions;
    }
    
    
// (d)(ii). Mutators (1) -----------------------------------------------------------------------------------------------
    
    /* Mutator of f_positions
     * This method sets the tetromino's position in accordance with the given coordinate.
     
     * Parameters:
         > a_pos: the coordinate at which the tetromino's origin coordinate (f_positions[]) is set.
    */
    public void SetPosition(Vector2D a_pos)
    {
        // Set the position of the 'centre piece'.
        f_positions[0] = a_pos;
        
        // Set the positions of the other (three) tiles.
        switch (f_type)
        {
            case I :
                // |/||/||/||/|
                //  1  0  3  2
                f_positions[1] = f_positions[0].Plus(new Vector2D(-1,0));
                f_positions[2] = f_positions[0].Plus(new Vector2D(2,0));
                f_positions[3] = f_positions[0].Plus(new Vector2D(1,0));
                break;
                
            case J :
                // |/|
                //  2
                // |/||/||/|
                //  1  0  3
                f_positions[1] = f_positions[0].Plus(new Vector2D(-1,0));
                f_positions[2] = f_positions[0].Plus(new Vector2D(-1,-1));
                f_positions[3] = f_positions[0].Plus(new Vector2D(1,0));
                break;
                
            case L :
                //       |/|
                //        2
                // |/||/||/|
                //  3  0  1
                f_positions[1] = f_positions[0].Plus(new Vector2D(1,0));
                f_positions[2] = f_positions[0].Plus(new Vector2D(1,-1));
                f_positions[3] = f_positions[0].Plus(new Vector2D(-1,0));
                break;
                
            case O :
                // |/||/|
                //  3  2
                // |/||/|
                //  0  1 
                f_positions[1] = f_positions[0].Plus(new Vector2D(1,0));
                f_positions[2] = f_positions[0].Plus(new Vector2D(1,-1));
                f_positions[3] = f_positions[0].Plus(new Vector2D(0,-1));
                break;
                
            case S :
                //    |/||/|
                //     2  3
                // |/||/|
                //  1  0 
                f_positions[1] = f_positions[0].Plus(new Vector2D(-1,0));
                f_positions[2] = f_positions[0].Plus(new Vector2D(0,-1));
                f_positions[3] = f_positions[0].Plus(new Vector2D(1,-1));
                break;
                
            case T :
                //    |/|
                //     2 
                // |/||/||/|
                //  1  0  3
                f_positions[1] = f_positions[0].Plus(new Vector2D(-1,0));
                f_positions[2] = f_positions[0].Plus(new Vector2D(0,-1));
                f_positions[3] = f_positions[0].Plus(new Vector2D(1,0));
                break;
                
            case Z :
                // |/||/|
                //  2  1
                //    |/||/|
                //     0  3
                f_positions[1] = f_positions[0].Plus(new Vector2D(0,-1));
                f_positions[2] = f_positions[0].Plus(new Vector2D(-1,-1));
                f_positions[3] = f_positions[0].Plus(new Vector2D(1,0));
                break;
            
        }
        
    }
    
    
    
// (e). Auxiliaries (3) ================================================================================================
    
    /* Auxiliary of Rotate
     * Rotates a coordinate in f_positions.
     
     * Parameters:
         > a_index_origin: the index of f_positions corresponding to the coordinate that is considered the tetromino's 
                           origin/centre.
         > a_index_pos: the index of f_positions of the coordinate to be rotated about the coordinate at a_index_origin.
         > a_clockwise: a flag that, when true, indicates that the coordinate should be rotated clockwise.
    */
    private void RotateTilePosition(int a_index_origin, int a_index_pos, boolean a_clockwise)
    {
        // The position of f_positions[a_index_pos] relative to f_positions[a_index_origin].
        Vector2D l_position_relative = f_positions[a_index_pos].Minus(f_positions[a_index_origin]);
        
        // The new position associated with index a_index_pos.
        Vector2D l_position_new = new Vector2D();
        
        // The rotation matrix necessary to 
        Vector2D l_matrix_rot[] = a_clockwise ? s_matrix_rot_cw : s_matrix_rot_ccw;
        
        // Rotate the X position of l_position_relative (store result in l_position_new).
        l_position_new.SetX( (l_matrix_rot[0].GetX() * l_position_relative.GetX()) + 
                             (l_matrix_rot[1].GetX() * l_position_relative.GetY()) );
        
        // Rotate the Y position of l_position_relative (store result in l_position_new).
        l_position_new.SetY( (l_matrix_rot[0].GetY() * l_position_relative.GetX()) + 
                             (l_matrix_rot[1].GetY() * l_position_relative.GetY()) );
        
        // Make l_position_new relative to the universal origin, not the relative origin f_positions[a_index_origin].
        l_position_new.PlusEquals(f_positions[a_index_origin]);
        
        // Set the new position.
        f_positions[a_index_pos] = l_position_new;
    }
    
    /* Auxiliary of Rotate
     * This method offsets the tetromino's position in accordance to the old and new rotation indexes.
     
     * Parameters:
         > a_index_rotation_old: the rotation index prior to rotation.
         > a_index_rotation_new: the rotation index post rotation (i.e. the current rotation index).
         > a_grid: the grid on which the tetromino is displayed.
     
     * Return Value:
         * This method returns true if the piece was successfully offset into a new position; false if otherwise. 
    */
    private boolean OffSet(int a_index_rotation_old, int a_index_rotation_new, TetrisGrid a_grid)
    {
        // The offset vector for l_index_rotation_old, l_index_rotation_new, and the relative offset, respectively.
        Vector2D l_offset_old, l_offset_new, l_offset_relative;
        
        // The offset data used to determine the values of the above offsets.
        Vector2D l_offset_data[][];
        
        if (f_type == Type.O)
        { l_offset_data = s_offset_data_O; }
        else if (f_type == Type.I)
        { l_offset_data = s_offset_data_I; }
        else
        { l_offset_data = s_offset_data_JLSTZ; }
        
        // A flag that, when true, indicates that none of the offsets lead to a valid placement.
        boolean l_can_offset = false;
        
        for (int index_offset = 0; index_offset < 5; ++index_offset)
        {
            // Get the offset vector for index_offset associated with l_index_rotation_old and l_index_rotation_new, respectively.
            l_offset_old = l_offset_data[index_offset][a_index_rotation_old];
            l_offset_new = l_offset_data[index_offset][a_index_rotation_new];
            
            // Calculate the relative offset between the old and new rotation indexes.
            l_offset_relative = l_offset_old.Minus(l_offset_new);
            
            if (Move(l_offset_relative, a_grid, false))
            {
                return true;
            }
        }
        
        return false;
    }
    
    /* Auxiliary of Move
     * If f_positions[a_index_pos] can be moved according to a_movement, true is returned; otherwise, false.
      
     * Parameters:
         > a_index_pos: the index of the coordinate in f_positions that is to be moved.
         > a_movement: the way in which to move the coordinate.
         > a_grid: the grid on which the tetromino is displayed.
         
      * Return Value:
         * This method returns true if the coordinate can be moved; false if otherwise.  
         
    */
    private boolean CanMoveTilePosition(int a_index_pos, Vector2D a_movement, TetrisGrid a_grid)
    {
        Vector2D l_position_post_move = f_positions[a_index_pos].Plus(a_movement);
        
        return a_grid.CanBeMovedTo(l_position_post_move);
    }

    
}
