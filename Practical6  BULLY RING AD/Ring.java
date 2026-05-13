import java.util.*;

class Process {
    int id;
    String state;
}

public class Ring {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // Enter number of processes
        System.out.print("Enter the number of processes: ");
        int n = in.nextInt();

        Process[] proc = new Process[n];

        // Initialize processes
        for (int i = 0; i < n; i++) {
            proc[i] = new Process();
        }

        // Input process IDs
        for (int i = 0; i < n; i++) {
            System.out.print("Enter the id of process: ");
            proc[i].id = in.nextInt();
            proc[i].state = "active";
        }

        // Sort processes by ID using Bubble Sort
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (proc[j].id > proc[j + 1].id) {
                    int temp = proc[j].id;
                    proc[j].id = proc[j + 1].id;
                    proc[j + 1].id = temp;
                }
            }
        }

        while (true) {
            System.out.println("\n1. Election");
            System.out.println("2. Quit");
            System.out.print("Enter your choice: ");
            int choice = in.nextInt();

            if (choice == 2) {
                System.out.println("Program terminated...");
                break;
            }

            // Process that starts election
            System.out.print("\nEnter the Process number who initiated election: ");
            int init = in.nextInt();

            int pos = -1;

            // Find initiator position
            for (int i = 0; i < n; i++) {
                if (proc[i].id == init) {
                    pos = i;
                    break;
                }
            }

            if (pos == -1) {
                System.out.println("Invalid process!");
                continue;
            }

            int maxId = proc[pos].id;
            int current = pos;

            // Pass message around ring
            do {
                int next = (current + 1) % n;

                System.out.println("Process " + proc[current].id +
                        " sends message to " + proc[next].id);

                if (proc[next].id > maxId) {
                    maxId = proc[next].id;
                }

                current = next;

            } while (current != pos);

            // Highest ID becomes coordinator
            System.out.println("Process " + maxId +
                    " is selected as coordinator");
        }

        in.close();
    }
}