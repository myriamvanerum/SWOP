package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import model.Component;
import model.Party;

public class ViewParty extends ViewComponent {
	private Party party;
	protected ViewLifeLine viewLifeLine;

	public ViewParty(Party party, Point2D clickPosition, Point2D windowPosition) {
		setParty(party);
		viewLabel = new ViewLabel();
		viewLifeLine = new ViewLifeLine();
		setPositionCom(new Point2D.Double(clickPosition.getX() - windowPosition.getX(),
				clickPosition.getY() - windowPosition.getY() - 25));
		setPositionSeq(new Point2D.Double(clickPosition.getX() - windowPosition.getX(), 40));
	}

	public ViewParty(ViewParty viewParty) {
		// TODO copy all parameters from viewParty into new viewParty
		setParty(viewParty.getParty());
		setViewLabel(viewParty.getViewLabel());
		setPositionCom(viewParty.getPositionCom());
		setPositionSeq(viewParty.getPositionSeq());
		setViewLifeLine(viewParty.getViewLifeLine());
	}

	// TODO "hook" --> template pattern
	public boolean checkCoordinates(Point2D coordinates, Point2D position, Point2D windowPosiion) {
		return false;
	}

	public boolean checkLabelPosition(Point2D coordinates, Point2D positionState, Point2D windowPosition) {
		return false;
	}

	public void drawCom(Graphics2D g, Point2D windowPosition) {
		setColor(this, g);
		draw(g, positionWindow(getPositionCom(), windowPosition));
	}

	public void drawSeq(Graphics2D g, Point2D windowPosition) {
		setColor(this, g);
		draw(g, positionWindow(getPositionSeq(), windowPosition));
		viewLifeLine.draw(g);
	}

	public Point2D positionWindow(Point2D position, Point2D windowPosition) {		
		return new Point2D.Double(position.getX() + windowPosition.getX(), position.getY() + windowPosition.getY());
	}

	public void draw(Graphics2D g, Point2D position) {
	}

	public Party getParty() {
		return party;
	}

	public void setParty(Party party) {
		this.party = party;
	}

	public ViewLifeLine getViewLifeLine() {
		return viewLifeLine;
	}

	public void setViewLifeLine(ViewLifeLine viewLifeLine) {
		this.viewLifeLine = viewLifeLine;
	}
	
	@Override 
	public Component getComponent() {
		return this.getParty();
	}
}
