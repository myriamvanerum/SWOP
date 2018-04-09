package view;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import model.Party;

public class ViewParty {
	private Party party;
	
	protected ViewLabel viewLabel;
	protected ViewLifeLine viewLifeLine;
	
	// ik zou de positie relatief tegenover het subwindow bijhouden, dat lijkt mij het gemakkelijkste
	Point2D positionCom; 
	Point2D positionSeq;
	Point2D drawPosition;
	
	public ViewParty(Party party, Point2D clickPosition, Point2D windowPosition) {
		setParty(party);
		viewLabel = new ViewLabel();
		viewLifeLine = new ViewLifeLine(this);
		setPositionCom(new Point2D.Double(clickPosition.getX() - windowPosition.getX(), clickPosition.getY() - windowPosition.getY()));
		setPositionSeq(new Point2D.Double(clickPosition.getX() - windowPosition.getX(), 40));
	}
	
	public ViewParty(ViewParty viewParty) {
		// TODO copy all parameters from viewParty into new viewParty
		setParty(viewParty.getParty());
		viewLabel = new ViewLabel();
		setPositionCom(viewParty.getPositionCom());
		setPositionSeq(viewParty.getPositionSeq());
	}
	
	// TODO "hook" --> template pattern
	public boolean checkCoordinates(Point2D coordinates, Point2D position, Point2D windowPosiion) { 
		return false;
	}
	
	public boolean checkLabelPosition(Point2D coordinates, Point2D position) { 
		return false;
	}
	
	public void drawCom(Graphics2D g, Point2D windowPosition) {		
		setDrawPosition(new Point2D.Double(getPositionCom().getX() + windowPosition.getX(), getPositionCom().getY() + windowPosition.getY()));
		draw(g, getPositionCom());
	}
	
	public void drawSeq(Graphics2D g, Point2D windowPosition) {
		setDrawPosition(new Point2D.Double(getPositionSeq().getX() + windowPosition.getX(), getPositionSeq().getY() + windowPosition.getY()));
		draw(g, getPositionSeq());
		viewLifeLine.draw(g);
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

	public Point2D getDrawPosition() {
		return drawPosition;
	}

	public void setDrawPosition(Point2D drawPosition) {
		this.drawPosition = drawPosition;
	}	
}
