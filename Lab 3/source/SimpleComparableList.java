/*
 * SimpleComparableList.java
 *
 * Created Febuary 8, 2011
 *
 * Last Modified: March 3, 2011
 *
 * @author Andrew Hobden
 *
 */
//*****************************************************
// SimpleComparableList class and auxilary methods
// that provide smooth operation for COMP139Lab2Driver
//*****************************************************

import java.util.*;

public class SimpleComparableList {

    protected int currentPosition;
    protected int maxElements;
    protected int head;
    protected int freeHead;
    protected int modCount; // for iterator
    protected Comparable[] data;
    protected int[] links;
    protected int[] freeList;

    /**
     * Default constructor.
     * The list is initialized to empty.
     * The maximum number of elements is set at the default value (10).
     * The current list size is set to zero and the current position is set to one.
     *
     */
    public SimpleComparableList() {
        init(10);
    }

    /**
     * Constructs and initializes a list with a caller specified maximum size.
     * The list is initialized to empty.
     * The current list size is set to zero and the current position is set to one.
     *
    @param size The maximum size of this list.
     *
     */
    public SimpleComparableList(int size) {
        init(size);
    }

    /**
     * Shared initialization expressions for constructors.
     *
    @param size The maximum size of the list
     *
     */
    public void init(int size) {
        currentPosition = 1;
        maxElements = size;
        head = -1;
        freeHead = 0;
        data = new Comparable[size];
        links = new int[size];
        freeList = new int[size];
        // Init freelist
        for (int i = 0; i < freeList.length - 1; i++) {
            freeList[i] = i + 1;
        }
        freeList[size - 1] = -1;

    }

    /**
     * Determines if this list is full.
     *
    @return true if the list is currently full, false otherwise.
     *
     */
    public boolean full() {
        boolean result = false;
        int step = 0;
        int i = head;
        while (i != -1) {
            i = links[i];
            step++;
        }
        if (step == maxElements) {
            result = true;
        }
        return result;
    }

    /**
     * Determines if this list is empty.
     *
    @return true if the list is currently empty, false otherwise.
     *
     */
    public boolean empty() {
        return (head == -1);
    }

    /**
     * Inserts item into the list at the current position.
     * If an item occupied the current position before this call, that item will be the new item's successor after the call.
     * The lists current position does not change. The first item in the list is defined to be at position 1 (one) not at position zero (0).
     * To insert an item after the last item in the list (i.e. insert an item which will become the last item and will not have a successor) you must use insertItem(Comparable, int)
     *
    @param item The item to be inserted.
     *
    @throws SimpleComparableListException if the list is full.
     *
     */
    public void insertItem(java.lang.Comparable item) throws SimpleComparableListException {
        try {
            insertItem(item, currentPosition);
        } catch (SimpleComparableListException e) {
            throw new SimpleComparableListException("List is full!");
        }
    }

    /**
     * Inserts item into the list at the specified position.
     * If the list is empty the new node becomes the first node.
     * If position is greater than the current list size, the new node becomes the last node.
     * If an item already occupied the specified position before this call, that item will be the new item's successor after the call.
     * Before returning, the current position is updated to item's position in the list.
     * The current position will not be greater than the current list size.
     *
    @param item The item to be inserted.
     *
    @param position The position in the list that the new item will occupy after insertion.
     *
    @throws SimpleComparableListException if the list is full.
     *
     */
    public void insertItem(java.lang.Comparable item, int position) throws SimpleComparableListException {
        modCount++;
        if (full()) {
            throw new SimpleComparableListException("List is full!");

        }
        //Start add - Note no error checking
        if (freeHead != -1) {
            int location = freeHead;
            freeHead = freeList[freeHead];
            data[location] = item;
            if (position == 1) {
                links[location] = head;
                head = location;
            } else {
                int probe = findPrev(position);
                links[location] = links[probe];
                links[probe] = location;
            }
        }
        if (position < getCurrentListSize()) {
            currentPosition = position;
        } else {
            currentPosition = getCurrentListSize();
        }
    }

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     * This implementation returns a straightforward implementation of the
     * iterator interface, relying on the backing list's size()and getItem()
     * methods.
     * Note that the iterator returned by this method will throw an
     * UnsupportedOperationException in response to its remove method.
     * This implementation throws a SimpleComparableListException exceptions
     * in the case of concurrent modification.
     *
    @returns The iterator.
     *
     */
    public java.util.Iterator iterator() {
        return (new listIterator());
    }

    /**
     * The item at the current position is removed from the list.
     * If the removed item had a successor then that successor will be at the current position after the removal.
     * The current position is not changed unless the list becomes empty (in that case current position is set to one), or the last item in the list is removed (in that case current position is set to the current number of items in the list, after the removal).
     *
    @throws SimpleComparableListException if the list is empty or the current position is not set.
     *
     */
    public void removeItem() throws SimpleComparableListException {
        try {
            removeItem(currentPosition);
        } catch (SimpleComparableListException e) {
            throw new SimpleComparableListException("List is empty!");
        }
    }

