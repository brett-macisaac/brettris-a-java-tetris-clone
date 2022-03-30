
package Utils;


import java.util.Random;


/*
 * A class whose (static) methods provide random integers between a given range.
 
 * Class Composition:
     (a). Fields (1)
         (i). Static Fields (1)
     (b). Static Methods (2)
*/
public class RNG
{
    
// (a). Fields (1) =====================================================================================================
    
    
// (a)(i). Static Fields (1) -------------------------------------------------------------------------------------------
    
    /*
     * The random number generator. 
    */
    private static Random l_rng = new Random();
    
    
    
// (b). Static Methods (2) =============================================================================================
    
    /*
     * This method returns a random integer in the range [a_min, a_max].
     
     * Parameters:
         > a_min: the minimum (inclusive) value of the range.
         > a_max: the maximum (inclusive) value of the range.
    */
    public static int RandomInt(int a_min, int a_max)
    {
        // The amount of integers in the range (inclusive of both a_min and a_max).
        int l_range_magnitude = a_max - a_min + 1;
        
        return l_rng.nextInt(l_range_magnitude) + a_min;
    }
    
    /*
     * This method returns a random integer in the range [0, a_max].
     
     * Parameters:
         > a_max: the maximum (inclusive) value of the range.
    */
    public static int RandomInt(int a_max)
    {   
        return l_rng.nextInt(a_max + 1);
    }
    
    
}