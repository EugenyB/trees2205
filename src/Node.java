/**
 * Tree element node.
 * Stores data as "key" in tree.
 * @param <T> type of elements in Tree. Must be Comparable
 */
public class Node<T extends Comparable<T>> {
    private T key;
    private Node<T> left;
    private Node<T> right;

    /**
     * Create node with data as key.
     * Left and Right subtrees are null
     * @param key data to store in Tree
     */
    public Node(T key) {
        this.key = key;
    }

    /**
     * Retrieve data from tree node
     * @return data, stored as key
     */
    public T getKey() {
        return key;
    }

    /**
     * Set new data in tree node
     * @param key data to store in node
     */
    public void setKey(T key) {
        this.key = key;
    }

    /**
     * Gets root of left subtree
     * @return root of left subtree
     */
    public Node<T> getLeft() {
        return left;
    }

    /**
     * Sets root of left subtree
     * @param left new root for left subtree
     */
    public void setLeft(Node<T> left) {
        this.left = left;
    }

    /**
     * Gets root of right subtree
     * @return root of right subtree
     */
    public Node<T> getRight() {
        return right;
    }

    /**
     * Sets root of right subtree
     * @param right new root for right subtree
     */
    public void setRight(Node<T> right) {
        this.right = right;
    }
}
