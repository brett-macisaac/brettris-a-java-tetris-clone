
package Utils;


import java.lang.Math;


/*
 * A two-dimensional vector class.
 
  * Composition:
     (a). Fields (6)
         (i). Static Fields (4)
     (b). Constructors (2)
     (c). Public Methods (11)
         (i). Accessors (2)
         (ii). Mutators (2)
*/
public class Vector2D
{
    
// (a). Fields (6) =====================================================================================================
    
    // The vector's x-coordinate.
    private float f_x;
    
    // The vector's  y-coordinate.
    private float f_y;
    
    
// (a)(i). Static Fields (4) -------------------------------------------------------------------------------------------
    
    /* Static Imnstances
     * These are static instances of the class that are useful for a variety of situations. Having static instances saves
       a programmer time be removing the necessity of creating instances of Vector2D that are commonly needed.
    */
    public static final Vector2D s_down = new Vector2D(0,-1);
    public static final Vector2D s_up = new Vector2D(0,1);
    public static final Vector2D s_left = new Vector2D(-1,0);
    public static final Vector2D s_right = new Vector2D(1,0);
    
    
    
// (b). Constructors (2) ===============================================================================================
    
    /*
     
     * Parameters:
         > a_x: The vector's x-coordinate.
         > a_y: The vector's y-coordinate.
    */
    public Vector2D(float a_x, float a_y)
    {
        f_x = a_x;
        
        f_y = a_y;
    }
    
    /*
    */
    public Vector2D()
    {
        f_x = 0;
        
        f_y = 0;
    }
    
    
    
// (c). Public Methods (11) ============================================================================================
    
    /*
     * This method returns the vector's magnitude. 
    */
    public float Magnitude()
    {
        return (float)( Math.sqrt( Math.pow((double)f_x, 2.0)) + Math.pow((double)f_y, 2.0) );
    }
    
    /*
     * This method calculates and returns the dot product of this vector and the one supplied. 
    */
    public float DotProduct(Vector2D a_rhs)
    {
        return f_x * a_rhs.f_x + f_y * a_rhs.f_y;
    }
    
    // += operator
    public void PlusEquals(Vector2D a_rhs)
    {
        f_x += a_rhs.f_x;
        
        f_y += a_rhs.f_y;
    }
    
    // -= operator.
    public void MinusEquals(Vector2D a_rhs)
    {
        f_x -= a_rhs.f_x;
        
        f_y -= a_rhs.f_y;
    }
    
    // + operator.
    public Vector2D Plus(Vector2D a_vector)
    {
        Vector2D l_sum = new Vector2D();
        
        l_sum.f_x = this.f_x + a_vector.f_x;
        l_sum.f_y = this.f_y + a_vector.f_y;
        
        return l_sum;
    }
    
    // - operator.
    public Vector2D Minus(Vector2D a_vector)
    {
        Vector2D l_sum = new Vector2D();
        
        l_sum.f_x = this.f_x - a_vector.f_x;
        l_sum.f_y = this.f_y - a_vector.f_y;
        
        return l_sum;
    }
    
    /*
     * This method returns the vector's string representation.
    */
    public String toString()
    {
        String l_str = "(";
        l_str.concat(String.valueOf(f_x));
        l_str.concat(", ");
        l_str.concat(String.valueOf(f_y));
        l_str.concat(")");
        
        return l_str;
    }
    
    
// (c)(i). Accessors (2) -----------------------------------------------------------------------------------------------
    
    /* Accesor of f_x
    */
    public float GetX()
    {
        return f_x;
    }
    
    /* Accesor of f_y
    */
    public float GetY()
    {
        return f_y;
    }
    
    
// (c)(ii). Mutators (2) -----------------------------------------------------------------------------------------------
    
    /* Mutator of f_x
    */
    public void SetX(float a_x)
    {
        f_x = a_x;
    }
    
    /* Mutator of f_y
    */
    public void SetY(float a_y)
    {
        f_y = a_y;
    }
    
    
}