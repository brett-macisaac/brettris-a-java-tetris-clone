
package TetrisPackage;


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;


/*
 * An object of this class is essentially a square with a given colour and dimensions; as such, a collection of 
   TetrisTile objects can be used to form a Tetris grid.
   
 * Composition:
     (a). Constructors (1)
     (b). Public Methods (4)
         (i). Accessors (1)
         (ii). Mutators (1)
*/
public class TetrisTile
    extends Canvas
{
    private static final long serialVersionUID = 1L;

    
// (a). Constructors (1) ===============================================================================================
    
    /*
     
     * Parameters:
         > a_size: the tile's dimension (a_size*a_size).
         > a_color:
    */
    public TetrisTile(int a_size, Color a_color)
    {
        super();
        
        // Set dimensions.
        super.setPreferredSize(new Dimension(a_size, a_size));
        
        // Set color.
        super.setBackground(a_color);
    }
    
    
    
// (b). Public Methods (4) =============================================================================================
    
    /*
     * This method redraws the canvas onto the screen. 
    */
    public void paint(Graphics g)
    {
        update(g);
    }
    
    /*
     * This method draws the canvas onto the screen.
    */
    public void update(Graphics g)
    {
        // Double-buffering.
        //Graphics gg = DoubleBuffer.getGraphics(this);
        
        // Draw background.
        g.setColor(super.getBackground());
        g.fillRect(0, 0, super.getWidth(), super.getHeight());
       
        //DoubleBuffer.switchBuffer(g);
    }
    
    
// (b)(i). Accessors (1) -----------------------------------------------------------------------------------------------
    
    /* Accessor of [background colour]
    */
    public Color GetColour()
    {
        return super.getBackground();
    }
    
    
// (b)(ii). Mutators (1) -----------------------------------------------------------------------------------------------
    
    /* Mutator of [background colour]
    */
    public void SetColour(Color a_colour)
    {
        super.setBackground(a_colour);
        
        repaint();
    }
    
    
}