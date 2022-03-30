

import java.io.*;
import java.net.*;
import java.util.*;

import org.json.JSONObject;

class Client {
    private final String ruta;
    private DataInputStream in;
    private DataOutputStream out;


    
    
    public Client(String ruta) throws IOException{
        this.ruta=ruta;
        this.in=null;
        this.out=null;
        

    }
    // driver code
    public void run() throws IOException
    {        
    	FileReader reader = new FileReader("/home/nodo3/proyecto2/properties/archive.properties");
  		
		Properties c = new Properties();
		
		c.load(reader);
    	
    	
        try (Socket socket = new Socket(c.getProperty("DIRECCION_IP"), Integer.parseInt (c.getProperty("PUERTO_NODOS")))) {
            in=new DataInputStream(socket.getInputStream());
            out=new DataOutputStream(socket.getOutputStream());
            int socketAddress = socket.getLocalPort();
            System.out.println("ADDRESS: "+socketAddress);
            String ruta=this.ruta+(socketAddress)+".txt";
            File file=new File(ruta);
            
            Scanner sc = new Scanner(System.in);
            String line = null;
            String cadena = null;
            
            /**/
            
            while (true) {
                
            	System.out.println("ADDRESS: "+socketAddress);
            	
                int limiteFichero = in.readInt();
                // Creo un array de bytes
                byte[] contenidoFichero= new byte[limiteFichero];

                // Recibo el contenido array
                for (int i = 0; i < limiteFichero; i++) {
                    contenidoFichero[i] = in.readByte();
                }
                // Creo el string 
                String contenido = new String(contenidoFichero);
                // lo guardo en un nuevo fichero
                
                FileWriter fw = new FileWriter(file);
                fw.write(contenido);
                System.out.println(contenido);
                if(contenido.contains("POST")){

                    System.out.println(contenido.charAt(5));}
                fw.close();
                

                System.out.printf("Replicando....");
                
                if (isFileEmpty(ruta) == false) {//System.out.println("ARCHIVO NO VACIO");
                cadena = guardar_map(ruta);
                String JSON = obtenerJson(cadena);
        	    System.out.println("json obtenido"+JSON);
        	    JSONObject json = new JSONObject(JSON);
      	        String id = json.getString("id");
        	    String name = json.getString("name");
                
               Api api = new Api();
               api.guardar_post(id, name);
                }else{
                	//System.out.println("ARCHIVO VACIO");
                	}
                

                
                
                //line = sc.nextLine();
                //String peticion_cliente=in.readUTF();
                        /*if(line.contains("get")){
                            String datoA_buscar=obtenerJson(line);
                            String resultbusqueda=buscardato(ruta, datoA_buscar);
                            System.out.println("Resultado de busqueda: "+resultbusqueda);
                        }*/
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String buscardato(String ruta, String datobuscar) throws FileNotFoundException, IOException{
        String result="";
        File doc = new File(ruta);
        BufferedReader obj = new BufferedReader(new FileReader(doc));
        String strng;
        while ((strng = obj.readLine()) != null){
            if(strng.contains(datobuscar)){
                System.out.println(strng);
                return strng;
            }
        }
         return "No encontrado";
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
    
	  public static String guardar_map(String ruta) throws IOException{
		  
		  
		    @SuppressWarnings("resource")
			BufferedReader input = new BufferedReader(new FileReader(ruta));
		    String last = null, line;

		    while ((line = input.readLine()) != null) { 
		        last = line;
		    }
		  
		    return last;
			  }
	  
	  public boolean isFileEmpty(String ruta2) throws IOException {
		    BufferedReader br = new BufferedReader(new FileReader(ruta2));     
		    return br.readLine() == null;
		}
	  
	  
}
