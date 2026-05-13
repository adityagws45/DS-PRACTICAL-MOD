import mpi.MPI;

public class ScatterGather {
    public static void main(String[] args) {

        // Initialize MPI
        MPI.Init(args);

        // Get process rank and total number of processes
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        int root = 0;

        // Buffer to hold data at root
        int sendbuf[] = null;
        sendbuf = new int[size];

        // Root process initializes data
        if (rank == root) {
            sendbuf[0] = 10;
            sendbuf[1] = 20;
            sendbuf[2] = 30;
            sendbuf[3] = 40;

            System.out.print("Processor " + rank + " has data: ");
            for (int i = 0; i < size; i++) {
                System.out.print(sendbuf[i] + " ");
            }
            System.out.println();
        }

        // Each process receives one element
        int recvbuf[] = new int[1];

        // Scatter operation
        MPI.COMM_WORLD.Scatter(
                sendbuf, 0, 1, MPI.INT,
                recvbuf, 0, 1, MPI.INT,
                root
        );

        // Display received value
        System.out.println("Processor " + rank +
                " has data: " + recvbuf[0]);

        // Perform computation
        System.out.println("Processor " + rank +
                " is doubling the data");

        recvbuf[0] = recvbuf[0] * 2;

        // Gather operation
        MPI.COMM_WORLD.Gather(
                recvbuf, 0, 1, MPI.INT,
                sendbuf, 0, 1, MPI.INT,
                root
        );

        // Root displays final result
        if (rank == root) {
            System.out.println("Process 0 has data: ");
            for (int i = 0; i < 4; i++) {
                System.out.print(sendbuf[i] + " ");
            }
            System.out.println();
        }

        // End MPI
        MPI.Finalize();set MPJ_HOME=D:\All Downloads from 9 may\mpj-v0_44\mpj-v0_44
    }
}