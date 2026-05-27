package org.example.util;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server for writing/updating JSON data of characters.
 * Port: 5003 (default)
 * Instances: 30 simultaneous connections
 */
public class ServerJsonWrite implements Runnable {

    private int port;
    private int instances;

    public ServerJsonWrite(int port, int instances) {
        this.port = port;
        this.instances = instances;
    }

    @Override
    public void run() {
        Thread[] threads = new Thread[this.instances];
        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(this.port);
            System.out.println("✅ JSON Write server started on port " + port + ". Waiting for clients...");
        } catch (IOException e) {
            System.out.println("❌ Error starting JSON Write server on port " + port);
            System.out.println("Error: " + e.getMessage());
            return; // ✅ EXIT IF WE CANNOT CREATE THE SERVER
        }

        while (true) {
            try {
                // Clean up finished threads
                for (int i = 0; i < threads.length; i++) {
                    if (threads[i] != null && !threads[i].isAlive()) {
                        threads[i] = null;
                    }
                }

                // Find available slot
                int availableSlot = -1;
                for (int i = 0; i < threads.length; i++) {
                    if (threads[i] == null) {
                        availableSlot = i;
                        break;
                    }
                }

                if (availableSlot == -1) {
                    System.out.println("⚠️ JSON Write server full (" + instances + "/" + instances + " clients). Waiting...");
                    Thread.sleep(500);
                    continue;
                }

                Socket clientSocket = serverSocket.accept();

                JsonWriteThread jsonWriteThread = new JsonWriteThread(clientSocket);
                threads[availableSlot] = new Thread(jsonWriteThread);
                threads[availableSlot].start();

            } catch (IOException e) {
                System.out.println("❌ IO error in ServerJsonWrite: " + e.getMessage());
            } catch (InterruptedException e) {
                System.out.println("⚠️ Thread interrupted: " + e.getMessage());
                Thread.currentThread().interrupt();
                break;
            }
        }

        // ✅ CLOSE RESOURCES ON SHUTDOWN
        try {
            if (!serverSocket.isClosed()) {
                serverSocket.close();
                System.out.println("✅ JSON Write server closed correctly");
            }
        } catch (IOException e) {
            System.out.println("❌ Error closing JSON Write server: " + e.getMessage());
        }
    }
}