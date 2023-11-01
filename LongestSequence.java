
/**
 * Author: Christopher Wagner
 * Programming Assignment 2 - Problem 3
 * Date: 10/31/2023
 * 
 * This program, LongestSequence, is designed to find the longest increasing sequence of elements in a given matrix.
 * It utilizes a custom stack implementation and a recursive depth-first search algorithm to identify the longest sequence.
 * The program reads input data from a file that represents an NxN matrix and outputs the longest sequence found.

 * Inputs: The program EXPECTS an input file located at "input/longestSequence.txt" containing data for the matrix.

 * Outputs: The program prints the longest increasing sequence found in the matrix, along with the length of the sequence.

 * Procedures Called:
 * - parseFile(String filePath): Parses the input file as an NxN matrix.
 * - longestSequence(int[][] matrix): Finds the longest increasing sequence in the matrix.
 * - elementLongestSequence(int[][] matrix, int row, int column): Finds the longest sequence for a given matrix element using a recursive depth-first search.
 * - printLongestSequence(Stack stack): Prints the longest sequence and its length.

 * Usage: Run the program with the provided input file, "input/longestSequence.txt," and observe the longest sequence and its length.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LongestSequence {
    /**
     * A node class that represents a node in a matrix. The node contains the value
     * of the element in the matrix, the row index of the element, and the column
     * index of the element.
     */
    public static class Node {
        private int value;
        private int row;
        private int column;

        public Node(int value, int row, int column) {
            this.value = value;
            this.row = row;
            this.column = column;
        }

        public int getValue() {
            return value;
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }
    }

    /**
     * A stack implementation that uses an ArrayList as the underlying data
     * structure. This allows the stack to grow dynamically.
     */
    public static class Stack {
        ArrayList<Node> stack;

        public Stack() {
            stack = new ArrayList<Node>();
        }

        public void push(Node value) {
            stack.add(value);
        }

        public Node pop() {
            if (isEmpty()) {
                return null;
            }
            return stack.remove(stack.size() - 1);
        }

        public boolean isEmpty() {
            return stack.size() == 0;
        }

        public int length() {
            return stack.size();
        }
    }

    public static void main(String[] args) {
        String inputPath = "input/longestSequence.txt";

        try {
            // Parse the input file
            int[][] a = parseFile(inputPath);

            // Get the longest sequence
            Stack longestSequence = longestSequence(a);

            // Print the longest sequence
            printLongestSequence(longestSequence);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    /**
     * Parses an input file as the format of a NxN matrix.
     * 
     * This function is opinioniated on how the file should be formatted.
     * It makes the assumption that the input file should have as many columns as
     * there are rows. If a row is only accounted for if it is not equal just a
     * newline character. If a given row has less than n-columns, where n represents
     * the number of rows, then an Error is thrown.
     * 
     * @param filePath
     * @return
     * @throws FileNotFoundException
     * @throws Error                 If a row does not have the same number of
     *                               columns as there are rows.
     */
    public static int[][] parseFile(String filePath) throws FileNotFoundException {
        Scanner input = new Scanner(new File(filePath));

        int size = 0;
        while (input.hasNextLine()) {
            if (input.nextLine() != "\n") {
                size++;
            }
        }
        input.close();

        int[][] matrix = new int[size][size];

        // Reinstantiate the scanner to the beginning of the file
        input = new Scanner(new File(filePath));

        for (int i = 0; i < size; i++) {
            String[] row = input.nextLine().split(" ");
            if (row.length != size) {
                input.close();
                throw new Error("Row " + i + " does not have the same number of columns as there are rows.");
            }
            for (int j = 0; j < size; j++) {
                matrix[i][j] = Integer.parseInt(row[j]);
            }
        }
        input.close();

        return matrix;
    }

    /**
     * Prints the longest sequence and the length of the longest sequence. The
     * longest sequence is represented as a stack.
     * 
     * @param stack The longest sequence represented as a stack
     */
    public static void printLongestSequence(Stack stack) {
        // Save the length of the longest sequence before popping the stack
        int length = stack.length();

        // Print the longest sequence nodes
        while (!stack.isEmpty()) {
            Node node = stack.pop();

            // Print the node
            System.out.printf("%s (%d, %d)\n", node.getValue(), node.getRow(), node.getColumn());
        }

        // Print the length of the longest sequence
        System.out.printf("The length of the longest sequence is %d\n", length);
    }

    /**
     * Finds the longest sequence in a matrix. The longest sequence is represented
     * as a stack of nodes. The nodes are in increasing order.
     * 
     * @param matrix The matrix to find the longest sequence in
     * @return The longest sequence represented as a stack of nodes
     */
    public static Stack longestSequence(int[][] matrix) {
        Stack longestSequence = new Stack();

        // Iterate through the matrix
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {

                // Get the longest sequence for the current element
                Stack localStack = elementLongestSequence(matrix, i, j);

                // If the local sequence is longer than the longest sequence, then replace the
                // longest sequence with the local sequence
                if (localStack.length() > longestSequence.length()) {
                    longestSequence = new Stack();

                    // Add the elements from the stack to the longest sequence
                    while (!localStack.isEmpty()) {
                        longestSequence.push(localStack.pop());
                    }
                }
            }
        }

        return longestSequence;
    }

    /**
     * Finds the longest sequence for a given element in a matrix. The longest
     * sequence is represented as a stack of nodes. The nodes are in increasing
     * order.
     * 
     * The function uses a recursive depth-first search to find the longest sequence
     * for a given element. The algorithm pushes the current element onto the stack
     * and then iterates through the 8 neighbors of the current element.
     * 
     * @param matrix The matrix to find the longest sequence in
     * @param row    The row index of the current element
     * @param column The column index of the current element
     * @return The longest sequence represented as a stack of nodes
     */
    public static Stack elementLongestSequence(int[][] matrix, int row, int column) {
        Stack stack = new Stack();

        // Create a node for the current element and push it onto the stack
        Node currentNode = new Node(matrix[row][column], row, column);
        stack.push(currentNode);

        // Create arrays used to compute the 8 neighbors of the current node using the
        // 8-connected method
        int[] x = { -1, -1, -1, 0, 0, 1, 1, 1 };
        int[] y = { -1, 0, 1, -1, 1, -1, 0, 1 };

        // Iterate through the 8 neighbors of the current node
        for (int i = 0; i < 8; i++) {
            int neighborRow = row + x[i];
            int neighborColumn = column + y[i];

            // Check if the neighbor is out of bounds, if so, then skip the neighbor
            // that is out of bounds
            if (neighborRow < 0 || neighborRow >= matrix.length || neighborColumn < 0
                    || neighborColumn >= matrix.length) {
                continue;
            }

            // Only check neighbors that are greater than the current node
            if (matrix[neighborRow][neighborColumn] <= currentNode.getValue()) {
                continue;
            }

            // Get the longest sequence for the neighbor
            Stack nieghborStack = elementLongestSequence(matrix, neighborRow, neighborColumn);

            // Check if the neighbor's stack is longer than the current stack and if so,
            // then replace the current stack with the neighbor's stack
            if (nieghborStack.length() + 1 > stack.length()) {
                // Create a temporary stack to hold the new
                stack = new Stack();
                stack.push(currentNode);

                // Create a temporary stack to hold the neighbors in reverse order
                Stack tempStack = new Stack();
                while (!nieghborStack.isEmpty()) {
                    Node node = nieghborStack.pop();
                    tempStack.push(node);
                }

                // Add the reversed neighbors to the current stack
                while (!tempStack.isEmpty()) {
                    Node node = tempStack.pop();
                    stack.push(node);
                }
            }
        }

        return stack;
    }
}
