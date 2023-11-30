/**
 * Author: Christopher Wagner
 * Programming Assignment 3 - Problem 3
 * Date: 11/27/2023
 * 
 * This program, CountingSort.java, implements the counting sort algorithm.
 * The program creates an array of 5000 random values from 0 to 500 and sorts
 * the array using the counting sort algorithm. The program then prints 50
 * values that are every 100th index in the array.
 *
 * Inputs: N/A, the program is designed to create an array with 5000 random
 * values from 0 to 500.
 *
 * Outputs: The program prints the first 100 values in the array, the time it
 * took to sort the array.
 * 
 * Procedures Called:
 * - countingSort(int[] arr): Sorts the given array using the counting sort
 *
 * Usage: Run the program and observe the output.
 */

public class CountingSort {
    public static void main(String[] args) {
        int size = 5000;

        // Initialize the array
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = (int) (Math.random() * 500);
        }

        // Evaluate the counting sort algorithm
        long startTime = System.nanoTime();
        countingSort(arr);
        long endTime = System.nanoTime();

        System.out.println("Counting Sort: " + (endTime - startTime) / 1000000.0 + "ms");

        for (int i = 0; i < 50; i++) {
            System.out.print(arr[i * 100] + " ");
        }
        System.out.println();
    }

    /**
     * Sorts an array using the counting sort algorithm.
     * 
     * @param arr the array to sort
     */
    public static void countingSort(int[] arr) {
        int[] count = new int[500];

        // Count the number of occurrences of each element
        for (int i = 0; i < arr.length; i++) {
            count[arr[i]]++;
        }

        // Reconstruct the array
        int index = 0;
        for (int i = 0; i < count.length; i++) {
            while (count[i] > 0) {
                arr[index] = i;
                index++;
                count[i]--;
            }
        }
    }
}
