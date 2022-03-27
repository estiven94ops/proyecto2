/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projecthttpserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author GINO
 */
public class httpmain {
    public static void main(String[] args){
        if(comprobar(9000)==true){
            Server servidor=new Server();
            servidor.run();
        }
        else{
            Client cliente=new Client();
            cliente.run();
        }
    }
    public static boolean comprobar(int puerto){
        try
            {
            Socket clientSocket = new Socket("localhost",puerto);
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
            System.out.println("Puerto " + puerto + " OCUPADO");
            out.close();
            clientSocket.close();
            return false;
            }
            catch (UnknownHostException e){
            System.out.println(e);
            return false;
            }
            catch (IOException e) {
            System.out.println("Puerto " + puerto + " LIBRE");
            return true;
            }
    }
}
