/*
 * SimpleStringList.java
 *
 * Created Febuary 8, 2011
 *
 * Last Modified: Febuary 16, 2011
 *
 * @author Andrew Hobden
 *
 */
//*****************************************************
// SimpleStringList class and auxilary methods
// that provide smooth operation for COMP139Lab2Driver
//*****************************************************

import java.util.*;

public class SimpleStringList {

    protected int currentPosition;
    protected int maxElements;
    protected OneWayNode head;

    /**
     * Default constructor.
     * The list is initialized to empty.
     * The maximum number of elements is set at the default value (10).
     * The current list size is set to zero and the current position is set to one.
     *
     */
    public SimpleStringList() {
        currentPosition = 1;
        maxElements = 10;
        head = null;
    }

    /**
     * Constructs and initializes a list with a caller specified maximum size.
     * The list is initialized to empty.
     * The current list size is set to zero and the current position is set to one.
     *
    @param size The maximum size of this list.
     *
     */
    public SimpleStringList(int size) {
        currentPosition = 1;
        maxElements = size;
        head = null;
    }

    /**
     * Copy constructor. Constructs and initializes a list with the contents of a caller specified SimpleStringList.
     * The current list contents, size and position are set to match (becomes a copy of) the other list provided by the caller
     *
    @param other The other SimpleStringList which will be the source from which the data will be copied.
     *
     */
    public SimpleStringList(SimpleStringList other) {
        maxElements = other.getCurrentMaxSize();
        int otherPosition = other.getCurrentPosition();
        for (int i = 1; i <= other.getCurrentListSize(); i++) {
            other.setCurrentPosition(i);
            try {
                insertItem((Comparable) other.getItem(), i);
            } catch (SimpleStringListException e) {
                System.out.println("WUT");
            }
        }
        other.setCurrentPosition(otherPosition);
        setCurrentPosition(otherPosition);
        //currentPosition = other.getCurrentPosition();
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
        OneWayNode probe = head;
        while (probe != null) {
            probe = probe.getNext();
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
        return (head == null);
    }

    /**
     * Inserts item into the list at the current position.
     * If an item occupied the current position before this call, that item will be the new item's successor after the call.
     * The list's current position does not change. The first item in the list is defined to be at position 1 (one) not at position zero (0).
     * To insert an item after the last item in the list (i.e. insert an item which will become the last item and will not have a successor) you must use insertItem(Comparable, int)
     *
    @param item The item to be inserted.
     *
    @throws SimpleStringListException if the list is full.
     *
     */
    public void insertItem(java.lang.Comparable item) throws SimpleStringListException {
        try {
            insertItem(item, currentPosition);
        } catch (SimpleStringListException e) {
            throw new SimpleStringListException("The list is full.");
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
    @throws SimpleStringListException if the list is full.
     *
     */
    public void insertItem(java.lang.Comparable item, int position) throws SimpleStringListException {
		if (full() == true) {
            throw new SimpleStringListException("This list is full.");
        } else if (head == null) {
            head = new OneWayNode(item, null);
        } else if (position == 1) {
            head = new OneWayNode(item, head);
        } else {
            int step = 0;
            OneWayNode probe = head;
            while (step < position && probe.getNext() != null) {
                probe = probe.getNext();
                step++;
            }
            probe.setNext(new OneWayNode(item, probe.getNext()));
        }
        if (position < getCurrentListSize()) {
            currentPosition = position;
        } else {
            currentPosition = getCurrentListSize();
        }
    }

    /**
     * The item at the current position is removed from the list.
     * If the removed item had a successor then that successor will be at the current position after the removal.
     * The current position is not changed unless the list becomes empty (in that case current position is set to one), or the last item in the list is removed (in that case current position is set to the current number of items in the list, after the removal).
     *
    @throws SimpleStringListException if the list is empty or the current position is not set.
     *
     */
    public void removeItem() throws SimpleStringListException {
        try {
            removeItem(currentPosition);
        } catch (SimpleStringListException e) {
            throw new SimpleStringListException("The list is empty.");
        }
    }

    /**
     * The item at position is removed from the list.
     * If the removed item had a successor then that successor will be at the vacated position after the removal.
     * The current position is not changed unless the list becomes empty (in that case current position is set to one), or the last item in the list is removed (in that case current position is set to the current number of items in the list, after the removal).
     *
    @param position The position of the item to be removed
     *
    @throws SimpleStringListException if the list is empty or the current position is not set.
     *
     */
    public void removeItem(int position) throws SimpleStringListException {
        if (head == null) {
            throw new SimpleStringListException("The list is empty.");
        } else if (position == 1) {
            head = head.getNext();
        } else {
            int step = 1;
            OneWayNode probe = head;
            while (step < position - 1 && probe.getNext() != null) {
                probe = probe.getNext();
                step++;
            }
            probe.setNext(probe.getNext().getNext());
            if (empty()) {
                currentPosition = 1;
            } else if (position > getCurrentListSize()) {
                currentPosition = getCurrentListSize();
            }
        }
    }

    /**
     * All item are removed from the list.
     * The list is empty after this method has finished.
     *
    @throws SimpleStringListException If the list is empty.
     *
     */
    public void removeAll() throws SimpleStringListException {
        if (head == null) {
            throw new SimpleStringListException("The list is empty already.");
        }
        head = null;
        currentPosition = 1;
    }

    /**
     * Gets the number of elements currently stored in this list.
     *
    @return the current number of elements in the list.
     *
     */
    public int getCurrentListSize() {
        OneWayNode probe = head;
        int counter = 0;
        while (probe != null) {
            probe = probe.getNext();
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
    @throws SimpleStringListException - if the list is empty.
     *
     */
    public Object getItem() throws SimpleStringListException {
        if (head == null) {
            throw new SimpleStringListException("The list is empty.");
        }
        OneWayNode probe = head;
        int step = 1;
        while (step < currentPosition) {
            probe = probe.getNext();
            step++;
        }
        return probe.getData();
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
    @throws SimpleStringListException if there is no next element or the list is empty.
     *
     */
    public void next() throws SimpleStringListException {
        if (head == null) {
            throw new SimpleStringListException("The list is empty.");
        }
        if (currentPosition != maxElements) {
            currentPosition++;
        }
    }

    /**
     * Decrements the current position of this list if the current position is not already the first position.
     * Otherwise nothing changes.
     *
    @throws SimpleStringListException if there is no previous element or if the list is empty.
     *
     */
    public void prev() throws SimpleStringListException {
        if (head == null) {
            throw new SimpleStringListException("The list is empty.");
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
        Object[] a = new Object[getCurrentListSize()];
        OneWayNode probe = head;
        for (int i = 0; i < a.length; i++) {
            a[i] = probe.getData();
            probe = probe.getNext();
        }
        Arrays.sort(a);
        head = new OneWayNode(a[0], null);
        probe = head;
        for (int i = 1; i < a.length; i++) {
            probe.setNext(new OneWayNode(a[i], null));
            probe = probe.getNext();
        }
    }

    /**
     * Required Method.
     * Returns the data of the linked list as a String.
     *
     */
    @Override
    public String toString() {
        String output = "";
        int w = 1;
        OneWayNode probe = head;
        while (probe.getNext() != null) {
            output += w + " " + probe.getData();
            probe = probe.getNext();
            w++;
        }
        return output;
    }

    public class OneWayNode {

        private Object data;
        private OneWayNode next;

        /**
         * Default Constructor.
         * Creates a new node with data and a link to the next node.
         *
        @param d The data to store.
         *
        @param n The next node.
         *
         */
        public OneWayNode(Object d, OneWayNode n) {
            data = d;
            next = n;
        }

        /**
         * Sets the next node.
         *
        @param n The next node.
         *
         */
        public void setNext(OneWayNode n) {
            next = n;
        }

        /**
         * Sets the data of the node.
         *
        @param dat The data to set for the node.
         *
         */
        public void setData(Object dat) {
            data = dat;
        }

        /**
         * Returns the data of the node.
         *
         */
        public Object getData() {
            return data;
        }

        /**
         * Returns the location of the next node.
         *
         */
        public OneWayNode getNext() {
            return next;
        }
    }
}

class SimpleStringListException extends Exception {

    /**
     * Default exception.
     *
     */
    public SimpleStringListException() {
        super("An unknown error occurred.");
    }

    /**
     * Exception that takes a string.
     *
    @param e The string to accept.
     *
     */
    public SimpleStringListException(String e) {
        super(e);
    }
}