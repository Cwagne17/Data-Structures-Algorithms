/**
 * This problem is an exercise in the implementation of queue. We will look at
 * the implementation of a circular array. Then we will use the implementation
 * to solve a problem called the Josephus problem.
 * Josephus Flavius was a famous historian of the first century at the time of
 * the Second Temple destruction. During the war he got trapped in a cave with a
 * group of 39 soldiers surrounded by Romans. The legend has it that preferring
 * suicide to capture, the people decided to form a circle and, proceeding
 * clockwise around it, to kill every seventh (It will be given as an input)
 * person until only one was left, who must then commit suicide. Josephus, an
 * accomplished mathematician, quickly found the safe spot in the circle (24th)
 * to be the last to go. But when the time came, instead of killing himself he
 * joined the Roman side. The problem rightfully raises the question of how
 * someone might be able to quickly compute the correct place to stand.
 * 
 * In this assignment you are to simulate the Josephus problem.
 * Input data (interactively input by keyboard):
 * 
 * How many soldiers? ➔ 10
 * Type 10 soldiers name:
 * Andy
 * Shawna
 * Jianjia
 * Adam
 * Jacob
 * Wesley
 * Zahari
 * Thomas
 * Chris
 * Ben
 * 
 * The program should simulate the Josephus problem by repeatedly removing the
 * n-th name from the list and displaying it. At the end, display the name of
 * the survivor. For the example above, your output should be:
 * 
 * Enter the position ➔ 3
 * Eliminating order:
 * 1. Jianjia (3)
 * 2. Wesley(6)
 * 3. Chris(9)
 * 4. Shawna(2)
 * 5. Zahari(7)
 * 6. Andy(1)
 * 7. Thomas(8)
 * 8. Jacob(5)
 * 9. Ben(10)
 * (Note: The number of the parenthesis is an original spot number.)
 * The survivor is Adam(4).
 */

public class JosephusProblem {
    public class CircularQueue {
        String[] queue;
        int frontIndex;
        int backIndex;

        public CircularQueue(int size) {
            // Since N - 1 is how the queue is being treated as the available size
            // we are adding 1 to the size to account for this internal implementation
            queue = new String[size + 1];
            frontIndex = 0;
            backIndex = 0;
        }

        public void enqueue(String name) {
            if (isFull()) {
                System.out.println("Queue is full");
                return;
            }
            queue[backIndex] = name;
            backIndex = (backIndex + 1) % queue.length;
        }

        public String dequeue() {
            if (isEmpty()) {
                System.out.println("Queue is empty");
                return null;
            }
            String name = queue[frontIndex];
            queue[frontIndex] = null;
            frontIndex = (frontIndex + 1) % queue.length;
            return name;
        }

        public boolean isEmpty() {
            return (backIndex + 1) % queue.length == (frontIndex + 1) % queue.length;
        }

        public boolean isFull() {
            return (backIndex + 1) % queue.length == frontIndex;
        }

    }

    public static void main(String[] args) {
        // List of names
        // TO_DO: Get the list of names from stdin
        String[] nameList = { "Andy", "Shawna", "Jianjia", "Adam", "Jacob", "Wesley", "Zahari", "Thomas", "Chris",
                "Ben" };

        // Create a queue
        CircularQueue queue = new JosephusProblem().new CircularQueue(nameList.length);

        // Initialize the queue
        for (String name : nameList) {
            queue.enqueue(name);
        }

        // TO_DO: Get the number of the n-th person to be eliminated from stdin
        int n = 3;

        while (!queue.isEmpty()) {
            // Dequeue than enqueue n - 1 times. This will move the n-th person to the front
            // of the queue
            for (int i = 0; i < n - 1; i++) {
                queue.enqueue(queue.dequeue());
            }

            // Dequeue the n-th person
            String name = queue.dequeue();
            if (queue.isEmpty()) {
                System.out.printf("The survivor is %s\n", name);
                break;
            }
            System.out.printf("Eliminated: %s\n", name);
        }
    }
}