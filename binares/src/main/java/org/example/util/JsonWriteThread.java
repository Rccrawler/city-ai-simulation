/**
 * Thread that manages writing and updating the JSON data
 * of a specific character, saving their latest actions and state.
 */
package org.example.util;
import java.net.Socket;

public class JsonWriteThread implements Runnable {

    private Socket clientSocket;

    public JsonWriteThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
    }
}