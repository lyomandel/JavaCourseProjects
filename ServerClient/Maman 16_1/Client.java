import java.io.*;
import java.net.Socket;

import javax.swing.*;

import java.net.*;
import java.awt.*;
import java.awt.event.*;

/*A Chat client class with gui components*/
public class Client extends JFrame {
	ObjectOutputStream out;
	ObjectInputStream in;
	Socket socket = null;
	TextArea taChat, taClients;
	TextField tfMsg;
	JButton bSend, bExit;
	String name, messages, hostName;
	final int portNumber = 7777;
	MsgReceiver receiver;

	/* main that runs client */
	public static void main(String[] args) {
		Client client = new Client();
	}

	/* Constructor */
	public Client() {
		/* ask user to enter a name */
		this.name = JOptionPane.showInputDialog("Enter a user name: ", JOptionPane.OK_OPTION);
		/* ask user to enter host name to connect to */
		this.hostName = JOptionPane.showInputDialog("Enter Host Name (localhost for same pc): ", JOptionPane.OK_OPTION);
		/* try open a connection */
		try {
			socket = new Socket(hostName, portNumber);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			MsgReceiver receiver = new MsgReceiver(in);
			out.writeObject(new ChatMsg(name, MsgType.CHAT, "Hi!"));
			out.flush();
			receiver.start();
		} catch (IOException e) {
			JOptionPane.showInternalMessageDialog(null, "Connection error!!");
			return;
		}
		/* initiate gui components */
		messages = "";
		taChat = new TextArea();
		taClients = new TextArea();
		tfMsg = new TextField();
		bSend = new JButton();
		bSend.setText("SEND MESSAGE");
		bSend.addActionListener(new btnPrseed());
		setLayout(new BorderLayout());
		bExit = new JButton();
		bExit.setText("Exit");
		bExit.addActionListener(new exitPressed());
		add(BorderLayout.NORTH, tfMsg);
		add(BorderLayout.CENTER, taChat);
		add(BorderLayout.WEST, taClients);
		add(BorderLayout.SOUTH, bSend);
		add(BorderLayout.EAST, bExit);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	/* Action listener for send button */
	class btnPrseed implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				ChatMsg msg = new ChatMsg(name, MsgType.CHAT, tfMsg.getText());
				out.writeObject(msg);
				tfMsg.setText("");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/* Action listener for exit button */
	class exitPressed implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				System.exit(ABORT);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/* Message receiver thread in charge of incoming messages */
	class MsgReceiver extends Thread {
		ObjectInputStream input;
		boolean isRunning;

		/* Constructor */
		public MsgReceiver(ObjectInputStream input) {
			this.input = input;
			this.isRunning = true;
		}

		public void setIsRunning(boolean isRunning) {
			this.isRunning = isRunning;
		}

		/* Update names of chat memebers */
		private void updateNames(ChatMsg msg) {
			String[] names = msg.getMsg().split(" ");
			String clients = "";
			for (String name : names) {
				clients += name + "\n";
			}
			taClients.setText(clients);
		}

		@Override
		public void run() {
			ChatMsg msg;
			try {
				while (isRunning) {
					msg = (ChatMsg) (input.readObject());
					/*Check what type of message was received and act accordingly*/
					switch (msg.getType()) {
					case CHAT:
						if (messages.length() > 1000)
							messages = "";
						messages += msg.getName() + ": " + msg.getMsg() + "\n";
						taChat.setText(messages);
						break;

					case JOIN:
						updateNames(msg);
						messages += msg.getName() + " has joined! " + "\n";
						taChat.setText(messages);
						break;
					case LEFT:
						updateNames(msg);
						messages += msg.getName() + " has left! " + "\n";
						taChat.setText(messages);
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
