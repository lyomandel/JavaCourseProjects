import java.io.IOException;
import java.net.*;
import java.util.*;

/*News sender thread - sending news messages to all registered clients*/
public class NewsSender extends Thread {
	ArrayList<DatagramPacket> clientList; // list of clients
	DatagramSocket socket;
	int port;

	/* Constructor */
	public NewsSender(DatagramSocket socket) {
		clientList = new ArrayList<DatagramPacket>();
		this.socket = socket;
	}

	/* gets a DatagramPacket and adds it as a client */
	public synchronized void addClient(DatagramPacket client) {
		byte[] buf = new byte[256];
		client.setData(buf);
		clientList.add(client);
	}

	/* gets a DatagramPacket and removes its corresponding client */
	public synchronized void removeClient(DatagramPacket client) {
		byte[] buf = new byte[256];
		client.setData(buf);
		clientList.remove(client);
	}

	/* sends a news message to all registered clients */
	public synchronized void sendMsg(String msg) {
		Iterator itr = clientList.iterator();
		try {
			while (itr.hasNext()) {
				DatagramPacket data = (DatagramPacket) itr.next();
				InetAddress ip = data.getAddress();
				int port = data.getPort();
				byte[] buf = msg.getBytes();
				DatagramPacket packet = new DatagramPacket(buf, buf.length, ip, port);
				socket.send(packet);
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
