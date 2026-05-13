import java.util.Scanner;

public class Bully {
    static boolean[] state = new boolean[5]; // true = process is active
    static int coordinator;

    // Bring a process up and hold election
    public static void up(int up) {
        if (up < 1 || up > 5) {
            System.out.println("Invalid process number!");
            return;
        }

        if (state[up - 1]) {
            System.out.println("Process " + up + " is already up");
        } else {
            state[up - 1] = true;
            System.out.println("Process " + up + " held election");

            // Send election message to higher-numbered processes
            for (int i = up; i < 5; i++) {
                System.out.println("Election message sent from process " +
                        up + " to process " + (i + 1));
            }

            // Highest active process becomes coordinator
            for (int i = 4; i >= 0; i--) {
                if (state[i]) {
                    coordinator = i + 1;
                    break;
                }
            }
        }

        System.out.println("Process " + coordinator + " is coordinator");
    }

    // Bring a process down
    public static void down(int down) {
        if (down < 1 || down > 5) {
            System.out.println("Invalid process number!");
            return;
        }

        if (!state[down - 1]) {
            System.out.println("Process " + down + " is already down");
        } else {
            state[down - 1] = false;
            System.out.println("Process " + down + " is now down");
        }
    }

    // Send message
    public static void message(int mess) {
        if (mess < 1 || mess > 5) {
            System.out.println("Invalid process number!");
            return;
        }

        if (!state[mess - 1]) {
            System.out.println("Process " + mess + " is down");
            return;
        }

        if (mess == coordinator) {
            System.out.println("OK");
        } else {
            System.out.println("Process " + mess + " election");

            for (int i = mess; i < 5; i++) {
                System.out.println("Election sent from process " +
                        mess + " to process " + (i + 1));
            }

            // Highest active process becomes coordinator
            for (int i = 4; i >= 0; i--) {
                if (state[i]) {
                    coordinator = i + 1;
                    break;
                }
            }

            System.out.println("Coordinator message sent from process "
                    + coordinator + " to all");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Initially all processes are active
        for (int i = 0; i < 5; i++) {
            state[i] = true;
        }

        coordinator = 5;

        System.out.println("Processes up = p1 p2 p3 p4 p5");
        System.out.println("Process 5 is coordinator");

        while (true) {
            System.out.println("\n1. Up a process");
            System.out.println("2. Down a process");
            System.out.println("3. Send a message");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Bring up which process: ");
                    int up = sc.nextInt();
                    up(up);
                    break;

                case 2:
                    System.out.print("Bring down which process: ");
                    int down = sc.nextInt();
                    down(down);
                    break;

                case 3:
                    System.out.print("Which process will send message: ");
                    int msg = sc.nextInt();
                    message(msg);
                    break;

                case 4:
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}