/*
 * BagADT.java
 *
 * Created on January 14, 2004, 9:39 AM
 *
 * Last Modified: January 19, 2011
 */

/**
 *
 * @author  leahy
 */
//********************************************************************
//  Defines the interface to a bag data structure.
//********************************************************************
import java.util.Iterator;

public interface BagADT<Object> {
   //  Adds one element to this bag
   public void add (Object element);

   //  Adds the contents of parameter to this bag.
   public void addAll (BagADT<Object> bag);

   //  Removes and returns a random element from this bag
   public Object removeRandom ();

   //  Removes and returns the specified element from this bag
   public Object remove (Object element);

   //  Returns the union of this bag and the parameter
   public BagADT<Object> union (BagADT<Object> set);

   //  Returns true if this bag contains the parameter
   public boolean contains (Object target);

   //  Returns true if this bag and the parameter contain exactly
   //  the same elements
   public boolean equals (BagADT<Object> bag);

   //  Returns true if this set contains no elements
   public boolean isEmpty();

   //  Returns the number of elements in this set
   public int size();

   //  Returns an iterator for the elements in this bag
   public Iterator iterator();

   //  Returns a string representation of this bag
  @Override
   public String toString();
}//end Interface Comp139BagADT

