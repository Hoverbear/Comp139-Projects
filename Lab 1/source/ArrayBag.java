/*
 * ArrayBag.java
 *
 * Created on January 24, 2011
 *
 * Last Modified: January 30, 2011
 *
 *
 * @author  Andrew Hobden
 */
//********************************************************************
//  ArrayBag class and auxillary methods allowing for the smooth
//   operation of the MemoryGame.class file.
//********************************************************************

import java.util.*;

public class ArrayBag implements BagADT {

    private Object[] aBag;
    private int currentCapacity;
    private int position = 0;
    private Random random = new Random();
    private int modCount = 0;

    /**
     * This ArrayBag is initialized to empty. The maximum number of elements is set to DEFAULT_CAPACITY (100). .
     *
     */
    public ArrayBag() {
        currentCapacity = 100;
        aBag = new Object[currentCapacity];
    }

    /**
     * This ArrayBag is initialized to empty. The maximum number of elements is set to initialCapacity which is provided by the caller.
     *
    @param  initialCapacity  The maximum number of elements.
     *
     */
    public ArrayBag(int initialCapacity) {
        currentCapacity = initialCapacity;
        aBag = new Object[initialCapacity];
    }

    /**
     * Adds element to this bag. The capacity of this ArrayBag is expanded (doubled) if necessary.
     * It modifies the bag and thus breaks running iterators.
     *
    @param  element  The object to be added to this bag.
     *
     */
    public void add(Object element) {
        //throw new UnsupportedOperationException("Not supported yet.");
        modCount++; // Increase our modCount for the iterator
        if (position == aBag.length) {
            doubleBagSize();
        }
        aBag[position] = element;
        position++; // Move forward one spot
    }

    /**
     * Adds the contents of parameter bag to this bag. The capacity of this ArrayBag is expanded (doubled) if necessary.
     * It modifies the bag and thus breaks running iterators.
     *
    @param  bag  The BagADT object to be added to this bag.
     *
     */
    public void addAll(BagADT bag) {
        //throw new UnsupportedOperationException("Not supported yet.");
        modCount++; // Increase our modCount for the iterator
        Iterator bagIter = bag.iterator();
        while (bagIter.hasNext()) {
            add(bagIter.next());
        }
    }

    /**
     * Removes a random element from this bag. An EmptyBagException is thrown if this ArrayBag is empty.
     * It modifies the bag and thus breaks running iterators.
     *
    @exception ArrayBag.EmptyBagException The bag is empty.
     *
    @return    The random removed object.
     *
     */
    public Object removeRandom() throws EmptyBagException {
        //throw new UnsupportedOperationException("Not supported yet.");
        modCount++; // Increase our modCount for the iterator
        if (isEmpty()) {
            throw new EmptyBagException("You borked it with an empty bag.");
        }
        int removedSpot = random.nextInt(position);
        Object removed = aBag[removedSpot];
        rearrangeBag(removedSpot);
        return removed;
    }

    /**
     * Removes an occurrence of the specified element from this bag. An EmptyBagException is thrown if this ArrayBag is empty. A NoSuchElementException is thrown if the target is not in the bag.
     * It modifies the bag and thus breaks running iterators.
     *
    @param  element  The element to be removed from this bag.
     *
    @exception java.util.NoSuchElementException Specified element does not exist.
     *
    @exception ArrayBag.EmptyBagException The bag is empty.
     *
    @return   The specified element, if it exists.
     *
     */
    public Object remove(Object element) throws EmptyBagException, java.util.NoSuchElementException {
        //throw new UnsupportedOperationException("Not supported yet.");
        modCount++; // Increase our modCount for the iterator
        if (isEmpty()) {
            throw new EmptyBagException("You borked it with an empty bag.");
        } else if (contains(element)) {
            int i = 0;
            boolean found = false;
            while (i < aBag.length && found != true) {
                if (aBag[i].equals(element)) {
                    rearrangeBag(i);
                    found = true;
                }
                i++;
            }
        } else {
            throw new java.util.NoSuchElementException("Can't find " + element);
        }
        return element;
    }

    /**
     * Returns a new ArrayBag that is the union of this ArrayBag and the parameter bag.
     * It modifies the bag and thus breaks running iterators.
     *
    @param  set  The bag to be joined with the current.
     *
    @return The joined bag.
     */
    public BagADT union(BagADT set) {
        //throw new UnsupportedOperationException("Not supported yet.");
        modCount++; // Increase our modCount for the iterator
        ArrayBag tBag = new ArrayBag(size() + set.size());
        Iterator thisIter = iterator();
        Iterator setIter = set.iterator();
        while (thisIter.hasNext()) {
            tBag.add(thisIter.next());
        }
        while (setIter.hasNext()) {
            tBag.add(setIter.next());
        }
        return tBag;
    }

