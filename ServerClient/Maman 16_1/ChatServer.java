import java.io.IOException;
import java.net.*;
import java.util.*;
import java.io.*;

/*Chat Server class*/
public class ChatServer {
	private boolean canRun;
	ServerSocket serverSocket = null;
	ArrayList<ClientThread> clientList;
	MsgSender sender = new MsgSender();

	/*
	 * Main that runs the server, the server gets a port number in it's constructor
	 */
	public static void main(String[] args) {
		ChatServer mainServer = new ChatServer(7777);

	}

	/* Constructor */
	public ChatServer(int port) {
		clientList = new ArrayList<ClientThread>();
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("port " + port + " is waiting for clients!");
			this.start();
		} catch (IOException e) {
			System.err.println("Could not listen on port: " + port);
			System.exit(1);
		}
	}

	/* Start accepting clients */
	public void start() {
		canRun = true;
		Socket socket = null;
		ObjectOutputStream tempOut;
		ObjectInputStream tempin;
		ChatMsg msg;
		while (canRun) {
			try {
				socket = serverSocket.accept();
				System.out.println("A new client logged in to the chat!");
				tempOut = new ObjectOutputStream(socket.getOutputStream());
				tempin = new ObjectInputStream(socket.getInputStream());
				msg = (ChatMsg) (tempin.readObject()); // Get a message with the client's name
				ClientThread client = new ClientThread(sender, msg.getName(), tempin, tempOut); //add client by its name
				sender.add(client);
				sender.sendJoinMessage(client.getClientName()); //sends join message to all clients
				client.start();
			} catch (IOException e) {
				System.out.println("Accept failed on port : ");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
