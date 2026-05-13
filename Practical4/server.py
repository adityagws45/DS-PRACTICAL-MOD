import socket
import threading
from datetime import datetime, timedelta

clients = []
client_times = []

def handle_client(conn, addr):
    print(f"Client Data updated with: {addr[0]} : {addr[1]}")
    clients.append(conn)

    # Receive client time
    client_time_str = conn.recv(1024).decode()
    client_time = datetime.strptime(
        client_time_str, "%Y-%m-%d %H:%M:%S.%f"
    )
    client_times.append((conn, client_time))

def synchronize():
    print("\nNew synchronization cycle started.")
    print(f"Number of clients to be synchronized: {len(client_times)}")

    # Master's current time
    master_time = datetime.now()

    # Calculate average time
    total_time = master_time.timestamp()
    for _, ct in client_times:
        total_time += ct.timestamp()

    avg_time = total_time / (len(client_times) + 1)
    avg_datetime = datetime.fromtimestamp(avg_time)

    print("Recent time sent successfully")

    # Send adjustment to each client
    for conn, ct in client_times:
        diff = avg_datetime - ct
        conn.send(str(diff.total_seconds()).encode())

def start_server():
    server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server.bind(("127.0.0.1", 5000))
    server.listen(5)

    print("Server started...\n")

    threads = []

    # Accept 3 clients
    for _ in range(3):
        conn, addr = server.accept()
        t = threading.Thread(target=handle_client, args=(conn, addr))
        t.start()
        threads.append(t)

    for t in threads:
        t.join()

    synchronize()
    server.close()

if __name__ == "__main__":
    start_server()