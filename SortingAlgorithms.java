import java.util.Arrays;

public class SortingAlgorithms {
    public static void main(String[] args) {
        int size = 5000;

        // Initialize the array
        int[] insertionArr = new int[size];
        for (int i = 0; i < size; i++) {
            insertionArr[i] = (int) (Math.random() * 50000);
        }

        // Create copies of the array for each sorting algorithm
        int[] selectionArr = insertionArr.clone();
        int[] bubbleArr = insertionArr.clone();
        int[] mergeArr = insertionArr.clone();

        // Evaluate the insertion sort algorithm
        long startTime = System.nanoTime();
        insertionSort(insertionArr);
        long endTime = System.nanoTime();

        System.out.println("Insertion Sort: " + (endTime - startTime) / 1000000.0 + " ms");
        for (int i = 0; i < 100; i++) {
            System.out.print(insertionArr[i] + " ");
        }
        System.out.println();

        // Evaluate the selection sort algorithm
        startTime = System.nanoTime();
        selectionSort(selectionArr);
        endTime = System.nanoTime();

        System.out.println("Selection Sort: " + (endTime - startTime) / 1000000.0 + "ms");
        for (int i = 0; i < 100; i++) {
            System.out.print(selectionArr[i] + " ");
        }
        System.out.println();

        // Evaluate the bubble sort algorithm
        startTime = System.nanoTime();
        bubbleSort(bubbleArr);
        endTime = System.nanoTime();

        System.out.println("Bubble Sort: " + (endTime - startTime) / 1000000.0 + "ms");
        for (int i = 0; i < 100; i++) {
            System.out.print(bubbleArr[i] + " ");
        }
        System.out.println();

        // Evaluate the merge sort algorithm
        startTime = System.nanoTime();
        mergeSort(mergeArr);
        endTime = System.nanoTime();

        System.out.println("Merge Sort: " + (endTime - startTime) / 1000000.0 + "ms");
        for (int i = 0; i < 100; i++) {
            System.out.print(mergeArr[i] + " ");
        }
        System.out.println();
    }

    /**
     * Sorts an array using the insertion sort algorithm
     * 
     * @param arr The array to sort
     */
    public static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int temp = arr[i]; // Store the current element
            int j = i - 1; // Start comparing with the previous element

            // Shift all elements greater than the current element to the right
            while (j >= 0 && arr[j] > temp) {
                arr[j + 1] = arr[j];
                j--;
            }

            // Insert the current element into the correct position
            arr[j + 1] = temp;
        }
    }

    /**
     * Sorts an array using the selection sort algorithm
     * 
     * @param arr The array to sort
     */
    public static void selectionSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int biggestIndex = 0;

            // Find the biggest element in the unsorted portion
            for (int j = 1; j < arr.length - i; j++) {
                if (arr[j] > arr[biggestIndex]) {
                    biggestIndex = j;
                }
            }

            // Swap the biggest element with the last element in the unsorted portion
            int temp = arr[arr.length - i - 1];
            arr[arr.length - i - 1] = arr[biggestIndex];
            arr[biggestIndex] = temp;
        }
    }

    /**
     * Sorts an array using the bubble sort algorithm
     * 
     * @param arr The array to sort
     */
    public static void bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            boolean swapped = false;

            // Compare each element with the next element and swap if necessary
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j + 1] < arr[j]) {
                    // Swap the elements
                    int temp = arr[j + 1];
                    arr[j + 1] = arr[j];
                    arr[j] = temp;

                    swapped = true;
                }
            }

            // If no swaps were made, the array is sorted
            if (!swapped) {
                break;
            }
        }
    }

    /**
     * Sorts an array using the merge sort algorithm
     * 
     * @param arr The array to sort
     */
    public static void mergeSort(int[] arr) {
        // Base case
        if (arr.length == 1) {
            return;
        }

        // Split the array into two halves
        int[] left = new int[arr.length / 2];
        for (int i = 0; i < left.length; i++) {
            left[i] = arr[i];
        }

        int[] right = new int[arr.length - left.length];
        for (int i = 0; i < right.length; i++) {
            right[i] = arr[i + left.length];
        }

        // Sort each half
        mergeSort(left);
        mergeSort(right);

        // Merge the two halves
        int leftIndex = 0;
        int rightIndex = 0;
        int arrIndex = 0;

        // Compare the elements of the two halves and add the smaller one to the array
        while (leftIndex < left.length && rightIndex < right.length) {
            if (left[leftIndex] < right[rightIndex]) {
                arr[arrIndex] = left[leftIndex];
                leftIndex++;
            } else {
                arr[arrIndex] = right[rightIndex];
                rightIndex++;
            }
            arrIndex++;
        }

        // Copy remaining elements of left[] if any
        while (leftIndex < left.length) {
            arr[arrIndex] = left[leftIndex];
            leftIndex++;
            arrIndex++;
        }

        // Copy remaining elements of right[] if any
        while (rightIndex < right.length) {
            arr[arrIndex] = right[rightIndex];
            rightIndex++;
            arrIndex++;
        }
    }
}
