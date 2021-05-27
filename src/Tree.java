
public class Tree<T extends Comparable<T>> {
    private Node<T> root;
    StringBuilder builder;

    public boolean insert(T value) {
        if (root == null) {
            root = new Node<>(value);
            return true;
        }
        return addInSubTree(value, root);
    }

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

    public String traverse() {
        builder = new StringBuilder();
        traverse(root);
        return builder.toString();
    }

    private void traverse(Node<T> root) {
        if (root != null) {
            traverse(root.getLeft());
            visit(root);
            traverse(root.getRight());
        }
    }

    public String draw() {
        builder = new StringBuilder();
        draw(root, 0);
        return builder.toString();
    }

    private void draw(Node<T> root, int level) {
        if (root != null) {
            draw(root.getRight(), level+1);
            visitWithLevel(root, level);
            draw(root.getLeft(), level+1);
        }
    }

    public void delete(T key) {
        delete(key, root);
    }

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

    private T successor(Node<T> node) {
        Node<T> root = node;
        root = root.getRight();
        while (root.getLeft() != null) {
            root = root.getLeft();
        }
        return root.getKey();
    }

    private T predecessor(Node<T> node) {
        Node<T> root = node;
        root = root.getLeft();
        while (root.getRight()!=null) {
            root = root.getRight();
        }
        return root.getKey();
    }

    private void visitWithLevel(Node<T> root, int level) {
//        System.out.print(" ".repeat(level*3));
//        System.out.println(root.getKey());
        builder.append(" ".repeat(level*3));
        builder.append(root.getKey()).append("|");
    }


    private void visit(Node<T> root) {
//        System.out.print(root.getKey() + " ");
        builder.append(root.getKey()).append(" ");
    }

    public boolean search(T value) {
        return search(value, root);
    }

    private boolean search(T value, Node<T> root) {
        if (root == null) return false;
        if (root.getKey().equals(value)) return true;
        if (value.compareTo(root.getKey()) < 0) {
            return search(value, root.getLeft());
        } else {
            return search(value, root.getRight());
        }
    }
}
