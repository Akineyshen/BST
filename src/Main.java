import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

class Tree {

    double value;

    Tree left;

    Tree right;

    public Tree(double value) {
        this.value = value;
        left = null;
        right = null;
    }
}

class BST {

    private Tree root;

    public BST() {
        root = null;
    }

    public void paste(double value) {
        Tree newNode = new Tree(value);

        if (root ==  null) {
            root = newNode;
        } else {
            Tree actual = root;
            while (true) {
                if (value < actual.value) {
                    if (actual.left == null ) {
                        actual.left = newNode;
                        break;
                    }
                    actual = actual.left;
                } else if (value > actual.value) {
                    if (actual.right == null ) {
                        actual.right = newNode;
                        break;
                    }
                    actual = actual.right;
                } else {
                    break;
                }
            }
        }
    }

    public void remove(double value) {
        root = removeValue(root, value);
    }

    private Tree removeValue(Tree actual, double value) {
        if (actual == null) {
            return null;
        }

        if (value == actual.value) {
            // No child
            if (actual.left == null && actual.right == null){
                return null;
            }

            // One child
            if (actual.right == null) {
                return actual.left;
            }

            if (actual.left == null) {
                return actual.right;
            }

            // Two child
            double smallValue = FindValue (actual.right);
            actual.value = smallValue;
            actual.right = removeValue(actual.right, smallValue);
            return actual;
        }

        if (value < actual.value) {
            actual.left = removeValue(actual.left, value);
            return actual;
        }

        actual.right = removeValue(actual.right, value);
        return actual;
    }

    private double FindValue(Tree node){
        while (node.left != null) {
            node = node.left;
        }
        return node.value;
    }

    public boolean searh(double value){
        if (root == null) {
            return false;
        }

        Tree actual = root;
        do {
            if (value == actual.value) {
                return true;
            } else if (value < actual.value) {
                actual = actual.left;
            } else {
                actual = actual.right;
            }
        } while (actual != null);

        return false;
    }

    public int countValue(double intPart) {
        return countValueScore(root, intPart);
    }

    private int countValueScore (Tree node, double intPart) {
        if (node == null) {
            return 0;
        }

        int count = (int) node.value == intPart ? 1 : 0;
        count += countValueScore(node.left, intPart);
        count += countValueScore(node.right, intPart);

        return count;
    }

    public void viewTree() {
        BuildTree(this.root, 0, true);
    }

    private void BuildTree(Tree node, int space, boolean isRight) {
        if (node == null) {
            return;
        }

        int count = 5;
        String spaceFill = " ".repeat(space);

        if (isRight) {
            BuildTree(node.right, space + count, true);
            if (space != 0) {
                System.out.println(spaceFill + "┌── " + node.value);
            } else {
                System.out.println(node.value);
            }
        } else {
            BuildTree(node.right, space + count, true);
            if (space != 0) {
                System.out.println(spaceFill + "└── " + node.value);
            } else {
                System.out.println(node.value);
            }
        }

        BuildTree(node.left, space + count, false);
    }
}

public class Main {
    public static void main(String[] args) {
        String fileName = "in.txt";

        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            BST bst = new BST();

            int n = scanner.nextInt();
            StringBuilder output = new StringBuilder();

            for (int i = 0; i < n; i++) {
                char operation = scanner.next().charAt(0);

                switch (operation) {
                    case 'W':
                        double paste = scanner.nextDouble();
                        bst.paste(paste);
                        break;
                    case 'U':
                        double remove = scanner.nextDouble();
                        bst.remove(remove);
                        break;
                    case 'S':
                        double search = scanner.nextDouble();
                        boolean found = bst.searh(search);
                        output.append(found ? "Yes\n" : "No\n");
                        break;
                    case 'L':
                        double countValue = scanner.nextDouble();
                        int count = bst.countValue(countValue);
                        output.append("Count ").append(countValue).append(": ").append(count).append("\n");
                        break;
                    default:
                        i--;
                }
            }

            scanner.close();

            System.out.println(output.toString());
            System.out.println("Tree BST:");
            bst.viewTree();

        } catch (FileNotFoundException e) {
            System.out.println("No File: " + e.getMessage());
        }
    }
}