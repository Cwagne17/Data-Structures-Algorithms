
/**
 * Author: Christopher Wagner
 * Programming Assignment 2 - Problem 2
 * Date: 10/31/2023
 * 
 * This program, JosephusProblem, simulates the Josephus problem, a historical scenario involving a group
 * of soldiers forming a circle and eliminating every nth person until only one person remains, who is the survivor.
 * The program uses a circular queue data structure to achieve this simulation.

 * Input: The program interactsively takes the number of soldiers and their names as input. Additionally, it prompts for
 * the elimination position (n).

 * Output: The program displays the elimination order and the name of the survivor. The elimination order shows
 * each soldier's name and their original spot number.

 * Procedures Called:
 * - CircularQueue(int size): Constructor to create a circular queue.
 * - enqueue(String name): Adds a soldier to the queue.
 * - dequeue(): Removes and returns a soldier from the queue.
 * - isEmpty(): Checks if the queue is empty.
 * - isFull(): Checks if the queue is full.

 * Usage: Run the program, provide the number of soldiers, their names, and the elimination position (n), and observe
 * the elimination order and the name of the survivor.
 */

import java.util.Scanner;

public class JosephusProblem {
    public class CircularQueue {
        String[] queue;
        int frontIndex;
        int backIndex;

        public CircularQueue(int size) {
            // Since N - 1 is how the queue is being treated as the available size
            // we are adding 1 to the size to account for this internal implementation
            queue = new String[size + 1];
            frontIndex = 0;
            backIndex = 0;
        }

        public void enqueue(String name) {
            if (isFull()) {
                System.out.println("Queue is full");
                return;
            }
            queue[backIndex] = name;
            backIndex = (backIndex + 1) % queue.length;
        }

        public String dequeue() {
            if (isEmpty()) {
                System.out.println("Queue is empty");
                return null;
            }
            String name = queue[frontIndex];
            queue[frontIndex] = null;
            frontIndex = (frontIndex + 1) % queue.length;
            return name;
        }

        public boolean isEmpty() {
            return (backIndex + 1) % queue.length == (frontIndex + 1) % queue.length;
        }

        public boolean isFull() {
            return (backIndex + 1) % queue.length == frontIndex;
        }

    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Get the number of soldiers from stdin
        System.out.print("How many soldiers? ");
        int numberOfSoldiers = input.nextInt();

        // Create a queue
        CircularQueue queue = new JosephusProblem().new CircularQueue(numberOfSoldiers);

        // Initialize the queue
        System.out.printf("Type %d soldiers name:\n", numberOfSoldiers);
        for (int i = 0; i < numberOfSoldiers; i++) {
            String name = input.next();
            queue.enqueue(name);
        }

        // Get the elimination position from stdin
        System.out.print("Enter the elimination position: ");
        int n = input.nextInt();

        // Close the scanner
        input.close();

        // Eliminate the soldiers
        while (!queue.isEmpty()) {
            // Dequeue than enqueue n - 1 times. This will move the n-th person to the front
            // of the queue
            for (int i = 0; i < n - 1; i++) {
                queue.enqueue(queue.dequeue());
            }

            // Dequeue the n-th person
            String name = queue.dequeue();
            if (queue.isEmpty()) {
                System.out.printf("The survivor is %s\n", name);
                break;
            }
            System.out.printf("Eliminated: %s\n", name);
        }
    }
}