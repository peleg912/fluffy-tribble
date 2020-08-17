package com.example.demo.web;

import com.example.demo.services.ClientFacade;

public class OurSession {
	
	private ClientFacade facade;
	private long lastAccessed;
	private int numOfTries;
	
	public OurSession(ClientFacade facade, long lastAccessed) {
		super();
		this.facade = facade;
		this.lastAccessed = lastAccessed;
	}

	public long getLastAccessed() {
		return lastAccessed;
	}

	public void setLastAccessed(long lastAccessed) {
		this.lastAccessed = lastAccessed;
	}

	public int getNumOfTries() {
		return numOfTries;
	}

	public void setNumOfTries(int numOfTries) {
		this.numOfTries = numOfTries;
	}

	public ClientFacade getFacade() {
		return facade;
	}
	
	
	
	

}
