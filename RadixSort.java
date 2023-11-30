
/**
 * Author: Christopher Wagner
 * Programming Assignment 3 - Problem 3
 * Date: 11/27/2023
 * 
 * This program, RadixSort.java, implements the radix sort algorithm.
 * The program creates an array of 5000 random values from 0 to 50000 and sorts
 * the array using the radix sort algorithm. The program then prints 100
 * values that are the first and last 100 values in the array.
 *
 * Inputs: N/A, the program is designed to create an array with 5000 random
 * values from 0 to 50000.
 *
 * Outputs: The program prints the first and last 100 values in the array,
 * the time it took to sort the array.
 * 
 * Procedures Called:
 * - radixSort(int[] arr): Sorts the given array using the radix sort
 *
 * Usage: Run the program and observe the output.
 */

import java.util.ArrayList;
import java.util.List;

public class RadixSort {
    public static void main(String[] args) {
        int size = 5000;

        // Initialize the array
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = (int) (Math.random() * 50000);
        }

        // Evaluate the radix sort algorithm
        long startTime = System.nanoTime();
        radixSort(arr);
        long endTime = System.nanoTime();

        System.out.println("Radix Sort: " + (endTime - startTime) / 1000000.0 + "ms");

        System.out.print("First 100 elements: ");
        for (int i = 0; i < 100; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();

        System.out.print("Last 100 elements: ");
        for (int i = arr.length - 100; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    /**
     * Sorts an array using the radix sort algorithm.
     * 
     * @param arr the array to sort
     */
    public static void radixSort(int[] arr) {
        List<ArrayList<Integer>> buckets = new ArrayList<>();

        // Initialize the buckets as ArrayLists
        for (int i = 0; i < 10; i++) {
            buckets.add(new ArrayList<Integer>());
        }

        // Find the maximum number of digits in the array
        int maxDigits = 0;
        for (int i = 0; i < arr.length; i++) {
            int digits = (int) Math.log10(arr[i]) + 1;
            if (digits > maxDigits) {
                maxDigits = digits;
            }
        }

        // Sort the array by each digit
        for (int i = 0; i < maxDigits; i++) {
            // Place each element in the appropriate bucket
            for (int j = 0; j < arr.length; j++) {
                int digit = (int) (arr[j] / Math.pow(10, i)) % 10;
                buckets.get(digit).add(arr[j]);
            }

            // Reconstruct the array
            int index = 0;
            for (int j = 0; j < buckets.size(); j++) {
                for (int k = 0; k < buckets.get(j).size(); k++) {
                    arr[index] = buckets.get(j).get(k);
                    index++;
                }
                buckets.get(j).clear();
            }
        }
    }
}
