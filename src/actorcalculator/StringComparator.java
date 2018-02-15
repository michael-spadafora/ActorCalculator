/*
 * Michael Spadafora
 * Michael.spadafora@stonybrook.edu
 * ID:110992992
 * Homework #7
 * cse214 Recitation section 8
 * Recitation TA: Michael P Rizzo
 * Grading TA: Timothy Zhang
 */
package actorcalculator;

import java.util.Comparator;

/**
 * comparator for Strings
 * @author mike spad
 */
public class StringComparator implements Comparator {



      /**
     * compares two Strings
     * @param o1 the first object to be compared
     * @param o2 the second object to be compared
     * @return a negative number if the left is before the right, 0 if theyre equal, a positive if right is before the left
     */
    @Override
    public int compare(Object o1, Object o2) {
        String s1 = (String)(o1);
        String s2 = (String)(o2);
        return s1.compareTo(s2);
        
    }
        
        
    
    
}
