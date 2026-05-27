/**
 * Test client to send prompts to the local AI.
 * Port: 5555 (by default)
 * Timeout: 2 minutes (120000ms)
 */
package org.example.text;

import java.io.*;
import java.net.Socket;

public class IAclient {

    public static String sendPrompt(String pront) {
        String host = "127.0.0.1";
        int port = 5555;

        try (Socket socket = new Socket(host, port)) {

            // ⬇ WE INCREASE TIMEOUT TO 2 MINUTES
            socket.setSoTimeout(120000);

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            writer.println(pront);
            writer.flush();

            StringBuilder answer = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.equals("<END_OF_RESPONSE>")) {
                    break;
                }
                answer.append(line).append("\n");
            }

            return answer.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "Error connecting with AI";
        }
    }

    public static void main(String[] args) {
        String answer = sendPrompt("Elisabet (TikTok influencer) wants to respond to Rebeca, who has told her that she does not want to go out with her, but she does not know what to say. What could she say to convince her to go out with her?");
        System.out.println(answer);
    }
}