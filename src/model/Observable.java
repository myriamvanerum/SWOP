package model;

import java.awt.geom.Point2D;

import view.Observer;

public interface Observable {
	public void addObserver(Observer o);
	public void removeObserver(Observer o);
	public void notifyDelete(Party party);
	public void notifyChangeType(Party party, Party partyNew);
	public void notifyAdd(Party party, Point2D position);
}
