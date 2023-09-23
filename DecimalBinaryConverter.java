
/**
 * Author: Christopher Wagner
 * Programming Assignment 1 - Problem 2
 * Date: 9/23/2023
 * 
 * This program converts a decimal number to a binary number.
 * The program prompts the user to enter a decimal number and
 * then prints the binary number.
 * 
 * Inputs: A decimal number from stdin
 * Outputs: Prints the binary number to stdout
 * 
 * Procedures Called:
 *    decimalToBinary(int decimal)
 */

import java.util.Scanner;

public class DecimalBinaryConverter {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Prompt the user to enter a decimal number
        System.out.print("Enter a decimal number : ");
        int decimal = input.nextInt();

        // Closes the scanner to prevent resource leak
        input.close();

        // Convert the decimal number to a binary number
        String binaryNumber = decimalToBinary(decimal);

        // Prints the binary number
        System.out.println("The binary number is " + binaryNumber);
    }

    /**
     * A recursive function that converts a decimal number to a binary number.
     * 
     * @param decimal - The decimal number to be converted
     * @return The binary number
     */
    public static String decimalToBinary(int decimal) {
        // Base case: If the decimal number is 0, then the function will return
        if (decimal == 0) {
            return "";
        }

        if (decimal % 2 == 0) {
            // If the decimal number is even, then the binary number is the
            // binary number of the decimal number divided by 2 with a 0
            // appended to the end. The function will return the binary number.
            return decimalToBinary(decimal / 2) + "0";
        } else {
            // If the decimal number is odd, then the binary number is the
            // binary number of the decimal number divided by 2 with a 1
            // appended to the end. The function will return the binary number.
            return decimalToBinary(decimal / 2) + "1";
        }

    }

}