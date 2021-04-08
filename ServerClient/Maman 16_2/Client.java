import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.*;

import javax.swing.*;

/*A udp client for news broadcast*/
public class Client {
	public static void main(String[] args) {
		Client client = new Client();
	}

	JFrame frame;
	JButton bexit;
	JButton bclear;
	JButton bjoin;
	JTextArea tArea;
	String hostName;
	DatagramSocket socket;
	String newsStream;
	boolean on;

	/* Constructor */
	public Client() {
		/* ask for news broadcast name */
		this.hostName = JOptionPane.showInputDialog("Enter Host Name (localhost for same pc): ", JOptionPane.OK_OPTION);
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/* set up gui components */
		on = true;
		frame = new JFrame();
		bexit = new JButton();
		bclear = new JButton();
		bjoin = new JButton();
		tArea = new JTextArea();
		tArea.setSize(50, 50);
		bexit.setText("Exit");
		bclear.setText("Clear");
		bjoin.setText("Join");
		newsStream = "";
		tArea.setText("**WAITING FOR NEWS***");
		bclear.addActionListener(new clearPressed());
		bexit.addActionListener(new exitPressed());
		bjoin.addActionListener(new joinPressed());
		frame.add(BorderLayout.EAST, bclear);
		frame.add(BorderLayout.SOUTH, bexit);
		frame.add(BorderLayout.CENTER, tArea);
		frame.add(BorderLayout.WEST, bjoin);
		frame.setSize(100, 100);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.pack();
		frame.repaint();
		frame.setVisible(true);
		byte[] buf = new byte[256];
		InetAddress address;
		try {
			address = InetAddress.getByName(hostName);
			buf = "join".getBytes();
			DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 7777);
			MsgReceiver receiver = new MsgReceiver(socket, hostName);
			receiver.start();
			socket.send(packet);
			newsStream = "";
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * Action listener for exit button - send an exit request and close the program
	 */
	class exitPressed implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				InetAddress address = InetAddress.getByName(hostName);
				String buf = "exit";
				DatagramPacket packet = new DatagramPacket(buf.getBytes(), buf.length(), address, 7777);
				socket.send(packet);
				System.exit(1);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/* Action listener for clear button - clears the text area */
	class clearPressed implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			newsStream = "";
			tArea.setText(newsStream);
		}
	}

	/* Action listener for join button - sends a join message to the server */
	class joinPressed implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			InetAddress address;
			try {
				address = InetAddress.getByName(hostName);
				String buf = "join";
				DatagramPacket packet = new DatagramPacket(buf.getBytes(), buf.length(), address, 7777);
				socket.send(packet);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}

	/* Message receiver thread */
	class MsgReceiver extends Thread {
		DatagramSocket socket;
		String hostName;

		/* Constructor - gets a socket and hostname */
		public MsgReceiver(DatagramSocket socket, String hostName) {
			this.socket = socket;
			this.hostName = hostName;
		}

		/* Listens to incoming packets and adds them to the text area */
		@Override
		public void run() {
			while (on) {
				byte[] buf = new byte[256];
				DatagramPacket packet;
				try {
					packet = new DatagramPacket(buf, buf.length, InetAddress.getByName(hostName), 7777);
					socket.receive(packet);
					newsStream += new String(packet.getData()) + "\n";
					tArea.setText(newsStream);
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
