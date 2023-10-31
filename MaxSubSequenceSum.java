import java.util.Random;

public class MaxSubSequenceSum {
    public static void main(String[] args) {
        int[] problem_sizes = { 5000, 10000, 50000, 100000, 200000 };
        for (int n : problem_sizes) {
            int[] a = createArray(n);

            measureExecutionTime(1, a);
            measureExecutionTime(2, a);
            measureExecutionTime(3, a);
            measureExecutionTime(4, a);

            // Print a new line for readability
            System.out.println();
        }
    }

    public static int[] createArray(int n) {
        Random rand = new Random();
        int[] a = new int[n];

        System.out.printf("Creating array of size %d...\n", n);

        for (int i = 0; i < n; i++) {
            a[i] = rand.nextInt(-5000, 5001);
        }

        return a;
    }

    /**
     * Measures the execution time of a method
     * 
     * @param method The method to be measured
     * @param a      The list of integers
     * @return
     */
    public static void measureExecutionTime(int method, int[] a) {
        long startTime = 0, endTime = 0;
        int maxSum = 0;

        switch (method) {
            case 1:
                startTime = System.nanoTime();
                maxSum = maxSubSeq1(a);
                endTime = System.nanoTime();
                break;
            case 2:
                startTime = System.nanoTime();
                maxSum = maxSubSeq2(a);
                endTime = System.nanoTime();
                break;
            case 3:
                startTime = System.nanoTime();
                maxSum = maxSubSeq3(a, 0, a.length - 1);
                endTime = System.nanoTime();
                break;
            case 4:
                startTime = System.nanoTime();
                maxSum = maxSubSeq4(a);
                endTime = System.nanoTime();
                break;
            default:
                System.out.println("Invalid method");
                break;
        }

        System.out.println("Algorithm " + method + ": found " + maxSum + " as the maximum in "
                + (endTime - startTime) / 1_000_000.0 + " miliseconds.");
    }

    /**
     * Cubic maximum contiguous subsequence sum algorithm.
     * 
     * @param a array of integers
     * @return the maximum sum
     */
    public static int maxSubSeq1(int[] a) {
        int maxSum = 0;

        for (int i = 0; i < a.length; i++) {
            for (int j = i; j < a.length; j++) {
                int localSum = 0;

                for (int k = i; k <= j; k++) {
                    localSum += a[k];
                }

                if (localSum > maxSum) {
                    maxSum = localSum;
                }
            }
        }

        return maxSum;
    }

    /**
     * Quadratic maximum contiguous subsequence sum algorithm.
     * 
     * @param a array of integers
     * @return the maximum sum
     */
    public static int maxSubSeq2(int[] a) {
        int maxSum = 0;

        for (int i = 0; i < a.length; i++) {
            int localSum = 0;

            for (int j = i; j < a.length; j++) {
                localSum += a[j];

                if (localSum > maxSum) {
                    maxSum = localSum;
                }
            }
        }

        return maxSum;
    }

    /**
     * Recursive maximum contiguous subsequence sum algorithm.
     * Finds maximum sum in subarray spanning a[left..right].
     * Does not attempt to maintain actual best sequence.
     * 
     * @param a     array of integers
     * @param left  left-most index of subarray
     * @param right right-most index of subarray
     * @return the maximum sum
     */
    public static int maxSubSeq3(int[] a, int left, int right) {
        if (left == right) { // Base case
            if (a[left] > 0) {
                return a[left];
            }
            return 0;
        }

        int center = (left + right) / 2;
        int maxLeftSum = maxSubSeq3(a, left, center);
        int maxRightSum = maxSubSeq3(a, center + 1, right);

        int maxLeftBorderSum = 0, leftBorderSum = 0;
        for (int i = center; i >= left; i--) {
            leftBorderSum += a[i];
            if (leftBorderSum > maxLeftBorderSum) {
                maxLeftBorderSum = leftBorderSum;
            }
        }

        int maxRightBorderSum = 0, rightBorderSum = 0;
        for (int i = center + 1; i <= right; i++) {
            rightBorderSum += a[i];
            if (rightBorderSum > maxRightBorderSum) {
                maxRightBorderSum = rightBorderSum;
            }
        }

        return Math.max(maxLeftSum, Math.max(maxRightSum, maxLeftBorderSum + maxRightBorderSum));
    }

    /**
     * Linear-time maximum contiguous subsequence sum algorithm.
     * 
     * @param a array of integers
     * @return the maximum sum
     */
    public static int maxSubSeq4(int[] a) {
        int maxSum = 0, localSum = 0;

        for (int i = 0; i < a.length; i++) {
            localSum += a[i];

            if (localSum > maxSum) {
                maxSum = localSum;
            } else if (localSum < 0) {
                localSum = 0;
            }
        }

        return maxSum;
    }

}
