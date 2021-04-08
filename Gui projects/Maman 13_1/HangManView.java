import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*A class represents the view of the hang man*/
public class HangManView extends JPanel {
	private int bodyParts;

	/* Constructor - initiates and sets the size of hang man */
	public HangManView() {
		super();
		bodyParts = 0;
		setSize(300, 300);
		setBackground(Color.white);
		setVisible(true);
	}

	/* returns the number of drawn body parts */
	public int getBodyParts() {
		return bodyParts;
	}

	/* sets the number of body parts to be drawn */
	public void setBodyParts(int bodyParts) {
		this.bodyParts = bodyParts;
	}

	/* paints the hang man according to the bodyParts */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		/* Sets background color */
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.BLACK);

		/* Draw the hang man based on bodyParts value */
		for (int i = 0; i < bodyParts; i++) {
			paintBodyPart(g, BodyPart.values()[i]);
		}
		/* Draw stand */
		g.drawLine(100, 300, 100, 50);
		g.drawLine(200, 50, 100, 50);
		g.drawLine(200, 100, 200, 50);

	}

	/* gets a graphics and a body part, paints the corresponding body part */
	private void paintBodyPart(Graphics g, BodyPart bodyPart) {
		switch (bodyPart) {
		case LEFT_LEG:
			g.drawLine(200, 200, 150, 250); // Left leg
			break;
		case RIGHT_LEG:
			g.drawLine(200, 200, 250, 250); // Right leg
			break;
		case LEFT_ARM:
			g.drawLine(150, 150, 200, 175); // Left arm
			break;
		case RIGHT_ARM:
			g.drawLine(250, 150, 200, 175); // Right arm
			break;
		case TORSO:
			g.drawLine(200, 200, 200, 150); // Torso
			break;
		case HEAD:
			g.drawOval(175, 100, 50, 50); // Head
		default:
			break;
		}
	}
}