import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.*;

/*Messages sender */
public class MsgSender {
	ArrayList<ClientThread> clientList;

	/* Constructor */
	public MsgSender() {
		clientList = new ArrayList<ClientThread>();
	}

	/*
	 * gets a ClientThread and adds it to the list of clients that receive messages
	 */
	public synchronized void add(ClientThread client) {
		clientList.add(client);
	}

	/*
	 * gets a client and removes it from the list of clients that receive messages
	 */
	public synchronized void remove(ClientThread client) {
		clientList.remove(client);
		this.sendremoveMessage(client.getClientName());
	}

	/* gets a message and sends it to all the clients in the list */
	public synchronized void sendMsg(ChatMsg msg) {
		try {
			ObjectOutputStream tempOut;
			Iterator<ClientThread> itr = clientList.iterator();
			while (itr.hasNext()) {
				tempOut = itr.next().getOos();
				tempOut.writeObject(msg);
				tempOut.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * gets a name of the joining memeber and sends a join message to all clients in
	 * the list
	 */
	public void sendJoinMessage(String name) {
		Iterator<ClientThread> itr = clientList.iterator();
		String msg = "";
		while (itr.hasNext()) {
			msg += " " + ((itr.next())).getClientName();
		}
		this.sendMsg(new ChatMsg(name, MsgType.JOIN, msg));
	}

	/*
	 * gets a name of the leaving memeber and sends a exit message to all clients in
	 * the list
	 */
	public void sendremoveMessage(String name) {
		Iterator<ClientThread> itr = clientList.iterator();
		String msg = "";
		while (itr.hasNext()) {
			msg += " " + ((itr.next())).getClientName();
		}
		this.sendMsg(new ChatMsg(name, MsgType.LEFT, msg));
	}
}
