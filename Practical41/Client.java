import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Client {
    public static void main(String[] args) throws Exception {

        // Connect to server
        Socket socket = new Socket("localhost", 5000);

        DataOutputStream out =
            new DataOutputStream(socket.getOutputStream());

        DataInputStream in =
            new DataInputStream(socket.getInputStream());

        SimpleDateFormat sdf =
            new SimpleDateFormat("HH:mm:ss");

        // Simulated local time with random offset (0 to 10 sec)
        long localTime =
            System.currentTimeMillis()
            + (long)(Math.random() * 10000);

        System.out.println(
            "Client local time: " +
            sdf.format(new Date(localTime))
        );

        // Send local time to server
        out.writeLong(localTime);

        // Receive adjustment from server
        long adjustment = in.readLong();

        // Apply adjustment
        long newTime = localTime + adjustment;

        System.out.println(
            "Adjustment received: " +
            adjustment +
            " ms"
        );

        System.out.println(
            "Adjusted time: " +
            sdf.format(new Date(newTime))
        );

        // Close socket
        socket.close();
    }
}