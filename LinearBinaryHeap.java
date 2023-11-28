
/**
 * Author: Christopher Wagner
 * Programming Assignment 3 - Problem 1
 * Date: 11/27/2023
 * 
 * This program, LinearBinaryHeap.java, implements a binary heap using an ArrayList as the underlying data structure.
 * The program designs the insert method to insert multiple values into the heap one at a time, then percolating down starting from the first non-leaf node.
 *
 * Inputs: N/A, the program is designed to create a heap with 5000 random values from 0 to 50000.
 *
 * Outputs: The program prints the first 50 values in the heap (not using deleteMin), the time it took to insert all the values, and the number of swaps that occurred.
 * 
 * Procedures Called:
 * - insert(ArrayList<Integr> values): Inserts the given values into the heap linearly and returns the number of swaps that occurred.
 * - percolateDown(int index): Recursively percolates down the heap until the heap property is maintained.
 *
 * Usage: Run the program and observe the output.
 */

import java.util.ArrayList;

public class LinearBinaryHeap {
    public static void main(String[] args) {
        BinaryHeap heap = new BinaryHeap();

        // Initialize the swaps counter
        int swaps = 0;

        // Create a list of 5000 random values
        ArrayList<Integer> values = new ArrayList<Integer>();
        for (int i = 0; i < 5000; i++) {
            values.add((int) (Math.random() * 50000));
        }

        // Get start time
        long startTime = System.nanoTime();

        // Insert the values into the heap
        swaps += heap.insert(values);

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
         * Inserts the given values into the heap linearly and returns the number of
         * swaps
         * that occurred.
         * 
         * @param values The values to insert
         * @return The number of swaps that occurred
         */
        public int insert(ArrayList<Integer> values) {
            int swaps = 0;

            // Add all the values to the heap
            this.heap.addAll(values);

            // Percolate down starting from the half way point
            for (int i = (this.heap.size() / 2) - 1; i >= 0; i--) {
                swaps += this.percolateDown(i);
            }

            return swaps;
        }

        /**
         * Recursively percolates down the value at the given index
         * until it is in the correct position.
         * 
         * @param index The index of the value to percolate down from
         * @return The number of swaps that occurred
         */
        private int percolateDown(int index) {
            int swaps = 0;
            int smallestIndex = index;

            // Calculate the left and right child indices
            int leftChildIndex = (2 * index) + 1;
            int rightChildIndex = (2 * index) + 2;

            // If right child index is within the bounds of the heap
            // and the value at the right child index is less than
            // the value at the smallest index, then update the
            // smallest index
            if (rightChildIndex < this.heap.size() && this.heap.get(rightChildIndex) < this.heap.get(smallestIndex)) {
                smallestIndex = rightChildIndex;
            }

            // If left child index is within the bounds of the heap
            // and the value at the left child index is less than
            // the value at the smallest index, then update the
            // smallest index
            if (leftChildIndex < this.heap.size() && this.heap.get(leftChildIndex) < this.heap.get(smallestIndex)) {
                smallestIndex = leftChildIndex;
            }

            // If the smallest index is not the current index,
            // then swap the values and percolate down from the
            // smallest index
            if (smallestIndex != index) {
                int smallestValue = this.heap.get(smallestIndex);
                int currentValue = this.heap.get(index);

                this.heap.set(smallestIndex, currentValue);
                this.heap.set(index, smallestValue);
                swaps++;

                swaps += this.percolateDown(smallestIndex);
            }

            return swaps;
        }
    }
}
