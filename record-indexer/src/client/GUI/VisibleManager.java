package client.GUI;

import java.awt.Dimension;

import client.communication.ClientCommunicator;

public class VisibleManager {
	
	private ClientCommunicator cCommunicator;
	private Login login;
	private Indexer indexer;
	private String username;
	private String password;
	private State state;
	
	public VisibleManager() {
		
		cCommunicator = new ClientCommunicator();
		
		login = new Login(this);
		indexer = new Indexer(this);
		
		login.setVisible(true);
		indexer.setVisible(false);
		
	}
	
	public void close(){
		login.setVisible(false);
		login.dispose();
		indexer.setVisible(false);
		indexer.dispose();
		System.exit(0);
	}
	
	public void setLoginVisible(){
		login.setVisible(true);
		indexer.setVisible(false);
	}
	
	public void setIndexerVisible(){
		login.setVisible(false);
		indexer.setState(state);
		indexer.setVisible(true);
	}
	
	public void setIndexerLocation(int x, int y){
		indexer.setLocation(x, y);
	}
	public void setIndexerSize(Dimension d){
		indexer.setSize(d);
	}
	public void setIndexerSplits(int x, int y){
		indexer.setSplits(x, y);
	}

	public ClientCommunicator getcCommunicator() {
		return cCommunicator;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	public void setState(State state){
		this.state = state;
	}
	public void setNewstate(State state){
		this.state = state;
		indexer = new Indexer(this);
	}
	
	public String getUsername(){
		return username;
	}
	
	public String getPassword(){
		return password;
	}
	public State getState(){
		return state;
	}
	
}