    /**
     * The item at position is removed from the list.
     * If the removed item had a successor then that successor will be at the vacated position after the removal.
     * The current position is not changed unless the list becomes empty (in that case current position is set to one), or the last item in the list is removed (in that case current position is set to the current number of items in the list, after the removal).
     *
    @param position The position of the item to be removed
     *
    @throws SimpleComparableListException if the list is empty or the current position is not set.
     *
     */
    public void removeItem(int position) throws SimpleComparableListException {
        modCount++;
        if (empty()) {
            throw new SimpleComparableListException("List is empty!");
        }
        // Start Remove - Note no error checking.
        if (position == 1) {
            freeList[head] = freeHead;
            freeHead = head;
            head = links[head]; // Set new head to position 2.
        } else {
            int i = head;
            int step = 2; // If position==1 then is already caught.
            while (step < position) { // Traverse array
                i = links[i]; //Store index in i
                step++;
            }
            freeList[links[i]] = freeHead; // De-allocate
            freeHead = links[i]; // Freehead becomes removed node.
            links[i] = links[links[i]]; // Set head to node 2 away.
        }
    }

    /**
     * All item are removed from the list.
     * The list is empty after this method has finished.
     *
    @throws SimpleComparableListException If the list is empty.
     *
     */
    public void removeAll() throws SimpleComparableListException {
        modCount++;
        if (head == -1) {
            throw new SimpleComparableListException("Already Empty!");
        }
        init(maxElements);
    }

    /**
     * Gets the number of elements currently stored in this list.
     *
    @return the current number of elements in the list.
     *
     */
    public int getCurrentListSize() {
        int i = head;
        int counter = 0;
        while (i != -1) {
            i = links[i];
            counter++;
        }
        return counter;
    }

    /**
     * Gets the maximum number of elements that can be stored in this list.
     *
    @return the maximum number of elements that can be stored in the list.
     *
     */
    public int getCurrentMaxSize() {
        return maxElements;
    }

    /**
     * Gets the current value of this list's position indicator.
     * The list's position indicator is '1 based' not '0 based'.
     * That is, the first position is position 1, the second is postion 2, etc.
     * The last position is 'n' where n is the number of items in this list.
     *
    @return the current position.
     *
     */
    public int getCurrentPosition() {
        return currentPosition;
    }

    /**
     * Gets the list item at the current position in this list.
     *
    @return the String stored at the current position in the list.
     *
    @throws SimpleComparableListException - if the list is empty.
     *
     */
    public Comparable getItem() throws SimpleComparableListException {
        if (head == -1) {
            throw new SimpleComparableListException("The list is empty.");
        }
        int i = head;
        int step = 1;
        while (step < currentPosition) {
            i = links[i];
            step++;
        }
        return data[i];
    }

    /**
     * Sets the current value of this list's position indicator.
     * The new value of position must be between one and the current number of elements in the list.
     * If position is less than one the current value of the list's position indicator will be set to one, if position is greater than the current number of elements in the list then the current value of the list's position indicator will be set to the current number of elements in the list.
     *
    @param position The new current position for this list.
     *
     */
    public void setCurrentPosition(int position) {
        modCount++;
        if (position <= getCurrentListSize() && position >= 1) {
            currentPosition = position;
        } else if (position < 1) {
            currentPosition = 1;
        } else if (position > getCurrentListSize()) {
            position = getCurrentListSize();
        }
    }

    /**
     * Increments the current position of this list if the current position is not already the last position.
     * Otherwise nothing changes
     *
    @throws SimpleComparableListException if there is no next element or the list is empty.
     *
     */
    public void next() throws SimpleComparableListException {
        if (head == -1) {
            throw new SimpleComparableListException("The list is empty.");
        }
        if (currentPosition != maxElements) {
            currentPosition++;
        }
    }

    /**
     * Decrements the current position of this list if the current position is not already the first position.
     * Otherwise nothing changes.
     *
    @throws SimpleComparableListException if there is no previous element or if the list is empty.
     *
     */
    public void prev() throws SimpleComparableListException {
        if (head == -1) {
            throw new SimpleComparableListException("The list is empty.");
        }
        if (currentPosition != 1) {
            currentPosition--;
        }
    }

