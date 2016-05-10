package server;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.util.logging.*;

import com.sun.net.httpserver.*;

import shared.model.*;


public class Server {

	private static int SERVER_PORT_NUMBER = 39640;
	private static int MAX_WAITING_CONNECTIONS = 10;
	
	private static Logger logger;	
	
	public Server(int port){
		SERVER_PORT_NUMBER = port;
	}
	
	static {
		try {
			initLog();
		}
		catch (IOException e) {
			System.out.println("Could not initialize log: " + e.getMessage());
		}
	}
	
	private static void initLog() throws IOException {
		
		Level logLevel = Level.FINE;
		
		logger = Logger.getLogger("serverhandler"); 
		logger.setLevel(logLevel);
		logger.setUseParentHandlers(false);
		
		Handler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(logLevel);
		consoleHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(consoleHandler);

		FileHandler fileHandler = new FileHandler("log.txt", false);
		fileHandler.setLevel(logLevel);
		fileHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(fileHandler);
	}

	
	private HttpServer server;
	
	private Server() {
		return;
	}
	
	private void run() {
		
		logger.info("Initializing Model");
		
		try {
			Model.initialize();		
		}
		catch (ModelException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			return;
		}
		
		logger.info("Initializing HTTP Server");
		
		try {
			server = HttpServer.create(new InetSocketAddress(SERVER_PORT_NUMBER),
											MAX_WAITING_CONNECTIONS);
		} 
		catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);			
			return;
		}

		server.setExecutor(null); // use the default executor
		
		server.createContext("/ValidateUser", validateUser_Handler);
		server.createContext("/GetProjects", getProjects_Handler);
		server.createContext("/GetSampleimage", getSampleimage_Handler);
		server.createContext("/DownloadBatch", downloadBatch_Handler);
		server.createContext("/SubmitBatch", submitBatch_Handler);
		server.createContext("/GetFields", getFields_Handler);
		server.createContext("/Search", search_Handler);
		server.createContext("/", downloadFile_Handler);
				
		logger.info("Starting HTTP Server");

		server.start();
	}

	private HttpHandler validateUser_Handler = new ValidateUser_Handler();
	private HttpHandler getProjects_Handler = new GetProjects_Handler();
	private HttpHandler getSampleimage_Handler = new GetSampleimage_Handler();
	private HttpHandler downloadBatch_Handler = new DownloadBatch_Handler();
	private HttpHandler submitBatch_Handler = new SubmitBatch_Handler();
	private HttpHandler getFields_Handler = new GetFields_Handler();
	private HttpHandler search_Handler = new Search_Handler();
	
	
	private HttpHandler	downloadFile_Handler = new HttpHandler(){

		@Override
		public void handle(HttpExchange exchange) throws IOException {
			// TODO Auto-generated method stub
			
			File file = new File("./data" + File.separator + exchange.getRequestURI().getPath());
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			Files.copy(file.toPath(), exchange.getResponseBody());
			
			exchange.getResponseBody().close();
			
//			URI uri = exchange.getRequestURI();
//			
//			String path = uri.toString().substring(14);
//			
//			System.out.println("path" + path);
//			
//			FileInputStream input = new FileInputStream(path);
//			
//			exchange.getResponseHeaders().add("Content-Type", "image/png");
//			exchange.sendResponseHeaders(200, 0);
//			int bytes = input.available();
//			OutputStream output = exchange.getResponseBody();
//			
//			while(bytes != -1){
//				output.write(input.read());
//				bytes--;
//			}
//			
//			input.close();
//			output.close();		
			
		}
		
	};
	
	public static void main(String[] args) {
		new Server(Integer.parseInt(args[0])).run();
	}

}