package view;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import model.Party;

public class ViewParty {
	private Party party;
	
	private ViewLabel viewLabel;
	
	// ik zou de positie relatief tegenover het subwindow bijhouden, dat lijkt mij het gemakkelijkste
	Point2D positionCom; 
	Point2D positionSeq;
	
	public ViewParty(Party party, Point2D clickPosition, Point2D windowPosition) {
		setParty(party);
		viewLabel = new ViewLabel();
		Point2D positionInWindow = clickPosition;
		positionInWindow.setLocation(clickPosition.getX() - windowPosition.getX(), clickPosition.getY() - windowPosition.getY());
		setPositionCom(positionInWindow);
		positionInWindow.setLocation(clickPosition.getX() - windowPosition.getX(), 40);
		setPositionSeq(positionInWindow);
	}
	
	public ViewParty(ViewParty viewParty) {
		// TODO copy all parameters from viewParty into new viewParty
		setParty(viewParty.getParty());
		viewLabel = new ViewLabel(viewParty.getViewLabel());
		setPositionCom(viewParty.getPositionCom());
		setPositionSeq(viewParty.getPositionSeq());
	}
	
	// TODO "hook" --> template pattern
	public boolean checkCoordinates(Point2D coordinates, Point2D position) { 
		return false;
	}
	
	public void drawCom(Graphics2D g, Point2D windowPosition) {
		draw(g, getPositionCom());
	}
	
	public void drawSeq(Graphics2D g,  Point2D windowPosition) {
		draw(g, getPositionSeq());
		// TODO draw lifeline
	}
	
	public void draw(Graphics2D g, Point2D position) {}

	public Party getParty() {
		return party;
	}

	public void setParty(Party party) {
		this.party = party;
	}

	public Point2D getPositionCom() {
		return positionCom;
	}

	public void setPositionCom(Point2D positionCom) {
		this.positionCom = positionCom;
	}

	public Point2D getPositionSeq() {
		return positionSeq;
	}

	public void setPositionSeq(Point2D positionSeq) {
		this.positionSeq = positionSeq;
	}

	public ViewLabel getViewLabel() {
		return viewLabel;
	}

	public void setViewLabel(ViewLabel viewLabel) {
		this.viewLabel = viewLabel;
	}
}