    /**
     * Sorts the list in ascending order according to the natural ordering of elements.
     * The natural ordering can be determined by the compareTo() method.
     * Before returning to the caller, the current position will be set at the last (end of list) position.
     *
     */
    public void sortList() {
        modCount++;
        Iterator iter = new listIterator();
        Comparable[] sortedData = new Comparable[getCurrentListSize()];
        int i = 0;
        while (iter.hasNext()) {
            sortedData[i] = (Comparable) iter.next();
            i++;
        }
        sortedData = quickPrep(sortedData);
        try {
            removeAll();
        } catch (SimpleComparableListException e) {
            System.out.println("There was an error sorting.");
        }
        for (int pos = 1; pos <= sortedData.length; pos++) {
            try {
                insertItem(sortedData[pos - 1], pos);
            } catch (SimpleComparableListException e) {
                System.out.println("There was an error sorting.");
            }
        }
    }

    private int findPrev(int position) {
        int i = head;
        int step = 0;
        while (step < position && links[i] != -1) {
            i = links[i];
        }
        return i;
    }

    /**
     * A method (For the private sorting methods) to swap specific places
     * of an array.
     *
    @param list
     *
    @param first
     *
    @param second
     *
     */
    private void swap(Comparable[] list, int first, int second) {
        Comparable oldFirst = list[first];
        list[first] = list[second];
        list[second] = oldFirst;
    }

    /**
     * A preparation method for the quick sort method below.
     *
    @param sortList The list to be prepared for sorting.
     *
     */
    private Comparable[] quickPrep(Comparable[] sortList) {
        int max = 0;
        for (int i = 1; i < sortList.length; i++) {
            if (sortList[i].compareTo(sortList[max]) > 0) {
                max = i;
            }
        }
        swap(sortList, sortList.length - 1, max);
        if (sortList.length > 1) {  // Make sure it's > 1 or it'll break
            quickSort(sortList, 0, sortList.length - 2);
        }
        return sortList;
    }

    /**
     * An implementation of the quick sort on Comparable items.
     *
    @param data The list to be sorted.
     *
    @param first The beginning bound of the list portion to be sorted.
     *
    @param last The ending bound of the list portion to be sorted.
     *
     */
    private void quickSort(Comparable[] data, int first, int last) {
        int lower = first + 1;
        int upper = last;
        swap(data, first, (first + last) / 2);
        Comparable pivot = data[first];
        while (lower <= upper) {
            while (data[lower].compareTo(pivot) < 0) {
                lower++;
            }
            while (data[upper].compareTo(pivot) > 0) {
                upper--;
            }
            if (lower < upper) {
                swap(data, lower++, upper--);
            } else {
                lower++;
            }
        }
        swap(data, upper, first);
        if (first < upper - 1) {
            quickSort(data, first, upper - 1);
        }
        if (upper + 1 < last) {
            quickSort(data, upper + 1, last);
        }
    }

    /**
     * Required Method.
     * Returns the data of the linked array as a String.
     *
     */
    @Override
    public String toString() {
        String output = "";
        int w = 1;
        int i = head;
        while (links[i] != -1) {
            output += w + " " + data[i];
            i = links[i];
            w++;
        }
        return output;
    }

    private class listIterator implements Iterator {

        private int iterPos;
        private int expectedModCount = modCount;

        /**
         * Determines whether the list has any objects that have not been
         * iterated.
         *
         *
        @return     <code>true</code> If there is.
         *         <code>false</code> If there is not.
         */
        public boolean hasNext() {
            return (iterPos < getCurrentListSize());
        }

        /**
         * Returns the next object to be iterated from the list.
         *
        @exception ConcurrentModicationException The list has been modified
         * since iterator creation.
         *
        @exception NoSuchElementException The list not have more elements.
         *
        @return     The next object in the List.
         */
        public Comparable next() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModicationException(
                        "Cannot mutate in context of iterator");
            }
            if (!hasNext()) {
                throw new NoSuchElementException(
                        "There are no more elements");
            }
            int step = 0;
            int i = head;
            while (step < iterPos) {
                i = links[i];
                step++;
            }
            iterPos++;
            return data[i];
        }

        /**
         * Unsupported. Do not use.
         *
         */
        public void remove() {
            throw new UnsupportedOperationException(
                    "remove not supported by List");
        }
    }

    private static class ConcurrentModicationException extends RuntimeException {

        /**
         * An exception occurring when there are concurrent modifications.
         *
         */
        public ConcurrentModicationException() {
            super("Error: Concurrent Modification.");
        }

        /**
         * An exception occurring when there are concurrent modifications.
         *
        @param  string  The error string.
         *
         */
        public ConcurrentModicationException(String string) {
            super(string);
        }
    }
}

class SimpleComparableListException extends Exception {

    /**
     * Default exception.
     *
     */
    public SimpleComparableListException() {
        super("There is some random error, I dunno what happened.");
    }

    /**
     * Exception that takes a string.
     *
    @param e The string to accept.
     *
     */
    public SimpleComparableListException(String e) {
        super(e);
    }
}
