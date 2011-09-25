/*
 * Comp139Lab4
 *
 * Created March 24, 2011
 *
 * Last Modified: March 25, 2011
 *
 * @author Andrew Hobden
 *
 */
//*****************************************************
// This class represents a Binary Search Tree (BST). Comparable Objects can be inserted into the BST according to the sort order determined by the object's compareTo() method. COMP 139 students will implement:
//
//  * Two constructors:
//        o a default constuctor.
//        o a constuctor that has a comparable item as a parameter.
//  * Four public methods:
//        o add(Comparable data)
//        o remove(Comparable item)
//        o Iterator()
//        o printTree()
//  * At least the following private method:
//        o inOrderTraversal()
//
// The (BST) must be re-balanced after each insert. Re-balancing after a removal is not required for this lab.
//*******
import java.util.*;

public class BinarySearchTreeADT {

    int modCount;
    BSTNode root;
    int size;
    boolean rebalanced;

    /**
     * Default constructor. The BST is initialized to the empty state.
     */
    public BinarySearchTreeADT() {
        modCount = 0;
        root = null;
        size = 0;
    }

    /**
     * The BST is instantiated with one node.
     *
     * @param data The data which will be in the single treenode after instantiation.
     */
    public BinarySearchTreeADT(java.lang.Comparable data) {
        modCount = 0;
        root = new BSTNode(data);
        size = 1;
    }

    /**
     * Adds a Comparable object to this Binary Search Tree.
     * The added Comparable object will be placed in the tree so that an
     * in-order traversal will produce a list of the trees objects arranged in
     * ascending order according to the compareTo() method of each object.
     * The BST will be re-balanced after each add operation.
     *
     * @param data
     */
    public void add(java.lang.Comparable data) {
        modCount++;
        rebalanced = false;
        root = recursiveAdd(data, root);
        size++;
    }

    /**
     * Prints the nodes of the Binary Search tree into a String using the
     * toString() method of each node. If the string is printed then the
     * output will be in the shape of a tree with the root node at the left,
     * the right subtree above and to the right of the root, and the left
     * subtree below and to the right of the root. The balance factors for
     * each node are shown in parentheses beside the node.
     *
     * @return A string that graphically shows the tree structure.
     */
    public String printTree() {
        String result = "";
        if (root != null) {
            result = "\n\n" + printTree(root, 0);
        }
        return result;
    }

    /**
     * Returns an iterator. The iterator will use an in-order traversal over
     * the nodes in this binary search tree. Note that the iterator returned
     * by this method will throw an UnsupportedOperationException in response
     * to its remove method. This implementation throws a
     * BinarySearchTreeIteratorException exceptions in the face of concurrent
     * modification.
     *
     * @return An iterator over the nodes in this Binary Search Tree.
     * The iterator uses an in-order traversal to visit the nodes in the tree
     */
    public Iterator iterator() {
        Iterator iter = new iterator();
        return iter;
    }

    /**
     * Removes a specified Comparable object from this Binary Search Tree.
     * The BST may not be rebalanced after the removal. The original order
     * will be preserved.
     *
     * @param item The item to be removed.
     */
    public void remove(java.lang.Comparable item) {
        modCount++;
        root = removeRecursive(item, root);
        rebuildBalanceFactors(root);
        size--;
    }

    /**
     * Rebalance the tree by detecting critical nodes and rotating as necessary.
     */
    private BSTNode rebalance(BSTNode start) {
        if (start.getBalance() <= -2) {
            if (start.getLeft().getBalance() > 0) {
                start.setLeft(ccwRotation(start.getLeft()));
            }
            start = cwRotation(start);
            start.setBalance(0);
            //rebuildBalanceFactors(start);
        } else if (start.getBalance() >= 2) {
            if (start.getRight().getBalance() < 0) {
                start.setRight(cwRotation(start.getRight()));
            }
            start = ccwRotation(start);
            start.setBalance(0);
            //rebuildBalanceFactors(start);
        }
        return start;
    }

    /**
     * Performs a clockwise rotation on the subRoot.
     * @param subRoot The 'root' node of the rotation.
     * @return The new 'root'
     */
    private BSTNode cwRotation(BSTNode start) { // Mirror
        BSTNode temp = start.getLeft().getRight();
        start.getLeft().setRight(start);
        start = start.getLeft();
        start.getRight().setLeft(temp);
        start.getRight().setBalance(0);
        return start;
    }

    /**
     * Performs a Counter Clockwise rotation on the subRoot.
     * @param subRoot The 'root' node of the rotation.
     * @return The new 'root'
     */
    private BSTNode ccwRotation(BSTNode start) { // Master
        BSTNode temp = start.getRight().getLeft();
        start.getRight().setLeft(start);
        start = start.getRight();
        start.getLeft().setRight(temp);
        start.getLeft().setBalance(0);
        return start;
    }

