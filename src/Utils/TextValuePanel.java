/*
 *  SWE30001, 2021
 * 
 *  Number canvas
 * 
 */

package Utils;


import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;


/*
 * A GUI element to display text and a corresponding integer value.
 
  * Composition:
     (a). Fields (7)
         (i). Static Fields (5)
     (b). Constructors (2)
     (c). Public Methods (4)
         (i). Accessors (1)
         (ii). Mutators (3)
*/
public class TextValuePanel
    extends JPanel 
{   
    
// (a). Fields (7) =====================================================================================================
    
    private Label f_lbl_text;
    
    private Label f_lbl_value;
    
    
// (a)(i). Static Fields (5) -------------------------------------------------------------------------------------------
    
    // The size of the padding above and below the text/value.
    private static final float s_padding_proportion_horizontal = 0.1f;
    
    // The size of the padding to the left and right of the text/value.
    private static final float s_padding_proportion_vertical = 0.1f;
    
    // The default background colour.
    private static final Color s_colour_back_default = Color.white;
    
    private static final Color s_colour_labels_back_default = Color.BLACK;
    
    private static final Color s_colour_labels_font_default = Color.white;// Color(200, 200, 200);
    
    private static final long serialVersionUID = 1L;
    
    
    
// (b). Constructors (2) ===============================================================================================

    public TextValuePanel(String a_text , int a_value, int a_width, int a_height, 
                          Color a_color_back, Color a_color_labels, Color a_color_font)
    {
        super(new GridBagLayout());
        
        // The horizontal and vertical padding.
        int l_padding_horizontal = (int)( a_width * s_padding_proportion_horizontal );
        int l_padding_vertical = (int)( a_height * s_padding_proportion_vertical );
        
        // The width and height of each label.
        int l_height_labels = (a_height - 2*l_padding_vertical) / 2;
        int l_width_labels = a_width - 2*l_padding_horizontal;
        
        // Create a constraints object to help place the elements properly.
        GridBagConstraints l_constraints = new GridBagConstraints();
        
        // Create the text label.
        f_lbl_text = new Label(a_text + ":", Label.Alignment.Left, l_width_labels, l_height_labels);
        f_lbl_text.setBackground(a_color_labels);
        f_lbl_text.setForeground(a_color_font);
        
        // Create the value label.
        f_lbl_value = new Label(String.valueOf(a_value), Label.Alignment.Left, l_width_labels, l_height_labels);
        f_lbl_value.setBackground(a_color_labels);
        f_lbl_value.setForeground(a_color_font);
        
        // Add f_lbl_text to the panel.
        l_constraints.gridx = 0; l_constraints.gridy = 0; // (0,0)
        l_constraints.insets = new Insets(l_padding_vertical, l_padding_horizontal, 0, l_padding_horizontal);
        super.add(f_lbl_text, l_constraints);
        
        // Add f_lbl_value to the panel.
        l_constraints.gridx = 0; l_constraints.gridy = 1; // (0,1)
        l_constraints.insets = new Insets(0, l_padding_horizontal, l_padding_vertical, l_padding_horizontal);
        super.add(f_lbl_value, l_constraints);
        
        // Set panel's background colour.
        super.setBackground(a_color_back);
    }
    
    public TextValuePanel(String a_text , int a_value, int a_width, int a_height)
    {
        super(new GridBagLayout());
        
        // The horizontal and vertical padding.
        int l_padding_horizontal = (int)( a_width * s_padding_proportion_horizontal );
        int l_padding_vertical = (int)( a_height * s_padding_proportion_vertical );
        
        // The width and height of each label.
        int l_height_labels = (a_height - 2*l_padding_vertical) / 2;
        int l_width_labels = a_width - 2*l_padding_horizontal;
        
        // Create a constraints object to help place the elements properly.
        GridBagConstraints l_constraints = new GridBagConstraints();
        
        // Create the text label.
        f_lbl_text = new Label(a_text + ":", Label.Alignment.Left, l_width_labels, l_height_labels);
        f_lbl_text.setBackground(s_colour_labels_back_default);
        f_lbl_text.setForeground(s_colour_labels_font_default);
        
        // Create the value label.
        f_lbl_value = new Label(String.valueOf(a_value), Label.Alignment.Left, l_width_labels, l_height_labels);
        f_lbl_value.setBackground(s_colour_labels_back_default);
        f_lbl_value.setForeground(s_colour_labels_font_default);
        
        // Add f_lbl_text to the panel.
        l_constraints.gridx = 0; l_constraints.gridy = 0; // (0,0)
        l_constraints.insets = new Insets(l_padding_vertical, l_padding_horizontal, 0, l_padding_horizontal);
        super.add(f_lbl_text, l_constraints);
        
        // Add f_lbl_value to the panel.
        l_constraints.gridx = 0; l_constraints.gridy = 1; // (0,1)
        l_constraints.insets = new Insets(0, l_padding_horizontal, l_padding_vertical, l_padding_horizontal);
        super.add(f_lbl_value, l_constraints);
        
        // Set panel's background colour.
        super.setBackground(s_colour_back_default);
    }
    
    
    
// (c). Public Methods (4) ============================================================================================
    
    
// (c)(i). Accessors (1) -----------------------------------------------------------------------------------------------
    
    /* Accesor of f_lbl_value
     * This accessor returns the value of the label f_lbl_value.
    */
    public int GetValue()
    {
        return Integer.parseInt(f_lbl_value.GetText());
    }
    
    
// (c)(ii). Mutators (3) -----------------------------------------------------------------------------------------------
    
    /* Mutator of f_lbl_value
     * This mutator changes the value of the label f_lbl_value.
    */
    public void SetValue(int a_value)
    {
        f_lbl_value.SetText(String.valueOf(a_value));
    }
    
    /* Mutator of f_lbl_value
     * This mutator increments the value of the label f_lbl_value by one.
    */
    public void Increment() 
    {
        int l_value = Integer.parseInt(f_lbl_value.GetText()) + 1;
        
        SetValue(l_value);
    }
    
    /* Mutator of f_lbl_value
     * This mutator decrements the value of the label f_lbl_value by one.
    */
    public void Decrement() 
    {
        int l_value = Integer.parseInt(f_lbl_value.GetText()) - 1;
        
        SetValue(l_value);
    }
    
    
}
