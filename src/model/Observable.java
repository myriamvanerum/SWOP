package model;

import java.awt.geom.Point2D;

import view.Observer;

public interface Observable {
	/**
	 * Add an Observer to this Observable
	 */
	public void addObserver(Observer o);
	/**
	 * Remove an Observer from this Observable
	 */
	public void removeObserver(Observer o);
	/**
	 * Notify all Observers a Party has been deleted
	 */
	public void notifyDelete(Party party);
	/**
	 * Notify all Observers the type of a Party has been changed
	 */
	public void notifyChangeType(Party party, Party partyNew);
	/**
	 * Notify all Observers a Party has been added
	 */
	public void notifyAdd(Party party, Point2D position);
	/**
	 * Notify all Observers a Message has been deleted
	 */
	public void notifyDelete(Message message);
	/**
	 * Notify all Observers a Message has been added
	 */
	public void notifyAdd(Message message, Point2D position);
}
