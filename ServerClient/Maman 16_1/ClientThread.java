import java.io.*;
import java.net.*;

/*A thread representing a chat client */
public class ClientThread extends Thread {
	private boolean on;
	private MsgSender sender;
	private String name;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	/* Constructor */
	public ClientThread(MsgSender sender, String name, ObjectInputStream ois, ObjectOutputStream oos) {
		this.sender = sender;
		this.name = name;
		this.ois = ois;
		this.oos = oos;
		on = true;
	}

	/* Return object output stream */
	public ObjectOutputStream getOos() {
		return oos;
	}

	/* Return client name */
	public String getClientName() {
		return name;
	}

	@Override
	public void run() {
		try {
			/*
			 * Read incoming messages from the client and send to all other clients upon
			 * receiving
			 */
			while (on) {
				ChatMsg input = (ChatMsg) ois.readObject();
				sender.sendMsg(input);
			}

		} catch (SocketException e) {
			on = false;
			sender.remove(this);
		} catch (IOException | ClassNotFoundException e) {
			on = false;
			sender.remove(this);
		}
	}
}