    /**
     * Returns true if this ArrayBag contains the specified target element, false if not.
     *
    @param  target  The object to be searched for.
     *
    @return    <code>true</code> if the object was found.
     *         <code>false</code> if it was not.
     */
    public boolean contains(Object target) {
        boolean result = false;
        for (int i = 0; i < position; i++) {
            if (aBag[i].equals(target)) {
                return true;
            }
        }
        return result;
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Returns true if this ArrayBag contains exactly the same elements as the parameter bag, false if not.
     *
    @param  bag  The bag to compare the current with.
     *
    @return    <code>true</code> if they are equal.
     *         <code>false</code> if it was not.
     */
    public boolean equals(BagADT bag) {
        boolean result = true;
        if (bag.size() == size()) {
            ArrayBag dBag = new ArrayBag(bag.size());
            dBag.addAll(bag);
            Iterator iter = iterator();
            while (iter.hasNext()) {
                try {
                    dBag.remove(iter.next());
                } catch (java.util.NoSuchElementException e) {
                    result = false;
                } catch (EmptyBagException e) {
                    result = false;
                }
            }
        } else {
            result = false;
        }
        return result;
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Returns true if this ArrayBag is empty, false otherwise.
     *
    @return    <code>true</code> if empty
     *         <code>false</code> if it was not.
     */
    public boolean isEmpty() {
        return (position == 0);
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Returns the number of elements currently in this ArrayBag.
     *
    @return    The size of the arrayBag.
     */
    public int size() {
        int size = 0;
        for (int i = 0; i < aBag.length; i++) {
            if (aBag[i] != null) {
                size++;
            }
        }
        return size;
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Doubles the current bag's size, returning the new size.
     *
     *
    @return    The new size.
     */
    private int doubleBagSize() {
        modCount++; // Increase our modCount for the iterator
        Object[] tBag = new Object[currentCapacity * 2];
        position = 0;
        for (int i = 0; i < aBag.length; i++) {
            tBag[i] = aBag[i];
            position++;
        }
        aBag = tBag;
        return aBag.length;
    }

    /**
     * "Fixes" the current bag so it's elements are properly placed.
     * Elements should fill from 0 to <code>position</code>.
     *
     *
    @param  removedSpot The current empty spot in the bag.
     */
    private void rearrangeBag(int removedSpot) {
        modCount++; // Increase our modCount for the iterator
        //for (int pos = removedSpot; pos < position; pos++) {
        for (int i = removedSpot; i < size() - 1; i++) {
            aBag[i] = aBag[i + 1];
        }
        aBag[size() - 1] = null;
        position--; // Move back one spot

    }

    /**
     * Returns a reference to an iterator object for the elements in this ArrayBag. 
     *
    @return The new iterator.
     */
    public Iterator iterator() {
        return new bagIterator();
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    private class bagIterator implements Iterator {

        private int curPos;
        private int expectedModCount = modCount;

        /**
         * Determines whether the bag has any objects that have not been iterated.
         *
         *
        @return     <code>true</code> If there is.
         *         <code>false</code> If there is not.
         */
        public boolean hasNext() {
            boolean hasNext = (curPos < position);
            return hasNext;
        }

        /**
         * Returns the next object to be iterated from the bag.
         *
        @exception ConcurrentModicationException The bag has been modified since iterator creation.
         *
        @exception NoSuchElementException The bag does not have more elements.
         *
        @return     The next object in the bag.
         */
        public Object next() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModicationException(
                        "Cannot mutate in context of iterator");
            }
            if (!hasNext()) {
                throw new NoSuchElementException(
                        "There are no more elements");
            }
            curPos++;
            return aBag[curPos - 1];
        }

        /**
         * Unsupported. Do not use.
         *
         */
        public void remove() {
            throw new UnsupportedOperationException(
                    "remove not supported by Bag");
        }
    }

    private static class EmptyBagException extends RuntimeException {

        /**
         * An exception occuring when the bag is empty.
         *
         */
        public EmptyBagException() {
            super("Error: The Bag is empty.");
        }

        /**
         * An exception occuring when the bag is empty.
         *
        @param  string  The error string.
         *
         */
        public EmptyBagException(String string) {
            super(string);
        }
    }

    private static class ConcurrentModicationException extends RuntimeException {

        /**
         * An exception occuring when there are concurrent modifications.
         *
         */
        public ConcurrentModicationException() {
            super("Error: Concurrent Modification.");
        }

        /**
         * An exception occuring when there are concurrent modifications.
         *
        @param  string  The error string.
         *
         */
        public ConcurrentModicationException(String string) {
            super(string);
        }
    }
}
