import socket
from datetime import datetime, timedelta

def start_client():
    # Create socket
    client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    # Connect to server
    client.connect(("127.0.0.1", 5000))

    # Send local time to server
    local_time = datetime.now()
    print("Local time before synchronization :", local_time)

    client.send(str(local_time).encode())

    # Receive adjustment (difference in seconds)
    diff = float(client.recv(1024).decode())

    # Adjust local time
    new_time = local_time + timedelta(seconds=diff)

    print("Synchronized time at the client is :", new_time)

    # Close connection
    client.close()

if __name__ == "__main__":
    start_client()