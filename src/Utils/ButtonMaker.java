
package Utils;


import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;


public class ButtonMaker 
{
    public static JButton CreateButton(String a_text, ActionListener a_listener, Font a_font)
    {
        return CreateButton(a_text, a_listener, null, a_font);
    }

    public static JButton CreateButton(String a_text, ActionListener a_listener, Dimension a_size, Font a_font)
    {
        JButton Result = new JButton(a_text);

        if (a_size != null)
        {
            Result.setPreferredSize(a_size);
        }

        if (a_font != null)
        {
            Result.setFont(a_font);            
        }
        
        Result.addActionListener(a_listener);
        
        return Result;
    }

    public static JButton CreateButton(String a_text, ActionListener aListener, Dimension aSize)
    {
        return CreateButton(a_text, aListener, aSize, null);
    }

    public static JButton CreateButton(String a_text, ActionListener aListener, int aHeight)
    {
        JButton Result = new JButton(a_text);
        Dimension lSize = Result.getPreferredSize();
        
        lSize.height = aHeight;
        
        Result.setPreferredSize(lSize);
        Result.setFont(new Font("Arial", Font.BOLD, 24));
        Result.addActionListener(aListener);
        
        return Result;
    }

    public static JButton CreateButton(String aTitle, ActionListener aListener)
    {
        return CreateButton(aTitle, aListener, null, null);
    }
    
    
}
