import java.util.*;

class TokenRing {
    public static void main(String args[]) {
        Scanner scan = new Scanner(System.in);

        System.out.print("Enter the number of nodes: ");
        int n = scan.nextInt();

        int token = 0;

        // Display ring
        System.out.println("\nLogical Ring:");
        for (int i = 0; i < n; i++) {
            System.out.print(i + " ");
        }
        System.out.println("0");

        // Input sender, receiver, and data
        System.out.print("\nEnter sender: ");
        int s = scan.nextInt();

        System.out.print("Enter receiver: ");
        int r = scan.nextInt();

        System.out.print("Enter data: ");
        int data = scan.nextInt();

        // Token passing
        System.out.print("\nToken passing: ");
        int i = token;
        while (i != s) {
            System.out.print(i + " -> ");
            i = (i + 1) % n;
        }
        System.out.println(s);

        // Sender sends data
        System.out.println("Sender " + s + " sending data: " + data);

        // Forwarding data
        i = (s + 1) % n;
        while (i != r) {
            System.out.println("Data " + data + " forwarded by " + i);
            i = (i + 1) % n;
        }

        // Receiver receives data
        System.out.println("Receiver " + r + " received data: " + data);

        scan.close();
    }
}