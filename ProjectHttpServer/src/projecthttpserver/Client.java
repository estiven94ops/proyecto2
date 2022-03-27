/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projecthttpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author GINO
 */
public class Client {
    // establish a connection by providing host and port
        // number
    public void Client(){}
    public void run(){
        try (Socket socket = new Socket("localhost", 9000)) {
            
            // writing to server
            PrintWriter out = new PrintWriter(
                socket.getOutputStream(), true);
  
            // reading from server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
  
            // object of scanner class
            Scanner sc = new Scanner(System.in);
            String line = null;
  
            while (!"exit".equalsIgnoreCase(line)) {
                
                // reading from user
                line = sc.nextLine();
  
                // sending the user input to server
                out.println(line);
                out.flush();
  
                // displaying server reply
                System.out.println("Repsuesta del servidor "+ in.readLine());
            }
            
            // closing the scanner object
            sc.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
}
}
