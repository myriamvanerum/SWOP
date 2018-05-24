package view.components;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import domain.Component;
import domain.message.Message;
import view.diagramstate.State;
import view.labelstate.EditInvocationMessageLabelState;
import view.windows.SubWindow;
/**
 * ViewMessage class. Controls the drawing of Messages
 * @author groep 03
 *
 */
public abstract class ViewMessage extends ViewComponent {
	Message message;
	ViewParty sender;
	ViewParty receiver;
	ViewActivationBar activationBar;
	ViewMessage companion;
	
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
	public abstract void draw(Graphics2D g, int xSender, int xReceiver, int ySender, int yReceiver);
	
	 /**
	 * Draw a message
	 * @param g
	 * 		Graphics class
	 * @param state
	 * 			Current WindowState
	 * @param windowPosition
	 * 			The position of the current subwindow
	 */
	public abstract void draw(Graphics2D g, State state, Point2D windowPosition);
	
	/**
	 * Copy the ViewMessage
	 * @return A copy of the ViewMessage
	 */
	public abstract ViewMessage copy();
	
	/**
	 * The position of the message will be changed if the callstack has changed
	 * @param y
	 * 			The y coordinate of the ViewMessage
	 */
	public void moveDownIfBelow(double y) {
		if (getPositionSeq().getY() < y) return;
		System.out.println("Moving Message Lower.");
		Integer heightIncrease = 40;
		setPositionSeq(new Point2D.Double(getPositionSeq().getX(), getPositionSeq().getY() + heightIncrease));
	}

	/**
	 * Draw an activationbar
	 * @param g
	 * 			Graphics class
	 * @param xSender
	 * 			The x coordinate of the sender
	 * @param xReceiver
	 * 			The x coordinate of the receiver
	 * @param yStart
	 * 			The y coordinate of where the activationbar should start
	 * @param yEnd
	 * 			The x coordinate of where the activationbar should start
	 */
	public void drawActivationBar(Graphics2D g, int xSender, int xReceiver, int yStart, int yEnd) {}

	
	public void changeViewParty(ViewParty viewParty, ViewParty newViewParty) {
		if (viewParty.equals(getSender())) setSender(newViewParty);
		if (viewParty.equals(getReceiver())) setReceiver(newViewParty);
	}
	
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

	public ViewMessage getCompanion() {
		return companion;
	}

	public void setCompanion(ViewMessage companion) {
		this.companion = companion;
	}
	
	@Override 
	public Component getComponent() {
		return this.getMessage();
	}

	@Override
	public void setLabelState(SubWindow subwindow) {
		subwindow.setLabelState(new EditInvocationMessageLabelState(subwindow, getViewLabel()));
	}
}
