package conectado;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import org.json.JSONObject;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;
import com.sun.net.httpserver.HttpServer;

public class Api extends Thread {
	
	static String mensaje = "HOLA DESDE SERVIDOR";
	static String POST = "HOLA DESDE SERVIDOR";
	static int PUERTO = 5000;
	static HashMap<String,String> student1 = new HashMap<>();
	static String server;
	

	
	@Override
	public void run() {
		
			
	      HttpServer server;
	      
	 	   boolean scanning=true;
		    
		    while (scanning) {
		try {
			server = HttpServer.create(new InetSocketAddress(PUERTO), 0);
		      HttpContext context = server.createContext("/clients");
		      context.setHandler(Api::handleRequest);
		      server.start();
		      scanning=false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("PUERTO DE API YA USADO, REINTANDO CON OTRO");
			PUERTO = PUERTO + 1;
			System.out.println(PUERTO);
	    	try {
	            Thread.sleep(1000);//1.5 seconds
	        } catch(InterruptedException ie){
	            ie.printStackTrace();
	        }
		}

	  }}

	  private static void handleRequest(HttpExchange exchange) throws IOException {
	      URI requestURI = exchange.getRequestURI();
	      printRequestInfo(exchange);//returns a stream from which the request body can be read.
	      String response = "This is the response at " + requestURI;
	      exchange.sendResponseHeaders(200, response.getBytes().length);
	      OutputStream os = exchange.getResponseBody();
	      os.write(response.getBytes());
	      os.close();
	  }

