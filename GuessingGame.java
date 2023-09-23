
/**
 * Author: Christopher Wagner
 * Programming Assignment 1 - Problem 1
 * Date: 9/23/2023
 * 
 * This program will generate a random integer from 0 to 1,000,000.
 * The program will then guess the number using a recursive function
 * that will either print found, lower, or higher. The program will
 * then print the number of guesses made to find the number. This
 * process will be repeated 100 times and the average number of
 * guesses will be printed.
 * 
 * Inputs: None
 * Outputs: Prints lower, higher, found, the number of guesses made,
 *         and the average number of guesses made.
 * 
 * Procedures Called:
 *     guess(int value, int lower_bound, int upper_bound)
 *
 */

import java.util.Random;

public class GuessingGame {
    public static void main(String[] args) {
        Random r = new Random();
        int sumGuesses = 0;

        for (int i = 0; i < 100; i++) {
            // nextInt will generate a random integer from 0 (inclusive)
            // to bound (exclusive). This is why 1,000,001 is chosen.
            int bound = 1_000_001;
            int randNumber = r.nextInt(bound);

            int guesses = guess(randNumber, 0, bound);
            sumGuesses += guesses;

            System.out.println("The number of guesing trial is " + guesses);
        }

        System.out.println("The average number of guesses is " + sumGuesses / 100);

    }

    /**
     * A recursive function that finds the integer number from
     * an integer guess passed to the function. The function
     * will either print found, lower, or higher.
     * 
     * @param value       - The value to be found
     * @param lower_bound - The lower bound of the guess
     * @param upper_bound - The upper bound of the guess
     * @return The number of guesses made to find the value
     */
    public static int guess(int value, int lower_bound, int upper_bound) {
        // The guess is the middle of the lower and upper bound
        int guess = lower_bound + (upper_bound - lower_bound) / 2;

        // Base case: If the value is the equalivent to the guess
        // then the "Found!" response may be print. The function
        // will return another 1 to be added to the sum of guesses
        // made to find the value.
        if (value == guess) {
            System.out.println("The number is found. It is " + guess);
            return 1;
        }

        if (value < guess) {

            // If the value is less than the guess, then the guess is
            // too high. The function will return another 1 to be added
            // to the sum of guesses made to find the value. The function
            // will also call itself with the lower bound being the same
            // and the upper bound being the guess.
            System.out.println("The number is lower than " + guess);
            return guess(value, lower_bound, guess) + 1;
        } else {

            // If the value is greater than the guess, then the guess is
            // too low. The function will return another 1 to be added
            // to the sum of guesses made to find the value. The function
            // will also call itself with the lower bound being the guess
            // and the upper bound being the same.
            System.out.println("The number is higher than " + guess);
            return guess(value, guess, upper_bound) + 1;
        }
    }
}