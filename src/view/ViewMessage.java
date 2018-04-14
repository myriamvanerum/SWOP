package view;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import model.Component;
import model.Message;

public class ViewMessage extends ViewComponent {
	Message message;
	ViewParty sender;
	ViewParty receiver;
	ViewActivationBar activationBar;

	public ViewMessage(Message message) {
		// TODO Auto-generated constructor stub
	}
	
	public ViewMessage(Message message, Point2D position, Point2D windowPosition, ViewParty sender, ViewParty receiver) {
		viewLabel = new ViewLabel();
		activationBar = new ViewActivationBar();
		// TODO
		setMessage(message);
		setPositionCom(new Point2D.Double(position.getX() - windowPosition.getX(), position.getY() - windowPosition.getY()+25));
		setPositionSeq(new Point2D.Double(position.getX() - windowPosition.getX(), position.getY() - windowPosition.getY()));
		setSender(sender);
		setReceiver(receiver);
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

	public void drawCom(Graphics2D g, Point2D windowPosition) {
		Point2D sender = getSender().getPositionCom();
		Point2D receiver = getReceiver().getPositionCom();
		draw(g, (int) (sender.getX() + windowPosition.getX() +  80), (int) (receiver.getX()+ windowPosition.getX()), (int) (sender.getY() + windowPosition.getY() + 25));
	}
	
	public void drawSeq(Graphics2D g) {
		ViewLifeLine senderLifeline = getSender().getViewLifeLine();
		ViewLifeLine receiverLifeline = getReceiver().getViewLifeLine();
		draw(g, senderLifeline.getX(), receiverLifeline.getX(), (int) getPositionSeq().getY());
	//	activationBar.draw(g, (int)getSender().getPositionSeq().getY(), (int)getSender().getPositionSeq().getY() + 10, senderLifeline.getX()-10);
	}
	
	public void draw(Graphics2D g, int xSender, int xReceiver, int y) {}
		
	@Override 
	public Component getComponent() {
		return this.getMessage();
	}
}