	  private static void printRequestInfo(HttpExchange exchange) throws IOException {
		  HashMap<String,String> student = new HashMap<>();
		  
		  String str = exchange.getRequestMethod();
          main prueba_lider = new main();
          server = "";
          
      	FileReader reader = new FileReader("E:\\USUARIOS\\UEES\\Sistemas Distribuidos\\conectado\\conectado\\src\\properties//archive.properties");
  		
    		Properties c = new Properties();
    		
    		c.load(reader);
		  
	      switch(str) {
            case "GET":

      	      prueba_lider.esLider();
      	      if (prueba_lider.esLider() == true) {System.out.println("SOY LIDER");
      	      	URI requestURI = exchange.getRequestURI();
      	      	String query = requestURI.getQuery();
      	      	System.out.println(query);
      	      	if (student1.containsKey(query)) {
        	    System.out.printf("ID: "+ query +" NAME: "+ student1.get(query));
        	    String response = "{\n\"id\":\""+ query + "\",\n\"name\":\"" + student1.get(query) + "\"\n}";
      	      	exchange.sendResponseHeaders(200, response.getBytes().length);
      	      	OutputStream os = exchange.getResponseBody();
      	      	os.write(response.getBytes());
      	      	os.close();
        	}else {System.out.println("NO EXISTE ESE CLIENTE"); System.out.println(mensaje);
        	String response = "NO EXISTE ESE CLIENTE";
  	      	exchange.sendResponseHeaders(200, response.getBytes().length);
  	      	OutputStream os = exchange.getResponseBody();
  	      	os.write(response.getBytes());
  	      	os.close();}
      	      	}
      	      else {
      	    	  System.out.println("SOY SEGUIDOR");
      	    	  URI requestURI = exchange.getRequestURI();
            	  String query = requestURI.getQuery();
            	  System.out.println(query);
            	  if (student1.containsKey(query)) {
              	    System.out.printf("ID: "+ query +" NAME: "+ student1.get(query));
            	    String response = "{\n\"id\":\""+ query + "\",\n\"name\":\"" + student1.get(query) + "\"\n}";
          	      	exchange.sendResponseHeaders(200, response.getBytes().length);
          	      	OutputStream os = exchange.getResponseBody();
          	      	os.write(response.getBytes());
          	      	os.close();
              	}else {System.out.println("NO EXISTE ESE CLIENTE");
              	String response = "NO EXISTE ESE CLIENTE";
      	      	exchange.sendResponseHeaders(200, response.getBytes().length);
      	      	OutputStream os = exchange.getResponseBody();
      	      	os.write(response.getBytes());
      	      	os.close();}
      	    	  
      	      }
                //System.out.println("GET CORRECTO");
                break;
            case "POST":
                prueba_lider.esLider();
      	      if (prueba_lider.esLider() == true) {
      	    	  //System.out.println("SOY LIDER");

      	      	      	      
      	      	InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
      	        BufferedReader br = new BufferedReader(isr);

      	        int b;
      	        StringBuilder buf = new StringBuilder();
      	        while ((b = br.read()) != -1) {
      	            buf.append((char) b);
      	        }

      	        br.close();
      	        isr.close();
      	        //System.out.println(buf.toString());
      	        String prueba = buf.toString();
      	        JSONObject json = new JSONObject(prueba);
      	        String id = json.getString("id");
      	      	String name = json.getString("name");
      	    	//System.out.println(name);
                  String proceso = json.getString("id") + " " +  json.getString("name");  
                  //System.out.println(proceso);
                  
                  if (student1.containsKey(id)) {
                	    System.out.printf("YA EXISTE EL CLIENTE: "+ student1.get(id) +" con ID "+ id); mensaje = "NADA";
                	    String response = "YA EXISTE EL CLIENTE: "+ student1.get(id) +" con ID "+ id;
              	      	exchange.sendResponseHeaders(200, response.getBytes().length);
              	      	OutputStream os = exchange.getResponseBody();
              	      	os.write(response.getBytes());
              	      	os.close();
                	}else {Server server = new Server(b, proceso, null);mensaje = "POST "+ json;System.out.println(mensaje);
                    try {
                        String ruta = c.getProperty("ruta_servidor");
                        //String ruta_2 = "C:\\Users\\Steven\\Desktop\\frecuencias.txt";
                        String contenido =System.lineSeparator()+ "POST "+ json;
                       //String contenido2 =System.lineSeparator()+ palabra + " " +  frecuencia;
                        File file = new File(ruta);
                        //File file2 = new File(ruta_2);
                        // Si el archivo no existe es creado
                        if (!file.exists()) {
                            file.createNewFile();
                            //file2.createNewFile();
                        }
                        FileWriter fw = new FileWriter(file, true);
                        BufferedWriter bw = new BufferedWriter(fw);
                        bw.write(contenido);
                        bw.close();
                        
                        //FileWriter fw1 = new FileWriter(file2, true);
                        //BufferedWriter bw1 = new BufferedWriter(fw1);
                        //bw1.write(contenido2);
                        //bw1.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } String linea = guardar_map(c.getProperty("ruta_servidor"));
            	      System.out.println("ULTIMA LINEA"+linea);
              	      String JSON = obtenerJson(linea);
              	      System.out.println("json obtenido"+JSON);
              	      JSONObject json_1 = new JSONObject(JSON);
            	        String id_1 = json_1.getString("id");
              	      	String name_1 = json_1.getString("name");
              	      student1.put(id_1, name_1);
              	      
              	    String response = "{\n\"id\":\""+ id_1 + "\",\n\"name\":\"" + name_1 + "\"\n}";
          	      	exchange.sendResponseHeaders(200, response.getBytes().length);
          	      	OutputStream os = exchange.getResponseBody();
          	      	os.write(response.getBytes());
          	      	os.close();
                	}
    	      		//student1.put(id, id);
    	      		
                  mensaje = "";
    	      	    //student1.put(id, name);
    	      	    
    	      	    /*System.out.println("Elements in student map after invoking putAll method:");
    	      	    System.out.println(student1);
    	      	    System.out.println(student1.get(id));*/
    	      	    System.out.println(student1);
                  

                  
                  
      	      }else {
      	    	  System.out.println("SOY SEGUIDOR, NO PUEDO HACER POST");
          	    String response = "SOY SEGUIDOR, NO PUEDO HACER POST";
      	      	exchange.sendResponseHeaders(200, response.getBytes().length);
      	      	OutputStream os = exchange.getResponseBody();
      	      	os.write(response.getBytes());
      	      	os.close();
      	    	  }
      	      
      	      //System.out.println("POST CORRECTO");
                break;
            case "DELETE":
            	prueba_lider.esLider();
        	      if (prueba_lider.esLider() == true) {System.out.println("SOY LIDER");
        	      	URI requestURI = exchange.getRequestURI();
        	      	String query = requestURI.getQuery();

                      
        	      	if (student1.containsKey(query)) {
        	      		
        	      		
          	  mensaje = "DELETE " + query + student1.get(query);
          	  String response = "CLIENTE ELIMINADO\n\n{\n\"id\":\""+ query + "\",\n\"name\":\"" + student1.get(query) + "\"\n}";
    	      exchange.sendResponseHeaders(200, response.getBytes().length);
    	      OutputStream os = exchange.getResponseBody();
    	      os.write(response.getBytes());
    	      os.close();
    	      student1.remove(query);
          	  //System.out.println(student1);
          	    
          	}else {System.out.println("NO EXISTE ESE CLIENTE, IMPOSIBLE ELIMINAR");
          	String response = "NO EXISTE ESE CLIENTE, IMPOSIBLE ELIMINAR";
  	      exchange.sendResponseHeaders(200, response.getBytes().length);
  	      OutputStream os = exchange.getResponseBody();
  	      os.write(response.getBytes());
  	      os.close();
          	}
        	      	}
        	      else {
        	    	  System.out.println("SOY SEGUIDOR, NO PUEDO ELIMINAR");
        	    	    String response = "SOY SEGUIDOR, NO PUEDO ELIMINAR";
            	      	exchange.sendResponseHeaders(200, response.getBytes().length);
            	      	OutputStream os = exchange.getResponseBody();
            	      	os.write(response.getBytes());
            	      	os.close();
            	    	  
        	    	  
        	    	          	    	  
        	      }
                  //System.out.println("GET CORRECTO");
                  break;
            case "PUT":
            	prueba_lider.esLider();
      	      if (prueba_lider.esLider() == true) {System.out.println("SOY LIDER");
      	      	URI requestURI = exchange.getRequestURI();
      	      	String query = requestURI.getQuery();
      	      	//System.out.println(query);
      	      	
      	      	
      	      	
      	      	
      	      	InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
        	        BufferedReader br = new BufferedReader(isr);

        	        int b;
        	        StringBuilder buf = new StringBuilder();
        	        while ((b = br.read()) != -1) {
        	            buf.append((char) b);
        	        }

        	        br.close();
        	        isr.close();
        	        //System.out.println(buf.toString());
        	        String prueba = buf.toString();
        	        JSONObject json = new JSONObject(prueba);
        	        //String id = json.getString("id");
        	      	String name = json.getString("name");
        	    	//System.out.println(name);
                    //String proceso = json.getString("id") + " " +  json.getString("name");  
                    //System.out.println(proceso);
                    
                    
      	      	if (student1.containsKey(query)) {
        	    student1.put(query, name);
        	    String response = "CLIENTE ACTUALIZADO\n\n{\n\"id\":\""+ query + "\",\n\"name\":\"" + student1.get(query) + "\"\n}";
      	      	exchange.sendResponseHeaders(200, response.getBytes().length);
      	      	OutputStream os = exchange.getResponseBody();
      	      	os.write(response.getBytes());
      	      	os.close();
        	  //System.out.println(student1);
        	    
        	}else {System.out.println("NO EXISTE ESE CLIENTE, IMPOSIBLE ACTUALIZAR");
        	 String response = "NO EXISTE ESE CLIENTE, IMPOSIBLE ACTUALIZAR";
  	      	exchange.sendResponseHeaders(200, response.getBytes().length);
  	      	OutputStream os = exchange.getResponseBody();
  	      	os.write(response.getBytes());
  	      	os.close();}
      	      	}
      	      else {
      	    	  System.out.println("SOY SEGUIDOR, NO PUEDO ACTUALIZAR");
      	    	 String response = "SOY SEGUIDOR, NO PUEDO ACTUALIZAR";
     	      	exchange.sendResponseHeaders(200, response.getBytes().length);
     	      	OutputStream os = exchange.getResponseBody();
     	      	os.write(response.getBytes());
     	      	os.close();
      	    	          	    	  
      	      }
                //System.out.println("GET CORRECTO");
                break;
        }
	        
	        
	        
	        
	        
	      /*System.out.println("-- headers --");
	      Headers requestHeaders = exchange.getRequestHeaders();
	      requestHeaders.entrySet().forEach(System.out::println);

	      System.out.println("-- principle --");
	      HttpPrincipal principal = exchange.getPrincipal();
	      System.out.println(principal);

	      System.out.println("-- HTTP method --");
	      String requestMethod = exchange.getRequestMethod();
	      System.out.println(requestMethod);
	      httpmain prueba_lider = new httpmain();
	      prueba_lider.esLider();
	      if (prueba_lider.esLider() == true) {System.out.println("SOY LIDER");}else {System.out.println("SOY SEGUIDOR");}

	      System.out.println("-- query --");
	      URI requestURI = exchange.getRequestURI();
	      String query = requestURI.getQuery();
	      System.out.println(query);
	      	      	      
	      	InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
	        BufferedReader br = new BufferedReader(isr);

	        int b;
	        StringBuilder buf = new StringBuilder();
	        while ((b = br.read()) != -1) {
	            buf.append((char) b);
	        }

	        br.close();
	        isr.close();
	        System.out.println(buf.toString());
	        String prueba = buf.toString();
	        JSONObject json = new JSONObject(prueba);
            String id = json.getString("id") + " " +  json.getString("name");  
            mensaje = id;
            System.out.println(id);
            System.out.println(mensaje);
	      
      	try {
            String ruta = "C:\\Users\\Steven\\Desktop\\frecuencias.txt";
            //String ruta_2 = "C:\\Users\\Steven\\Desktop\\frecuencias.txt";
            String contenido =System.lineSeparator()+ buf;
           //String contenido2 =System.lineSeparator()+ palabra + " " +  frecuencia;
            File file = new File(ruta);
            //File file2 = new File(ruta_2);
            // Si el archivo no existe es creado
            if (!file.exists()) {
                file.createNewFile();
                //file2.createNewFile();
            }
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(contenido);
            bw.close();
            
            //FileWriter fw1 = new FileWriter(file2, true);
            //BufferedWriter bw1 = new BufferedWriter(fw1);
            //bw1.write(contenido2);
            //bw1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
	  }
	  
	  public String mensaje_pasa () {
		  return mensaje;
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
	  
	  
      public static String obtenerJson(String cadena){
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


		  	  
	  
	    public void guardar_post(String id, String name) {
	    	student1.put(id, name);
	    }
	    
	    
	    /*public void comprobar_server(String id) {
	        if (student.containsKey(id)) {
	      	    System.out.printf("Contiene la clave A. Su valor es: %d\n", hashmap.get(id));
	      	}
	    }*/
		  
	  } 