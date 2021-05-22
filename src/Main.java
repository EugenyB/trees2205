public class Main {

    public static void main(String[] args) {
	    new Main().run();
    }

    private void run() {
        Tree<Integer> tree = new Tree<>(); // 70, 60, 85, 87, 35, 68, 72
        tree.insert(70);
        tree.insert(60);
        tree.insert(85);
        tree.insert(87);
        tree.insert(35);
        tree.insert(68);
        tree.insert(72);
        tree.insert(86);
        tree.insert(90);

        System.out.println(tree.draw());
        System.out.println(tree.traverse());

        tree.delete(85);

        System.out.println(tree.draw());
        System.out.println(tree.traverse());
    }
}
