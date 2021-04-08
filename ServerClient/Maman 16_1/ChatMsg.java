import java.io.Serializable;

/*A chat message class. a message has the name of the sender, the string of the message itself and the message type ( chat or member list)*/
public class ChatMsg implements Serializable {
	private String name;
	private String msg;
	private MsgType type;

	/* Constructor */
	public ChatMsg(String name, MsgType type, String msg) {
		this.name = name;
		this.type = type;
		this.msg = msg;
	}

	/* returns the name of the sender */
	public String getName() {
		return name;
	}

	/* returns the message type */
	public MsgType getType() {
		return type;
	}

	/* returns the message content */
	public String getMsg() {
		return msg;
	}
/*sets the chat message with new variables*/
	public void setMsg(String name, MsgType type, String msg) {
		this.name = name;
		this.type = type;
		this.msg = msg;
	}
}
