
package Utils;


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;


/*
 * An instance of this class is a graphical rectangle that can be drawn onto the screen with text displayed across it.

 * Composition:
     (a). Enums (1)
     (b). Fields (6)
         (i). Static Fields (4)
     (c). Constructors (2)
     (d). Public Methods (4)
         (i). Accessors (1)
         (ii). Mutators (1)
*/
public class Label
    extends Canvas 
{
    
// (a). Enums (1) ======================================================================================================
    
    // Text alignment.
    public enum Alignment
    { Left, Centre }
    
    
    
// (b). Fields (6) =====================================================================================================
    
    /*
     * The text that's displayed upon the canvas. 
    */
    private String f_text;
    
    /*
     * The alignment of f_text upon the canvas. 
    */
    private Alignment f_align;
    
    
// (b)(i). Static Fields (4) -------------------------------------------------------------------------------------------
    
    // The size of the padding above and below the text/value.
    /*
     * The size (as a proportion of the label's width) of the padding to the left and right of the text.
    */
    private static final float s_padding_proportion_horizontal = 0.1f;
    
    /*
     * The size (as a proportion of the label's height) of the padding above and below the text.
    */
    private static final float s_padding_proportion_vertical = 0.1f;
    
    // The default background colour.
    private static final Color s_colour_back_default = Color.BLACK;
    
    // The default foreground/text colour.
    private static final Color s_colour_text_default = Color.WHITE;
    
    private static final long serialVersionUID = 1L;
    
    
    
// (c). Constructors (2) ===============================================================================================
    
    /*
    
     * Parameters:
         > a_text: the label's initial text.
         > a_align: the alignment of a_text upon the canvas.
         > a_width: the label's width (pixels).
         > a_height: the label's height (pixels).
         > a_colour_back: the canvas' colour (i.e. the background colour).
         > a_colour_text: the text's colour.
    */
    public Label(String a_text, Alignment a_align, int a_width, int a_height, Color a_colour_back, Color a_colour_text)
    {
        super();
        
        // Set size.
        super.setPreferredSize(new Dimension(a_width, a_height));
        
        // Set colours.
        super.setBackground(a_colour_back);
        super.setForeground(a_colour_text);

        // Set text.
        f_text = a_text;
        f_align = a_align;
    }
    
    /*
    
     * Parameters:
         > a_text: the label's initial text.
         > a_align: the alignment of a_text upon the canvas.
         > a_width: the label's width (pixels).
         > a_height: the label's height (pixels).
    */
    public Label(String a_text, Alignment a_align, int a_width, int a_height)
    {
        super();
        
        // Set size.
        super.setPreferredSize(new Dimension(a_width, a_height));
        
        // Set colours (use default colours).
        super.setBackground(s_colour_back_default);
        super.setForeground(s_colour_text_default);

        // Set text.
        f_text = a_text;
        f_align = a_align;
    }
    
    
    
// (d). Public Methods (4) ============================================================================================= 
    
    /*
     * This method is called to update the canvas' graphics.
    */
    @Override
    public void paint(Graphics g)
    {
        update(g);
    }
    
    /*
     * This method updates the canvas' graphics. 
    */
    @Override
    public void update(Graphics g)
    {
        // Draw the background.
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        
        // The horizontal and vertical padding.
        int l_padding_horizontal = (int)( super.getPreferredSize().width * s_padding_proportion_horizontal );
        int l_padding_vertical = (int)( super.getPreferredSize().height * s_padding_proportion_vertical );
        
        // The text's maximum width.
        int l_width_max = (int)( super.getPreferredSize().width - 2 * l_padding_horizontal );
        
        // The font's maximum height.
        int l_height_max = (int)( super.getPreferredSize().height - 2 * l_padding_vertical );

        // Print the text upon the canvas at the appropriate font.
        for (int height = l_height_max; height > 1; --height)
        {
            g.setFont(new Font("Arial", Font.PLAIN, height));         
            
            FontMetrics l_font_metrics = g.getFontMetrics();
            
            // The highest height of a character of the current font.
            int l_height_text = l_font_metrics.getAscent();
            
            // Check if the height is too high.
            if (l_height_text > l_height_max)
            { continue; }
            
            // The width of l_text_full under the current font.
            int l_width_text = l_font_metrics.stringWidth(f_text);
            
            // Check if the width is too wide.
            if (l_width_text > l_width_max)
            { continue; }
            
            // X and Y coordinate of the text.
            int l_x = 0;
            int l_y = 0;
            
            // Set the coordinates.
            if (f_align == Alignment.Left)
            {
                l_x = l_padding_horizontal;
                l_y = l_padding_vertical + l_height_text;
            }
            else if (f_align == Alignment.Centre)
            {
                l_x = l_padding_horizontal + (l_width_max - l_width_text) / 2;
                l_y = l_padding_vertical + l_height_text;
            }
            
            // Set the colour for l_text_string.
            g.setColor(getForeground());
            
            // Draw l_text_string.
            g.drawString(f_text, l_x, l_y);
            
            break;
        }

    }
    
    
// (d)(i). Accessors (1) -----------------------------------------------------------------------------------------------
    
    /* Accesor of f_text
    */
    public String GetText()
    {
        return f_text;
    }
    
    
// (d)(ii). Mutators (1) -----------------------------------------------------------------------------------------------
    
    /* Mutator of f_text
    */
    public void SetText(String a_text)
    {
        f_text = a_text;
        
        // Update the graphics to account for the change of text.
        super.repaint();
    }
    
    
}
