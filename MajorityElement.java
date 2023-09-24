
/**
 * Author: Christopher Wagner
 * Programming Assignment 1 - Problem 4
 * Date: 9/24/2023
 * 
 * This program will identify the majority element in a list of integers
 * using the divide and conquer method and the Boyer-Moore majority vote
 * algorithm. The program will then print the majority element found by 
 * each method and the execution time of each method.
 * 
 * The program will repeat this test for each input file defined in the
 * inputFiles array in the main method.
 * 
 * Inputs: None, but the program will read from the input files defined
 *        in the inputFiles array in the main method.
 * 
 * Outputs: The following template will be printed for each input file:
 * 
 * ---------------------------------------------
 * Testing <input file>...
 * 
 * (divide-conquer): <majority element> is the majority element
 * (boyer-moore): <majority element> is the majority element
 * 
 * Results (n = <size of input file>):
 * Divide and Conquer: <execution time>ms
 * Boyer-Moore: <execution time>ms
 * ---------------------------------------------
 * 
 * If a file is not found, the program will print the exception in the
 * following format and continue to the next file.
 * 
 * ---------------------------------------------
 * Testing <input file>...
 * 
 * java.io.FileNotFoundException: <input file> (No such file or directory)
 * ---------------------------------------------
 * 
 * Procedures Called:
 *    parseFile(String filePath)
 *    measureExecutionTime(String method, List<Integer> a, String inputFile)
 *      getMajorityDivideConquer(List<Integer> a)
 *      getMajorityBoyerMooreVoting(List<Integer> a)
 * 
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MajorityElement {
    public static void main(String[] args) {
        /**
         * The input files to be tested
         * 
         * Update this array to test different input files.
         * FileNotFound exceptions will be caught and printed
         * for each file that is not found. Each test case
         * is independent of each other. A not found exception
         * will not stop the program from testing the other
         * files.
         */
        String[] inputFiles = {
                "input/Majex.txt",
                "input/Majex2.txt",
                "input/Majex3.txt",
                "input/Majex4.txt"
        };

        // Iterate through each input file
        for (String inputFile : inputFiles) {

            System.out.println("---------------------------------------------\nTesting " + inputFile + "...\n");

            try {
                // Parse the file into an ArrayList of integers
                ArrayList<Integer> list = parseFile(inputFile);

                // Measure the execution time of each method
                double divideConquerTime = measureExecutionTime("divide-conquer", list, inputFile);
                double boyerMooreTime = measureExecutionTime("boyer-moore", list, inputFile);

                // Print the results
                System.out.println("\nResults (n = " + list.size() + "): \n" +
                        "Divide and Conquer: " + divideConquerTime + "ms" + "\n" +
                        "Boyer-Moore: " + boyerMooreTime + "ms");
            } catch (FileNotFoundException e) {
                // Print the exception and continue to the next file
                System.out.println(e.toString());
            }

            System.out.println("---------------------------------------------\n");
        }

    }

    /**
     * Parses a file and returns an ArrayList of integers
     * 
     * @param filePath The path to the file to be parsed
     * @return An ArrayList of integers
     */
    public static ArrayList<Integer> parseFile(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner input = new Scanner(file);

        ArrayList<Integer> list = new ArrayList<Integer>();

        // Iterate indiscriminately through
        // the file parsing all tokens
        while (input.hasNext()) {
            // If the token is an integer
            // it'll add it to the list
            // otherwise it'll skip it
            if (input.hasNextInt()) {
                list.add(input.nextInt());
            } else {
                input.next();
            }
        }

        input.close();
        return list;
    }

    /**
     * Measures the execution time of a method
     * 
     * @param method    The method to be measured
     * @param a         The ArrayList of integers
     * @param inputFile The input file (for printing purposes)
     * @return The execution time in milliseconds
     */
    public static double measureExecutionTime(String method, List<Integer> a, String inputFile) {
        long startTime = 0, endTime = 0;
        int majorityElement = -1;

        switch (method) {
            case "boyer-moore":
                startTime = System.nanoTime();
                majorityElement = getMajorityBoyerMooreVoting(a);
                endTime = System.nanoTime();
                break;
            case "divide-conquer":
                startTime = System.nanoTime();
                majorityElement = getMajorityDivideConquer(a);
                endTime = System.nanoTime();
                break;
            default:
                System.out.println("Invalid method");
                break;
        }

        // Print the majority element found by the method
        // -1 represents NO MAJORITY ELEMENT
        if (majorityElement == -1) {
            System.out.println("(" + method + "): No Majority Element");
        } else {
            System.out.println("(" + method + "): " + majorityElement + " is the majority element");
        }

        return (endTime - startTime) / 1_000_000.0;
    }

    /**
     * Finds the majority element in an ArrayList of integers using
     * the divide and conquer method.
     * 
     * @param a The ArrayList of integers
     * @return The majority element
     */
    public static int getMajorityDivideConquer(List<Integer> a) {
        // Base case: If the list is of size 1, then
        // the majority element is the only element.
        if (a.size() == 1) {
            return a.get(0);
        }

        // Divide the list into two sublists.
        List<Integer> leftList = a.subList(0, a.size() / 2);
        List<Integer> rightList = a.subList(a.size() / 2, a.size());

        // Recursively find the majority element of each sublist.
        int leftMajority = getMajorityDivideConquer(leftList);
        int rightMajority = getMajorityDivideConquer(rightList);

        // If the majority elements of each sublist are the same,
        // then that element is the majority element of the list.
        if (leftMajority == rightMajority) {
            return leftMajority;
        }

        // Otherwise, count the number of occurrences of each
        // majority element in the combined list
        int leftCount = Collections.frequency(a, leftMajority);
        int rightCount = Collections.frequency(a, rightMajority);

        // If either majority element occurs more than half
        // the size of the list, then that element is the
        // majority element of the list. Otherwise, there
        // is no majority element.
        if (leftCount >= (a.size() / 2)) {
            return leftMajority;
        } else if (rightCount >= (a.size() / 2)) {
            return rightMajority;
        } else {
            return -1; // -1 represents NO MAJORITY ELEMENT
        }
    }

    /**
     * Finds the majority element in an ArrayList of integers using
     * the Boyer-Moore majority vote algorithm.
     * 
     * @param a The ArrayList of integers
     * @return The majority element
     */
    public static int getMajorityBoyerMooreVoting(List<Integer> a) {
        // The index of the majority element and the count
        // of the majority element.
        //
        // The count can be initialized to 1 because the
        // majority element will always have a count of
        // 1 at the beginning of the list. (Saves an unnecessary
        // iteration through the list)
        int majority_index = 0, count = 1;

        // Iterate through the list.
        // Start at index 1 because the majority element
        // is initialized to the first element.
        for (int i = 1; i < a.size(); i++) {

            // If the current element is the same as the majority
            // element, then increment the count. Otherwise, decrement
            // the count.
            if (a.get(majority_index) == a.get(i)) {
                count++;
            } else {
                count--;
            }

            // If the count is 0, then the majority element
            // is the current element.
            if (count == 0) {
                majority_index = i;
                count = 1;
            }
        }

        return a.get(majority_index);
    }

}
