/**
 * Main class that initializes all multithreaded servers.
 * Start the AI servers, JSON reading, and JSON writing on their respective ports.
 */
package org.example;

import org.example.llm.LlmConnectionServer;
import org.example.util.ServerJsonRead;
import org.example.util.ServerJsonWrite;

public class Main {

    public static void main(String[] args) {
        // We launch the AI server thread, remember that each character is a thread so that they can do various things at the same time
        LlmConnectionServer LlmConnectionServer = new LlmConnectionServer(5001, 100);
        Thread ClienteIA_servidor_Thread = new Thread(LlmConnectionServer, "cline");
        ClienteIA_servidor_Thread.start();

        ServerJsonRead serverJsonRead = new ServerJsonRead(5002, 30);
        Thread servidorJsonRead_trhead = new Thread(serverJsonRead, "s");
        servidorJsonRead_trhead.start();

        ServerJsonWrite serverJsonWrite = new ServerJsonWrite(5003, 30);
        Thread servidorJsonWrite_trhead = new Thread(serverJsonWrite, "s");
        servidorJsonWrite_trhead.start();

        // We wait a moment to make sure the server is ready
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}