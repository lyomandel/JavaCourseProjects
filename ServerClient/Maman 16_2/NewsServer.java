import java.net.*;
import java.io.*;

/*News Server broadcasting news messages to all registered clients*/
public class NewsServer {
	public static void main(String[] args) {
		NewsServer server = new NewsServer(7777);
	}

	DatagramSocket serverSocket = null;
	boolean listening;
	NewsSender sender;

	/* Constructor */
	public NewsServer(int port) {
		try {
			listening = true;
			serverSocket = new DatagramSocket(port);
			sender = new NewsSender(serverSocket);
			NewsInput newsInput = new NewsInput(sender);
			this.start();
		} catch (

		IOException ioe) {
			System.err.println("Couldn't listen on port 7");
			System.exit(-1);
		}

	}

	/* Start listening to join/exit requests from clients and act accordingly */
	public void start() {
		while (true) {
			DatagramPacket packet;
			while (listening) {
				byte[] buf = new byte[256];
				packet = new DatagramPacket(buf, buf.length);
				try {
					serverSocket.receive(packet);
					if ((new String(packet.getData())).startsWith("join")) {
						sender.addClient(packet);
					} else if ((new String(packet.getData())).startsWith("exit"))
						sender.removeClient(packet);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/* News input thread that waits for news messages from the user */
	public class NewsInput extends Thread {
		BufferedReader buffer;
		NewsSender sender;

		/* Constructor - gets NewsSender */
		public NewsInput(NewsSender sender) {
			buffer = new BufferedReader(new InputStreamReader(System.in));
			this.sender = sender;
			this.start();
		}

		@Override
		public void run() {
			/* wait for news input from the user and broadcast it using NewsSender */
			while (true) {
				try {
					System.out.println("!Enter the NEWS!");
					String line = buffer.readLine();
					sender.sendMsg(line);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
