

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;

public class main {
	
	static boolean lider = true;
	
    public static void main(String[] args) throws IOException{
    	
    	FileReader reader = new FileReader("/home/nodo?4/proyecto2/properties/archive.properties");
  		
  		Properties c = new Properties();
  		
  		c.load(reader);
        final String ruta=c.getProperty("ruta_servidor");//ruta archivo servidor
        final String rutac=c.getProperty("ruta_cliente");//ruta para archivos de clientes
        String ip="192.168.1.2";//ip maquina servidor/fisica
        
        Api api = new Api();
        api.run();
        if(comprobar(9000)==true){
            
            FileWriter file = new FileWriter(ruta);
            BufferedWriter buffer = new BufferedWriter(file);
            Server servidor=new Server(9000, ruta, buffer);
            lider = true;
            
            servidor.start();
        }
        else{
            Client cliente=new Client(rutac);
            lider = false;
            
            cliente.run();
        }
    }
    public static boolean comprobar(int puerto){
        try
            {
            Socket clientSocket = new Socket("localhost",puerto);
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
            System.out.println("SOY SEGUIDOR");
            //System.out.println("Puerto " + puerto + " OCUPADO");
            out.close();
            clientSocket.close();
            return false;
            }
            catch (UnknownHostException e){
            System.out.println(e);
            return false;
            }
            catch (IOException e) {
            System.out.println("SOY LIDER");
            	//System.out.println("Puerto " + puerto + " LIBRE");
            return true;
            }
    }
    
    public boolean esLider() {
    	return lider;
    } 
}
