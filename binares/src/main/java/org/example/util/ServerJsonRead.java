package org.example.util;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server for reading JSON data of characters.
 * Port: 5002 (default)
 * Instances: 30 simultaneous connections
 */
public class ServerJsonRead implements Runnable {
    private int port;
    private int instances;

    public ServerJsonRead(int port, int instances) {
        this.port = port;
        this.instances = instances;
    }

    @Override
    public void run() {
        Thread[] threads = new Thread[this.instances];
        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(this.port);
            System.out.println("✅ JSON Read server started on port " + port + ". Waiting for clients...");
        } catch (IOException e) {
            System.out.println("❌ Error starting JSON Read server on port " + port);
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
                    System.out.println("⚠️ JSON Read server full (" + instances + "/" + instances + " clients). Waiting...");
                    Thread.sleep(500);
                    continue;
                }

                Socket clientSocket = serverSocket.accept();

                JsonReadThread jsonReadThread = new JsonReadThread(clientSocket);
                threads[availableSlot] = new Thread(jsonReadThread);
                threads[availableSlot].start();

            } catch (IOException e) {
                System.out.println("❌ IO error in ServerJsonRead: " + e.getMessage());
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
                System.out.println("✅ JSON Read server closed correctly");
            }
        } catch (IOException e) {
            System.out.println("❌ Error closing JSON Read server: " + e.getMessage());
        }
    }
}