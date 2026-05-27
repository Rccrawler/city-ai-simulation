/**
 * Multithreaded server in charge of receiving the characters' connections
 * and assign them a thread to interact with the AI.
 * Port: 5001 (by default)
 * Instances: 100 simultaneous connections
 */
package org.example.llm;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class LlmConnectionServer implements Runnable{

    private int port = 0;
    private int instances = 0;

    public LlmConnectionServer(int port, int instances) {
        this.port = port;
        this.instances = instances;
    }

    @Override
    public void run() {
        Thread[] threads = new Thread[this.instances];
        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(this.port);
            System.out.println("✅ Server started on port " + port + ". Waiting for connections...");
        } catch (IOException e) {
            System.out.println("❌ Failed to start server: " + e.getMessage());
            return; // Exit if we can't create the server
        }

        while (true) {
            try {
                // Clean up finished threads
                for (int i = 0; i < threads.length; i++) {
                    if (threads[i] != null && !threads[i].isAlive()) {
                        threads[i] = null;
                    }
                }

                // Find an available slot
                int availableSlot = -1;
                for (int i = 0; i < threads.length; i++) {
                    if (threads[i] == null) {
                        availableSlot = i;
                        break;
                    }
                }

                if (availableSlot == -1) {
                    System.out.println("⚠️ Server full (" + instances + "/" + instances + " clients). Waiting...");
                    Thread.sleep(500);
                    continue;
                }

                Socket clientSocket = serverSocket.accept();
                System.out.println("✅ Client connected on slot " + availableSlot);

                LlmRequestTask requestTask = new LlmRequestTask(clientSocket);
                threads[availableSlot] = new Thread(requestTask);
                threads[availableSlot].start();

            } catch (IOException e) {
                System.out.println("❌ IO Error: " + e.getMessage());
            } catch (InterruptedException e) {
                System.out.println("⚠️ Thread interrupted: " + e.getMessage());
                Thread.currentThread().interrupt();
                break;
            }
        }

        // Close resources on shutdown
        try {
            if (!serverSocket.isClosed()) {
                serverSocket.close();
                System.out.println("✅ ServerSocket closed successfully");
            }
        } catch (IOException e) {
            System.out.println("❌ Error closing ServerSocket: " + e.getMessage());
        }
    }
}
