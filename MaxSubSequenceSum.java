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
        }
    }

    public static int[] createArray(int n) {
        Random rand = new Random();
        int[] a = new int[n];

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
                maxSum = maxSubSeq3(a);
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

    public static int maxSubSeq1(int[] a) {
        int maxSum = 0;

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                int localSum = 0;

                for (int k = 0; k <= j; k++) {
                    localSum += a[k];
                }

                if (localSum > maxSum) {
                    maxSum = localSum;
                }
            }
        }

        return maxSum;
    }

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

    public static int maxSubSeq3(int[] a) {
        return 0;
    }

    public static int maxSubSeq4(int[] a) {
        return 0;
    }

}
