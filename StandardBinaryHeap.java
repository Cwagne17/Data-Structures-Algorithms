
/**
 * Author: Christopher Wagner
 * Programming Assignment 3 - Problem 1
 * Date: 11/27/2023
 * 
 * This program, StandardBinaryHeap.java, implements a standard binary heap using an ArrayList as the underlying data structure.
 * The program designs the insert method to insert each value into the heap one at a time, percolating up each value as it is inserted.
 *
 * Inputs: N/A, the program is designed to create a heap with 5000 random values from 0 to 50000.
 *
 * Outputs: The program prints the first 50 values in the heap (not using deleteMin), the time it took to insert all the values, and the number of swaps that occurred.
 * 
 * Procedures Called:
 * - insert(int value): Inserts the given value into the heap and returns the number of swaps that occurred.
 * - percolateUp(int index): Recursively percolates up the value at the given index until it is in the correct position.
 *
 * Usage: Run the program and observe the output.
 */

import java.util.ArrayList;

public class StandardBinaryHeap {
    public static void main(String[] args) {
        BinaryHeap heap = new BinaryHeap();

        // Initialize the swaps counter
        int swaps = 0;

        // Get start time
        long startTime = System.nanoTime();

        // Insert 5000 random values into the heap
        for (int i = 0; i < 5000; i++) {
            swaps += heap.insert((int) (Math.random() * 50000));
        }

        // Get end time
        long endTime = System.nanoTime();

        // Print out the first 50 values in the heap
        System.out.print("[ ");
        for (int i = 0; i < 50; i++) {
            System.out.print(heap.heap.get(i) + " ");
        }
        System.out.println("]");

        // Print out the time it took to insert all the values
        // and the number of swaps that occurred
        System.out.println("Time: " + (endTime - startTime) / 1000000.0 + " ms");
        System.out.println("Swaps: " + swaps);
    }

    public static class BinaryHeap {
        ArrayList<Integer> heap = new ArrayList<Integer>();

        public BinaryHeap() {
        }

        /**
         * Inserts the given value into the heap and returns the number of swaps
         * that occurred.
         * 
         * @param value The value to insert
         * @return The number of swaps that occurred
         */
        public int insert(int value) {
            int swaps = 0;

            // Add the value to the end of the heap
            this.heap.add(value);

            // Percolate up from the last index to restore heap property
            swaps += this.percolateUp(this.heap.size() - 1);

            return swaps;
        }

        /**
         * Recursively percolates up the value at the given index
         * until it is in the correct position.
         * 
         * @param index The index of the value to percolate up
         * @return The number of swaps that occurred
         */
        private int percolateUp(int index) {
            int swaps = 0;

            // Calculate the parent index of the current index
            int parentIndex = (index - 1) / 2;

            // If the current value is less than the parent value,
            // then swap the values
            if (this.heap.get(index) < this.heap.get(parentIndex)) {
                int currentValue = this.heap.get(index);
                this.heap.set(index, this.heap.get(parentIndex));
                this.heap.set(parentIndex, currentValue);
                swaps++;

                // Keep going until we are at the root
                if (parentIndex > 0) {
                    swaps += this.percolateUp(parentIndex);
                }
            }

            return swaps;
        }

    }
}
