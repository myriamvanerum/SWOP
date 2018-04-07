package view;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import model.Party;

public class ViewParty {
	private Party party;
	
	public ViewParty(Party party) {
		setParty(party);
	}
	
	public ViewParty(ViewParty viewParty) {
		// copy all parameters from viewParty into new viewParty
	}

	// ik zou de positie relatief tegenover het subwindow bijhouden, dat lijkt mij het gemakkelijkste
	Point2D positionCom; 
	Point2D positionSeq;

	
	// TODO "hook" --> template pattern
	public boolean checkCoordinates(Point2D coordinates) { 
		return false;
	}
	
	public void drawCom(Graphics2D g) {
		draw(g);
	}
	
	public void drawSeq(Graphics2D g) {
		
	}
	
	public void draw(Graphics2D g) {
		
	}

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
}
