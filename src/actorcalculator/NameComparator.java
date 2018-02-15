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
 * comparator for actor names
 * @author mike spad
 */
public class NameComparator implements Comparator {


    /**
     * compares two names
     * @param o1 the first object to be compared
     * @param o2 the second object to be compared
     * @return a negative number if the left is before the right, 0 if theyre equal, a positive if right is before the left
     */
    @Override
    public int compare(Object o1, Object o2) {
        Actor left = (Actor)(o1);
        Actor right = (Actor)(o2);
        
        return left.getName().compareTo(right.getName());
    }
    
}
