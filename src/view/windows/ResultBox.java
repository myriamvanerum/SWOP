package view.windows;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import view.components.ViewResultMessage;
import view.formelements.TextBox;
import view.formelements.WindowControl;

public class ResultBox extends DialogBox {
	private ViewResultMessage message;
		
	public ResultBox(ViewResultMessage message, Point2D position) {
		this.message = message;
		this.position = position;
		
		this.controls = new ArrayList<WindowControl>();
		controls.add(new TextBox(new Point2D.Double(position.getX()+10, position.getY()+20), "Invoer: ", message.getViewLabel()));
		this.currentControl = 0;
	}
	
	public ViewResultMessage getMessage() {
		return message;
	}

	public void setMessage(ViewResultMessage message) {
		this.message = message;
	}
		
	public void draw(Graphics2D g) {		
		int x = (int) position.getX();
		int y = (int) position.getY();
		
		// TODO extract method --> to superclass	drawWhiteField()
		// use height and with properties
		g.setColor(Color.WHITE);
		g.fillRect(x, y, 250, 300);
		g.setColor(Color.BLACK);	

		
		drawControls(g);
		
		// TODO	drawBlackBorder()
		// Draw black border
		Rectangle r = new Rectangle(x, y, 250, 300);
		g.draw(r);
	}
}
