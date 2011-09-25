/*
 * BSTNode Class
 *
 * Created March 24, 2011
 *
 * Last Modified: March 29, 2011
 *
 * @author Andrew Hobden
 * 
 */

public class BSTNode {
    // Instance Variables

    Comparable data;
    BSTNode left;
    BSTNode right;
    int balance;

    //Constructors
    /**
     * Initialize everything to null or nothing.
     */
    public BSTNode() {
        data = null;
        left = null;
        right = null;
        balance = 0;
    }

    /**
     * Initialize the data to the input, but nothing else.
     * @param item The data of the node.
     */
    public BSTNode(Comparable item) {
        data = item;
        left = null;
        right = null;
        balance = 0;
    }

    /**
     * Initialize the data to the input, but nothing else.
     * @param item The data of the node.
     * @param newBalance The new balance factor.
     */
    public BSTNode(Comparable item, int newBalance) {
        data = item;
        left = null;
        right = null;
        balance = newBalance;
    }

    /**
     * Initialize the data to the input, set the left and right.
     * @param item The data of the node.
     * @param left The left pointer of the node.
     * @param right The right pointer of the node.
     */
    public BSTNode(Comparable item, BSTNode left, BSTNode right) {
        data = item;
        this.left = left;
        this.right = right;
        balance = 0;
    }

    /**
     * Initialize the data to the input, set the left and right, and balance
     * factor.
     * @param item The data of the node.
     * @param left The left pointer of the node.
     * @param right The right pointer of the node.
     * @param balance The balance factor of the node.
     */
    public BSTNode(Comparable item, BSTNode left, BSTNode right, int balance) {
        data = item;
        this.left = left;
        this.right = right;
        this.balance = balance;
    }

    /**
     * Sets the data to the item.
     * @param item The new data.
     */
    public void setData(Comparable item) {
        data = item;
    }

    /**
     * Sets the left pointer to the input.
     * @param newLeft The new left pointer.
     */
    public void setLeft(BSTNode newLeft) {
        left = newLeft;
    }

    /**
     * Sets the right pointer to the input.
     * @param newRight The new right pointer.
     */
    public void setRight(BSTNode newRight) {
        right = newRight;
    }

    /**
     * Sets the balance factor to the input.
     * @param newBalance The new balance factor.
     */
    public void setBalance(int newBalance) {
        balance = newBalance;
    }

    /**
     * Decrements the balance by one.
     */
    public void decrementBalance() {
        balance--;
    }

    /**
     * Increments the balance by one.
     */
    public void incrementBalance() {
        balance++;
    }

    /**
     * Returns the data of the node.
     * @return The data of the node.
     */
    public Comparable getData() {
        return data;
    }

    /**
     * Returns the left pointer of the node.
     * @return The left pointer of the node.
     */
    public BSTNode getLeft() {
        return left;
    }

    /**
     * Returns the right pointer of the node.
     * @return The right pointer of the node.
     */
    public BSTNode getRight() {
        return right;
    }

    /**
     * Returns the balance of the node.
     * @return The balance of the node.
     */
    public int getBalance() {
        return balance;
    }
}
