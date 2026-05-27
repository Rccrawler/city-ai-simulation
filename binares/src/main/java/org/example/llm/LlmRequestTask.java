/**
 * Individual thread that manages the communication of a character with the Python server.
 * Reads the character's prompt, sends it to the LLM, and returns the parsed response.
 * AI Port: 5555 (by default)
 */
package org.example.llm;

import java.io.*;
import java.net.Socket;

public class LlmRequestTask implements Runnable {

    private Socket clientSocket;

    private String prompt = "";
    public LlmRequestTask(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    /**
     * Sends the prompt to the Python AI server via TCP socket
     * and stores the response back into the prompt field.
     */
    public void sendPrompt() {
        String host = "127.0.0.1";
        int port = 5555;
        Socket socket = null;

        try {
            socket = new Socket(host, port);

            // Set timeout to 2 minutes
            socket.setSoTimeout(120000);

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            writer.println(this.prompt);
            writer.flush();

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.equals("<END_OF_RESPONSE>")) {
                    break;
                }
                response.append(line).append("\n");
            }

            this.prompt = response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error connecting to AI server");
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        readRequest();
        sendPrompt();
        sendResponse();
    }

    /**
     * Reads the incoming prompt from the game client connection.
     */
    private void readRequest() {
        try {
            InputStream inputStream = this.clientSocket.getInputStream();
            DataInputStream dataInput = new DataInputStream(inputStream);

            try {
                this.prompt = dataInput.readUTF();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sends the AI-generated response back to the game client.
     */
    private void sendResponse() {
        try {
            OutputStream outputStream = this.clientSocket.getOutputStream();
            DataOutputStream dataOutput = new DataOutputStream(outputStream);

            try {
                dataOutput.writeUTF(this.prompt);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}