
/**
 * Author: Christopher Wagner
 * Programming Assignment 1 - Problem 3
 * Date: 9/25/2023
 * 
 * This program finds words from a wordlist in a given n x n puzzle input.
 * The puzzle input is given as a text file, and the wordlist is given as
 * a text file.
 * 
 * Inputs: None, but the program will read from the input files defined
 * 
 * Outputs:
 *  The puzzle solution will be printed to the console.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class WordPuzzle {
    public static void main(String[] args) {
        // Define the file paths for the input files
        String puzzleInputFilePath = "input/puzzleinput.txt";
        String wordlistFilePath = "input/wordlist.txt";

        try {
            // Parse the input files
            ArrayList<String> wordlist = parseWordlistFile(wordlistFilePath);
            char[][] puzzleInput = parsePuzzleInputFile(puzzleInputFilePath);

            // Find the words in the puzzle input
            ArrayList<CharCoordinate> foundWords = findWords(puzzleInput, wordlist);

            // Print the found words in the puzzle input
            printFoundWords(foundWords, puzzleInput.length);
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
        }

    }

    /**
     * Parses the wordlist input file
     * 
     * @param filePath the relative path to the input file
     * @return a list of words found in the wordlist file
     * @throws FileNotFoundException
     */
    public static ArrayList<String> parseWordlistFile(String filePath) throws FileNotFoundException {
        Scanner input = new Scanner(new File(filePath));

        ArrayList<String> wordlist = new ArrayList<String>();
        while (input.hasNextLine()) {
            wordlist.add(input.nextLine().toLowerCase());
        }

        return wordlist;
    }

    /**
     * Parses an input file as the format of a NxN matrix.
     * 
     * This function is opinioniated on how the file should be formatted.
     * It makes the assumption that the puzzle input file should have as
     * many columns as there are rows. If a row is only accounted for if
     * it is not equal just a newline character. If a given row has less
     * than n-columns, where n represents the number of rows, then an
     * Error is thrown.
     * 
     * @param filePath
     * @return
     * @throws FileNotFoundException
     */
    public static char[][] parsePuzzleInputFile(String filePath) throws FileNotFoundException {
        Scanner input = new Scanner(new File(filePath));

        int rowCounter = 0;
        while (input.hasNextLine()) {
            if (input.nextLine() != "\n") {
                rowCounter++;
            }
        }
        input.close();

        char[][] puzzleInput = new char[rowCounter][rowCounter];

        // Reinstantiate the scanner to the beginning of the file
        input = new Scanner(new File(filePath));

        for (int i = 0; i < rowCounter; i++) {
            String[] row = input.nextLine().split("");
            if (row.length != rowCounter) {
                input.close();
                throw new Error("Row " + i + " does not have the same number of columns as there are rows.");
            }
            puzzleInput[i] = String.join("", row).toLowerCase().toCharArray();
        }
        input.close();

        return puzzleInput;
    }

    /**
     * Prints the found words in the puzzle input.
     * 
     * @param foundWords The list of words found in the puzzle input.
     * @param n          The number of rows/columns in the puzzle input.
     */
    public static void printFoundWords(ArrayList<CharCoordinate> foundWords, int n) {
        char[][] solution = new char[n][n];

        // Initialize the puzzle input found words
        for (CharCoordinate coordinate : foundWords) {
            solution[coordinate.x][coordinate.y] = coordinate.letter;
        }

        // Iterate throug the rows
        for (char[] row : solution) {

            // Iterate through the columns
            for (char letter : row) {

                // If the letter is null, then print a space
                if (letter == '\u0000') {
                    System.out.print(" ");
                } else {
                    System.out.print(letter);
                }
            }
            System.out.println();
        }
    }

    /**
     * A class to represent a character coordinate in the puzzle input.
     */
    class CharCoordinate {
        private char letter;
        private int x;
        private int y;

        public CharCoordinate(char letter, int x, int y) {
            this.letter = letter;
            this.x = x;
            this.y = y;
        }
    }

    /**
     * Finds all the words in the puzzle input.
     * 
     * @param puzzleInput The puzzle input as a NxN matrix.
     * @param wordlist    The list of words to find in the puzzle input.
     * @return A list of CharCoordinates that represent the found words in the
     */
    public static ArrayList<CharCoordinate> findWords(char[][] puzzleInput, ArrayList<String> wordlist) {
        // Represents the coordinates for each character in the found words
        ArrayList<CharCoordinate> foundWords = new ArrayList<CharCoordinate>();

        for (String word : wordlist) {
            // The length of the word minus 1 since the index starts at 0
            int wordLength = word.length() - 1;

            for (int i = 0; i < puzzleInput.length; i++) {
                for (int j = 0; j < puzzleInput[i].length; j++) {
                    CharCoordinate[] coordinates;

                    // If the first letter of the word is found in the puzzle input
                    if (puzzleInput[i][j] == word.charAt(0)) {

                        // Check the right

                        if (j + wordLength < puzzleInput.length) {
                            boolean found = true;
                            coordinates = new CharCoordinate[word.length()];

                            // Continue checking the letters to the right
                            for (int k = 0; k < word.length(); k++) {

                                // Add the current letter to the coordinates
                                coordinates[k] = new WordPuzzle().new CharCoordinate(word.charAt(k), i, j + k);

                                // If the letters do not match, then the word is not found
                                if (puzzleInput[i][j + k] != word.charAt(k)) {
                                    found = false;
                                    break;
                                }
                            }

                            if (found) {
                                for (CharCoordinate c : coordinates)
                                    foundWords.add(c);
                            }
                        }

                        // Check the left
                        if (j - wordLength >= 0) {
                            boolean found = true;
                            coordinates = new CharCoordinate[word.length()];

                            // Continue checking the letters to the left
                            for (int k = 0; k < word.length(); k++) {
                                // Add the current letter to the coordinates
                                coordinates[k] = new WordPuzzle().new CharCoordinate(word.charAt(k), i, j - k);

                                // If the letters do not match, then the word is not found
                                if (puzzleInput[i][j - k] != word.charAt(k)) {
                                    found = false;
                                    break;
                                }
                            }

                            if (found) {
                                for (CharCoordinate c : coordinates)
                                    foundWords.add(c);
                            }
                        }

                        // Check the down
                        if (i + wordLength < puzzleInput.length) {
                            boolean found = true;
                            coordinates = new CharCoordinate[word.length()];

                            // Continue checking the letters down
                            for (int k = 0; k < word.length(); k++) {
                                // Add the current letter to the coordinates
                                coordinates[k] = new WordPuzzle().new CharCoordinate(word.charAt(k), i + k, j);

                                // If the letters do not match, then the word is not found
                                if (puzzleInput[i + k][j] != word.charAt(k)) {
                                    found = false;
                                    break;
                                }
                            }

                            if (found) {
                                for (CharCoordinate c : coordinates)
                                    foundWords.add(c);
                            }
                        }

                        // Check the up
                        if (i - wordLength >= 0) {
                            boolean found = true;
                            coordinates = new CharCoordinate[word.length()];

                            // Continue checking the letters to the up
                            for (int k = 0; k < word.length(); k++) {
                                // Add the current letter to the coordinates
                                coordinates[k] = new WordPuzzle().new CharCoordinate(word.charAt(k), i - k, j);

                                // If the letters do not match, then the word is not found
                                if (puzzleInput[i - k][j] != word.charAt(k)) {
                                    found = false;
                                    break;
                                }
                            }

                            if (found) {
                                for (CharCoordinate c : coordinates)
                                    foundWords.add(c);
                            }
                        }

                        // Check the down-right
                        if (i + wordLength < puzzleInput.length && j + wordLength < puzzleInput.length) {
                            boolean found = true;
                            coordinates = new CharCoordinate[word.length()];

                            // Continue checking the letters to the down-right
                            for (int k = 0; k < word.length(); k++) {
                                // Add the current letter to the coordinates
                                coordinates[k] = new WordPuzzle().new CharCoordinate(word.charAt(k), i + k, j + k);

                                // If the letters do not match, then the word is not found
                                if (puzzleInput[i + k][j + k] != word.charAt(k)) {
                                    found = false;
                                    break;
                                }
                            }

                            if (found) {
                                for (CharCoordinate c : coordinates)
                                    foundWords.add(c);
                            }
                        }

                        // Check the down-left
                        if (i + wordLength < puzzleInput.length && j - wordLength >= 0) {
                            boolean found = true;
                            coordinates = new CharCoordinate[word.length()];

                            // Continue checking the letters to the down-left
                            for (int k = 0; k < word.length(); k++) {
                                // Add the current letter to the coordinates
                                coordinates[k] = new WordPuzzle().new CharCoordinate(word.charAt(k), i + k, j - k);

                                // If the letters do not match, then the word is not found
                                if (puzzleInput[i + k][j - k] != word.charAt(k)) {
                                    found = false;
                                    break;
                                }
                            }

                            if (found) {
                                for (CharCoordinate c : coordinates)
                                    foundWords.add(c);
                            }
                        }

                        // Check the up-right
                        if (i - wordLength >= 0 && j + wordLength < puzzleInput.length) {
                            boolean found = true;
                            coordinates = new CharCoordinate[word.length()];

                            // Continue checking the letters to the up-right
                            for (int k = 0; k < word.length(); k++) {
                                // Add the current letter to the coordinates
                                coordinates[k] = new WordPuzzle().new CharCoordinate(word.charAt(k), i - k, j + k);

                                // If the letters do not match, then the word is not found
                                if (puzzleInput[i - k][j + k] != word.charAt(k)) {
                                    found = false;
                                    break;
                                }
                            }

                            if (found) {
                                for (CharCoordinate c : coordinates)
                                    foundWords.add(c);
                            }
                        }

                        // Check the up-left
                        if (i - wordLength >= 0 && j - wordLength >= 0) {
                            boolean found = true;
                            coordinates = new CharCoordinate[word.length()];

                            // Continue checking the letters to the up-left
                            for (int k = 0; k < word.length(); k++) {
                                // Add the current letter to the coordinates
                                coordinates[k] = new WordPuzzle().new CharCoordinate(word.charAt(k), i - k, j - k);

                                // If the letters do not match, then the word is not found
                                if (puzzleInput[i - k][j - k] != word.charAt(k)) {
                                    found = false;
                                    break;
                                }
                            }

                            if (found) {
                                for (CharCoordinate c : coordinates)
                                    foundWords.add(c);
                            }
                        }
                    }
                }
            }
        }
        return foundWords;
    }
}
