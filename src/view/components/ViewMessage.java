package view.components;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import model.Component;
import model.Message;
import view.InvocationState;
import view.SubWindow;
/**
 * ViewMessage class. Controls the drawing of Messages
 * @author groep 03
 *
 */
public class ViewMessage extends ViewComponent {
	Message message;
	ViewParty sender;
	ViewParty receiver;
	ViewActivationBar activationBar;
	
	/**
	 * ViewMessage Constructor
	 * @param message
	 * 		Message to draw
	 * @param position
	 * 		Message position
	 * @param windowPosition
	 * 		SubWindow position
	 * @param sender
	 * 		Message sender
	 * @param receiver
	 * 		Message receiver
	 */
	public ViewMessage(Message message, Point2D position, Point2D windowPosition, ViewParty sender, ViewParty receiver) {
		viewLabel = new ViewLabel(message.getLabel());
		activationBar = new ViewActivationBar();
		setMessage(message);
		setPositionSeq(new Point2D.Double(position.getX() - windowPosition.getX(), position.getY() - windowPosition.getY()-25));
		setSender(sender);
		setReceiver(receiver);
	}
	
	/**
	 * Copy Constructor
	 * @param message
	 * 		Message to draw
	 * @param position
	 * 		Message position
	 * @param sender
	 * 		Message sender
	 * @param receiver
	 * 		Message receiver
	 */
	public ViewMessage(ViewMessage viewMessage) {
		viewLabel = new ViewLabel(viewMessage.getViewLabel());
		activationBar = new ViewActivationBar();
		setMessage(viewMessage.getMessage());
		setPositionSeq(new Point2D.Double(viewMessage.getPositionSeq().getX(), viewMessage.getPositionSeq().getY()));
		setSender(viewMessage.getSender());
		setReceiver(viewMessage.getReceiver());
	}
	
	/**
	 * Draw a Message in a Communication diagram
	 * @param g
	 * 		Graphics class
	 * @param windowPosition
	 * 		SubWindow position
	 */
	public void drawCom(Graphics2D g, Point2D windowPosition) {
		Point2D sender = getSender().getPositionCom();
		Point2D receiver = getReceiver().getPositionCom();
		draw(g, (int) (sender.getX() + windowPosition.getX() +  80), 
				(int) (receiver.getX()+ windowPosition.getX()), 
				(int) (sender.getY() + windowPosition.getY() + 25),
				(int) (receiver.getY() + windowPosition.getY() + 25));
	}
	
	/**
	 * Draw a Message in a Communication diagram
	 * @param g
	 * 		Graphics class
	 * @param windowPosition
	 * 		SubWindow position
	 */
	public void drawSeq(Graphics2D g, Point2D windowPosition) {
		ViewLifeLine senderLifeline = getSender().getViewLifeLine();
		ViewLifeLine receiverLifeline = getReceiver().getViewLifeLine();
		int xSender = senderLifeline.getX();
		int xReceiver = receiverLifeline.getX();
		int y = (int) (getPositionSeq().getY() + windowPosition.getY());
				
		draw(g, xSender, xReceiver, y, y);
		
		if (this.getClass() == ViewInvocationMessage.class) {
			activationBar.draw(g, xSender - 5, y - 5);
			activationBar.draw(g, xReceiver - 5, y - 5);	
		}
	}
	
	/**
	 * Draw a message
	 * @param g
	 * 		Graphics class
	 * @param xSender
	 * 		Sender x coordinate
	 * @param xReceiver
	 * 		Receiver x coordinate
	 * @param ySender
	 * 		Sender y coordinate
	 * @param yReceiver
	 * 		Receiver y coordinate
	 */
	public void draw(Graphics2D g, int xSender, int xReceiver, int ySender, int yReceiver) {}
	
	public ViewMessage copy() {return null;}
	
	/* GETTERS AND SETTERS */
	
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

	@Override 
	public Component getComponent() {
		return this.getMessage();
	}
	

	@Override
	public void setLabelState(SubWindow subwindow) {
		subwindow.setLabelState(new InvocationState(subwindow));
	}
}
