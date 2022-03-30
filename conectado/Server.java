
import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;

class Server extends Thread{
	
		
    // Vector to store active clients
    static Vector<ClientHandler> ar = new Vector<>();
    static String aux = "";
     
    // counter for clients
    static int i = 0;
    
    private int puerto; // Número de puerto de escucha para la conexión de clientes
    private String ruta;
    private BufferedWriter bufferW;
    
    public Server(int puerto, String ruta, BufferedWriter bufferW) {
        this.puerto = puerto;
        this.ruta=ruta;
        this.bufferW=bufferW;
        
        
        
    }
    @Override
    public void run()
    {
        ServerSocket socketServidor = null;
        
  
        try {
        	
        	FileReader reader = new FileReader("E:\\USUARIOS\\UEES\\Sistemas Distribuidos\\conectado\\conectado\\src\\properties//archive.properties");
      		
    		Properties c = new Properties();
    		
    		c.load(reader);
    		
    		puerto = Integer.parseInt (c.getProperty("PUERTO_NODOS"));
		  
            //Creamos instancia de ServerSocket para
            //atender peticiones de clientes por el puerto indicado
            socketServidor = new ServerSocket(this.puerto);
            System.out.println(" Servidor esperando " +
                    "peticiones de clientes por puerto " + puerto + "...");
            
            // server is listening on port 1234
            //server = new ServerSocket(1234);
            //server.setReuseAddress(true);
  
            // running infinite loop for getting
            // client request
            while (true) {
  
                // socket object to receive incoming client
                // requests
                Socket client = socketServidor.accept();
                 
                // Displaying that new client is connected
                // to server
                //System.out.println("New client connected"+ client.getInetAddress().getHostAddress());
                System.out.println("Nuevo cliente conectado: "+client.getRemoteSocketAddress());
                
                // create a new thread object
                ClientHandler clientSock
                    = new ClientHandler(client, this.bufferW, this.ruta);
                ar.add(clientSock);
  
                // This thread will handle the client
                // separately
                new Thread(clientSock).start();
                
                i++;
                System.out.println(ar);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (socketServidor != null) {
                try {
                    socketServidor.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
  
    // ClientHandler class
    private static class ClientHandler implements Runnable {
        private  Socket clientSocket;
        private  BufferedWriter bf;
        private  String ruta;
        private DataOutputStream out;
        private DataInputStream in;
  
        // Constructor
        public ClientHandler(Socket socket, BufferedWriter bufferW, String ruta)
        {
            this.clientSocket = socket;
            this.bf=bufferW;
            this.ruta=ruta;
        }
  
        @Override
        public void run()
        {
            
            try {
                Scanner sc = new Scanner(System.in);
                String lineout = null;
                boolean prueba;//para enviar al cliente si es comando POST  
                
                System.out.println(clientSocket);
                
                out = new DataOutputStream(clientSocket.getOutputStream());
                
                File file_0=new File(this.ruta);
                
                byte[] arraycontenido_0 = obtenerBytes(file_0);
                out.writeInt(arraycontenido_0.length);

                // Envio los bytes uno a uno
                for (int i = 0; i < arraycontenido_0.length; i++) {
                    out.writeByte(arraycontenido_0[i]);
                }
  
                  // obtiene peticion de cliente
                while (true) {
                    // escribiendo lo que se recibe del cliente
                    //System.out.printf("Ingrese comando: ");
                	Api api = new Api();
                	String pruebA = api.mensaje_pasa();
                	
                	
                	//lineout = sc.nextLine();
                    
                    if(pruebA.contains("POST")){
                    	System.out.println("FUNCIONA LA REPLICACION");
                    	//System.out.println(pruebA);
                    	//aux = pruebA;
                    	
                    	//out.writeUTF(pruebA);
                    	
                    //this.bf.write(pruebA+"\n");
                    this.bf.flush();
                    
                                        
                    File file=new File(this.ruta);
                    
                    byte[] arraycontenido = obtenerBytes(file);
                    out.writeInt(arraycontenido.length);

                    // Envio los bytes uno a uno
                    for (int i = 0; i < arraycontenido.length; i++) {
                        out.writeByte(arraycontenido[i]);
                    }lineout = " ";
                    }
                }
            }
            catch (IOException e) {
            	System.out.println("CLIENTE NO ENCONTRADO");
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
        public String obtenerJson(String cadena){
            String result="";
            for(int i=0; i<cadena.length();i++){
                if(cadena.charAt(i)=='}') break;
                if(cadena.charAt(i)=='{'){
                    for(int j=i; j<cadena.length();j++)
                    result+=cadena.charAt(j);
                } 
            }
            return result;
        }
        public String buscardato(File file, String datobuscar) throws FileNotFoundException{
        String result="";
        Scanner obj = new Scanner(file);
         while (obj.hasNextLine()){
            System.out.println(obj.nextLine());
            if(obj.nextLine().contains(datobuscar)){
            result+=obj.nextLine();
            return result;
            }
         }
         return "No encontrado";
        }
        public int numLineasArchivo(File file) throws FileNotFoundException, IOException{
            int result=0;
            BufferedReader br=new BufferedReader(new FileReader(file));
            String linea="";
            while((linea=br.readLine())!=null){
                result++;
            }
            br.close();
            return result;
        }
        public byte[] obtenerBytes(File f) throws FileNotFoundException, IOException{
        
        BufferedReader br = new BufferedReader(new FileReader(f));
        
        String linea = "";
        String contenido="";
        
        while( (linea = br.readLine()) != null){
            
                contenido+=linea+"\r\n";
        }
        
        br.close();
        
        return contenido.getBytes();
        
    }
        
    }
}
