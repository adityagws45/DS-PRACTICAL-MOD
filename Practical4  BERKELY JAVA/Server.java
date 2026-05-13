import java.io.*;
import java.net.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class Server {
    public static void main(String[] args) throws Exception {

        // Create server socket on port 5000
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Server started...");

        List<Socket> clients = new ArrayList<>();
        List<Long> times = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        // Accept 3 clients
        for (int i = 0; i < 3; i++) {
            Socket socket = serverSocket.accept();
            clients.add(socket);
            System.out.println("Client connected");
        }

        // Receive time from each client
        for (Socket s : clients) {
            DataInputStream in = new DataInputStream(s.getInputStream());

            long clientTime = in.readLong();
            times.add(clientTime);

            System.out.println(
                "Received client time: " +
                sdf.format(new Date(clientTime))
            );
        }

        // Get server's own current time
        long serverTime = System.currentTimeMillis();
        times.add(serverTime);

        System.out.println(
            "Server time: " +
            sdf.format(new Date(serverTime))
        );

        // Calculate average time
        long sum = 0;
        for (long t : times) {
            sum += t;
        }

        long avg = sum / times.size();

        System.out.println(
            "Average Time: " +
            sdf.format(new Date(avg))
        );

        // Send adjustment to each client
        for (int i = 0; i < clients.size(); i++) {
            Socket s = clients.get(i);
            DataOutputStream out =
                new DataOutputStream(s.getOutputStream());

            long diff = avg - times.get(i);

            out.writeLong(diff);

            System.out.println(
                "Sent adjustment to client " +
                (i + 1) +
                ": " +
                diff +
                " ms"
            );

            s.close();
        }

        // Close server socket
        serverSocket.close();
    }
}