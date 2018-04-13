package view;

import java.awt.Graphics2D;

import model.Component;
import model.Message;

public class ViewMessage extends ViewComponent {
	Message message;
	ViewParty sender;
	ViewParty receiver;

	public ViewMessage(Message message) {
		// TODO Auto-generated constructor stub
	}

	public ViewMessage(ViewMessage viewMessage) {
		// TODO copy all parameters from viewMessage into new viewMessage
	}
	
	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public ViewParty getSender() {
		return sender;
	}

	public void setSender(ViewParty sender) {
		this.sender = sender;
	}

	public ViewParty getReceiver() {
		return receiver;
	}

	public void setReceiver(ViewParty receiver) {
		this.receiver = receiver;
	}

	public void drawCom(Graphics2D g) {
		draw(g);
	}
	
	public void drawSeq(Graphics2D g) {
		draw(g);
	}
	
	public void draw(Graphics2D g) {}
		
	@Override 
	public Component getComponent() {
		return this.getMessage();
	}
}
