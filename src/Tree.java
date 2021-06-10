/**
 * Tree - class for representation of Binary Search Tree
 * @param <T> type of elements to store in tree. Must be Comparable
 */
public class Tree<T extends Comparable<T>> {
    /**
     * Root node for tree
     */
    private Node<T> root;

    /**
     * StringBuilder for build string representation of tree for visualisation or print
     */
    private StringBuilder builder;

    /**
     * "Add"/"Insert" operation implementation
     * @param value for insert in tree
     * @return true if value inserted or false if not (there is equal value in tree)
     */
    public boolean insert(T value) {
        if (root == null) {
            root = new Node<>(value);
            return true;
        }
        return addInSubTree(value, root);
    }

    /**
     * Helper method for implementation o "Add"/"Insert".
     * Running only if tree root is not null
     * If new element less than element in root node, then insert in left subtree
     * If new element greater than element in root node, then insert in right subtree
     * @param value for insert in tree
     * @param root root of subtree for insert
     * @return true if value inserted or false if not (there is equal value in tree)
     */
    private boolean addInSubTree(T value, Node<T> root) {
        if(value.equals(root.getKey())) {
            return false;
        }
        if (value.compareTo(root.getKey()) < 0) {
            if (root.getLeft() == null) {
                root.setLeft(new Node<>(value));
                return true;
            } else {
                return addInSubTree(value, root.getLeft());
            }
        } else { // value.compareTo(root.getKey()) > 0
            if (root.getRight() == null) {
                root.setRight(new Node<>(value));
                return true;
            } else {
                return addInSubTree(value, root.getRight());
            }
        }
    }

    /**
     * Traverse tree in Infix order - gets elements in ascending order
     * @return string with elements in ascending order
     */
    public String traverse() {
        builder = new StringBuilder();
        traverse(root);
        return builder.toString();
    }

    /**
     * Helper method for traverse - do traverse tree in Infix order - gets elements in ascending order
     * @param root start point for traverse
     */
    private void traverse(Node<T> root) {
        if (root != null) {
            traverse(root.getLeft());
            visit(root);
            traverse(root.getRight());
        }
    }

    /**
     * Create string visualisation of tree with right-to-left infix order traversing
     * @return string visualisation of tree
     */
    public String draw() {
        builder = new StringBuilder();
        draw(root, 0);
        return builder.toString();
    }

    /**
     * Helper method for draw
     * @param root start point to visualisation of tree
     * @param level current level for this node
     */
    private void draw(Node<T> root, int level) {
        if (root != null) {
            draw(root.getRight(), level+1);
            visitWithLevel(root, level);
            draw(root.getLeft(), level+1);
        }
    }

    /**
     * "Delete"/"Remove" operation implementation for tree
     * @param key value for delete
     */
    public void delete(T key) {
        delete(key, root);
    }

    /**
     * Helper method for delete
     * @param key value for delete
     * @param node start node to find element to delete
     * @return node that was deleted from tree or null if no node was deleted
     */
    private Node<T> delete(T key, Node<T> node) {
        Node<T> root = node;
        if (root == null) return root;
        if (key.compareTo(root.getKey()) > 0) {
            root.setRight(delete(key, root.getRight()));
        } else if (key.compareTo(root.getKey()) < 0) {
            root.setLeft(delete(key, root.getLeft()));
        } else {
            if (root.getLeft() == null && root.getRight() == null) {
                root = null;
            } else if (root.getRight() != null) {
                root.setKey(successor(root));
                root.setRight(delete(root.getKey(), root.getRight()));
            } else {
                root.setKey(predecessor(root));
                root.setLeft(delete(root.getKey(), root.getLeft()));
            }
        }
        return root;
    }

    /**
     * Helper method for delete.
     * Search successor for value that current node is containing
     * @param node current node for search
     * @return value of successor
     */
    private T successor(Node<T> node) {
        Node<T> root = node;
        root = root.getRight();
        while (root.getLeft() != null) {
            root = root.getLeft();
        }
        return root.getKey();
    }

    /**
     * Helper method for delete.
     * Search predecessor for value that current node is containing
     * @param node current node for search
     * @return value of predecessor
     */
    private T predecessor(Node<T> node) {
        Node<T> root = node;
        root = root.getLeft();
        while (root.getRight()!=null) {
            root = root.getRight();
        }
        return root.getKey();
    }

    /**
     * Build part of string representation for draw tree
     * @param root current root for subtree
     * @param level current level of subtree
     */
    private void visitWithLevel(Node<T> root, int level) {
        builder.append(" ".repeat(level*3));
        builder.append(root.getKey()).append("|");
    }

    /**
     * Build part of string representation of ascending order for tree elements
     * @param root current root for visit
     */
    private void visit(Node<T> root) {
        builder.append(root.getKey()).append(" ");
    }

    /**
     * "Search" operation in tree
     * @param value data for find in tree
     * @return true if value is in tree, false - otherwise
     */
    public boolean search(T value) {
        return search(value, root);
    }

    /**
     * Helper method for search
     * @param value data for find in subtree
     * @param root root node of subtree where search performed
     * @return true if value is in tree, false - otherwise
     */
    private boolean search(T value, Node<T> root) {
        if (root == null) return false;
        if (root.getKey().equals(value)) return true;
        if (value.compareTo(root.getKey()) < 0) {
            return search(value, root.getLeft());
        } else {
            return search(value, root.getRight());
        }
    }

    /**
     * Delete element from tree
     * @param value object for delete from tree
     */
    public void delete(Object value) {
        try {
            delete((T)value);
        } catch (ClassCastException ignored) {}
    }

    /**
     * Find element in tree
     * @param value object to find in tree
     * @return true if element is in tree, or false - if not
     */
    public boolean search(Object value) {
        try {
            return search((T) value);
        } catch (ClassCastException e) {
            return false;
        }
    }

    /**
     * Insert element in tree
     * @param value object to insert in tree
     * @return true if element was inserted, or false - if not
     */
    public boolean insert(Object value) {
        try {
            return insert((T) value);
        } catch (ClassCastException e) {
            return false;
        }
    }
}