    /**
     * Traverses the tree recursively and adds the data at the proper spot.
     * @param data The data to add.
     * @param start The position we're currently in.
     */
    private BSTNode recursiveAdd(Comparable data, BSTNode start) {
        BSTNode result = start;
        if (start == null) {
            result = new BSTNode(data);
        } else if (data.compareTo(start.getData()) <= 0) { //Go left
            result.setLeft(recursiveAdd(data, start.getLeft()));
            if (rebalanced == false) {
                start.decrementBalance();
                if (start.getBalance() == -2) {
                    result = rebalance(start);
                    rebalanced = true;
                }
            }
        } else if (data.compareTo(start.getData()) > 0) { //Go right
            result.setRight(recursiveAdd(data, start.getRight()));
            if (rebalanced == false) {
                start.incrementBalance();
                if (start.getBalance() == 2) {
                    result = rebalance(start);
                    rebalanced = true;
                }
            }
        }
        return result;
    }

    /**
     * Recursively searches for the parent of the desired data.
     * @param data The data we're looking for.
     * @param start The sub-root we are working with.
     * @return The parent of the data node we seek.
     */
    private BSTNode removeRecursive(Comparable data, BSTNode start) {
        if (data.compareTo(start.getData()) < 0) { //Go left
            if (start.getLeft() != null){
                start.setLeft(removeRecursive(data, start.getLeft()));
            }
        } else if (data.compareTo(start.getData()) > 0) { //Go right
            if (start.getRight() != null){
                start.setRight(removeRecursive(data, start.getRight()));
            }
        } else {
            if (start.getRight() != null && start.getLeft() != null) {
                start.setData(findMin(start.getRight()).getData());
                start.setRight(removeMin(start.getRight()));
            } else if (start.getLeft() != null) {
                start = start.getLeft();
            } else if (start.getRight() != null){
                start = start.getRight();
            } else {
                start = null;
            }
        }
        return start;
    }

    /**
     * Internal method to remove minimum item from a subtree.
     * @param t the node that roots the tree.
     * @return the new root.
     * @throws ItemNotFoundException if x is not found.
     */
    protected BSTNode removeMin(BSTNode start) {
        if (start.getLeft() != null) {
            start.setLeft(removeMin(start.getLeft()));
            return start;
        } else {
            return start.getRight();
        }
    }

    private BSTNode findMin(BSTNode start) {
        if (start != null) {
            while (start.getLeft() != null) {
                start = start.getLeft();
            }
        }
        return start;
    }

    /**
     * Rebalance the tree (in it's entirety) to it conforms with an in-order
     * transversal properly.
     * @param root The root of the tree.
     */
    private int rebuildBalanceFactors(BSTNode node) {
        int result = -1;
        int right;
        int left;
        if (node != null) {
            left = rebuildBalanceFactors(node.getLeft());
            left++;
            right = rebuildBalanceFactors(node.getRight());
            right++;
            node.setBalance(right - left);
            result = right - left;
        }
        return result;
    }

    /**
     * A recursive method to print a BST as a diagram into a string and return
     * the string.
     * @param root The root node.
     * @param indent The indent size
     * @return The tree, printed recursively.
     */
    private String printTree(BSTNode root, int indent) {
        int separation = 5;
        String result = "";
        if (root.getRight() != null) {
            result = result + printTree(root.getRight(), indent + separation);
        }
        result = result + makeIndents(indent) + root.getData().toString()
                + "(" + root.getBalance() + ")" + "\n";
        if (root.getLeft() != null) {
            result = result + printTree(root.getLeft(), indent + separation);
        }
        return result;
    }

    /**
     * Create and return a String with a given number of "indents" where
    // and indent is an adjustable sequence of spaces.
     * @param indent The indent size.
     * @return The new indent size.
     */
    private String makeIndents(int indent) {
        String result = "";
        for (int i = 0; i < indent; i++) {
            result = result + "     ";
        }
        return result;
    }

    /**
     * The iterator class, required for lab.
     */
    private class iterator implements Iterator {

        Comparable[] elements;
        int expectedModCount;
        int arrayPosition = 0; // Init to zero for the fill.

        /**
         * Default Constructor.
         */
        public iterator() {
            expectedModCount = modCount;
            elements = new Comparable[size];
            fillArray(root);
            arrayPosition = 0; // Reset to zero after fill.
        }

        /**
         * Detects if there is still more nodes left to iterate.
         * @return True if there is, false if there is not.
         */
        public boolean hasNext() {
            boolean result = false;
            if (arrayPosition <= elements.length - 1) {
                result = true;
            }
            return result;
        }

        /**
         * Returns the next piece of data within the BST.
         * @return The next piece of data within the BST.
         */
        public Object next() {
            Comparable data;
            if (hasNext() && expectedModCount == modCount) {
                data = elements[arrayPosition];
            } else {
                data = null;
            }
            arrayPosition++;
            return data;
        }

        /**
         * Not implemented.
         */
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        /**
         * Recursive array for filling the elements array with the BST
         * @param temp The node to hand in, recursively.
         */
        private void fillArray(BSTNode temp) {
            if (temp.getLeft() != null) {
                fillArray(temp.getLeft());
            }
            elements[arrayPosition] = temp.getData();
            arrayPosition++;
            if (temp.getRight() != null) {
                fillArray(temp.getRight());
            }
        }
    }
}
