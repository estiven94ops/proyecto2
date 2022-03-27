/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projecthttpserver;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author GINO
 */
public class Server {
    
    public void Server(){}
    public void run()
    {
        ServerSocket server = null;
  
        try {
  
            // server is listening on port 1234
            server = new ServerSocket(9000);
            server.setReuseAddress(true);
  
            // running infinite loop for getting
            // client request
            while (true) {
  
                // socket object to receive incoming client
                // requests
                Socket client = server.accept();
  
                // Displaying that new client is connected
                // to server
                InetSocketAddress socketAddress = (InetSocketAddress) client.getRemoteSocketAddress();
                System.out.println("Nuevo cliente conectado: "
                                   + socketAddress);
  
                // create a new thread object
                ClientHandler clientSock
                    = new ClientHandler(client);
  
                // This thread will handle the client
                // separately
                new Thread(clientSock).start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (server != null) {
                try {
                    server.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
  
    // ClientHandler class
    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;
        DataOutputStream outs;
        // Constructor
        public ClientHandler(Socket socket)
        {
            this.clientSocket = socket;
        }
  
        @Override
        public void run()
        {
            PrintWriter out = null;
            BufferedReader in = null;
            try {
                    
                  // get the outputstream of client
                out = new PrintWriter(
                    clientSocket.getOutputStream(), true);
  
                  // get the inputstream of client
                in = new BufferedReader(
                    new InputStreamReader(
                        clientSocket.getInputStream()));
                //String cadena=in.readLine().contains("");
                //System.out.println("Cadena: "+cadena);
                String line;
                //System.out.println(in.readLine());
                    BufferedWriter bw=new BufferedWriter(new FileWriter("C://Users//Steven//Desktop//ficheropost.txt"));
                bw.write("{");
                int c=0;
                while ((line = in.readLine()) != null) {
                    c++;
                    if(c>=12){
                        System.out.printf("%s\n",line);
                        out.println(line);
                    out.write(line);
                    bw.write(line);
                    
                    }     
                }
                bw.flush();
                
                
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                        clientSocket.close();
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
