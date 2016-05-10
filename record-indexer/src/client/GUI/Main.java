package client.GUI;

import java.awt.EventQueue;

import client.communication.ClientCommunicator;

public class Main {
	
	public static void main(String[] args) {
		
		ClientCommunicator.setHOST(args[0]);
		ClientCommunicator.setPORT(args[1]);
		
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				
				VisibleManager manager = new VisibleManager();
				
			}
			
		});
	}

}
 