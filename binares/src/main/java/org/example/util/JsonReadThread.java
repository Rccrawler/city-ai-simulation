/**
 * Thread that manages reading JSON data for a specific character.
 * It handles incoming requests and returns the structured information.
 */
package org.example.util;

import java.io.*;
import java.net.Socket;

public class JsonReadThread implements Runnable {

    private Socket clientSocket;

    public JsonReadThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            InputStream input = this.clientSocket.getInputStream();
            OutputStream output = this.clientSocket.getOutputStream();

            DataInputStream dataIn = new DataInputStream(input);
            DataOutputStream dataOut = new DataOutputStream(output);

            // Read client request
            String request = dataIn.readUTF();
            System.out.println("📖 JSON Read request: " + request);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}