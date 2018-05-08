package view.windows;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import view.components.ViewInvocationMessage;
import view.components.ViewLabel;
import view.formelements.ListBox;
import view.formelements.TextBox;
import view.formelements.WindowControl;

public class InvocationBox extends DialogBox {
	private ViewInvocationMessage message;
	
	public InvocationBox(ViewInvocationMessage message, Point2D position) {
		this.message = message;
		this.position = position;
		
		// ADD CONTROLS
		this.controls = new ArrayList<WindowControl>();
		//controls.add(new TextBox(new Point2D.Double(position.getX()+10, position.getY()+20), "Method: ", message.getViewLabel()));
		controls.add(new TextBox(new Point2D.Double(position.getX()+10, position.getY()+20), "Invoer: ", new ViewLabel("message label")));
		controls.add(new ListBox(new ArrayList<String>(), new Point2D.Double(position.getX()+10, position.getY()+100)));
		//controls.add(new TextBox(new Point2D.Double(position.getX()+10, position.getY()+70), "New: ", message.getViewLabel()));
		controls.add(new TextBox(new Point2D.Double(position.getX()+10, position.getY()+50), "New: ", new ViewLabel("message label")));
		
		this.currentControl = 0;
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
