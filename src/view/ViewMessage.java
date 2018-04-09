package view;

import java.awt.Graphics2D;

import model.Message;

public class ViewMessage {

	public ViewMessage(Message message) {
		// TODO Auto-generated constructor stub
	}

	public ViewMessage(ViewMessage viewMessage) {
		// TODO copy all parameters from viewMessage into new viewMessage
	}
	
	public void drawCom(Graphics2D g) {
		draw(g);
	}
	
	public void drawSeq(Graphics2D g) {
		draw(g);
		// TODO draw lifeline
	}
	
	public void draw(Graphics2D g) {}
}
