package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import viewModel.Controller;

public class FlightManager {
	
	int speed;
	Queue<String> lines;
	Queue<String> flight;
	int time;
	
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private BufferedReader in;
    
    private boolean started = false;
	private Socket fg;
	PrintWriter out;
	private Controller controller;
	private int send;
	
	public FlightManager(Controller c, int send, int recive) {
		controller = c;
		speed = 1;
		lines = new ConcurrentLinkedQueue<String>();
		flight = new ConcurrentLinkedQueue<String>();
		time = 0;
		this.send = send;
		try {
			serverSocket = new ServerSocket(recive);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public String simulate(String data) {
		out.println(data);
		out.flush();
		String ans = null;
		try {
			ans = in.readLine();
			flight.add(ans);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ans;
	}	
	
	
    public void stop() {
			out.close();
			try {
				fg.close();
				in.close();
		        clientSocket.close();
		        serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
    }
	
	public void start() {
		if(!started) {
			try {
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			started = true;
		}
	}
    

	public void init() {
		try {
			fg = new Socket("localhost", send);
			out=new PrintWriter(fg.getOutputStream());
			clientSocket = serverSocket.accept();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
