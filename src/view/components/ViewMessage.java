package view.components;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import domain.Component;
import domain.message.Message;
import view.labelstate.InvocationState;
import view.windows.DiagramWindow;
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
		setViewLabel(new ViewLabel(message.getLabel()));
		setActivationBar(new ViewActivationBar());
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
		setViewLabel(new ViewLabel(viewMessage.getViewLabel()));
		setActivationBar(new ViewActivationBar());
		setMessage(viewMessage.getMessage());
		setPositionSeq(new Point2D.Double(viewMessage.getPositionSeq().getX(), viewMessage.getPositionSeq().getY()));
		setSender(viewMessage.getSender());
		setReceiver(viewMessage.getReceiver());
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

	public ViewActivationBar getActivationBar() {
		return activationBar;
	}

	public void setActivationBar(ViewActivationBar activationBar) {
		this.activationBar = activationBar;
	}

	@Override 
	public Component getComponent() {
		return this.getMessage();
	}
	

	@Override
	public void setLabelState(DiagramWindow subwindow) {
		subwindow.setLabelState(new InvocationState(subwindow));
	}

	public void moveDownIfBelow(double y) {
		if (getPositionSeq().getY() >= y)
			setPositionSeq(new Point2D.Double(getPositionSeq().getX(), getPositionSeq().getY() + 50));
	}
}
