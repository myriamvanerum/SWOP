package view;

import java.awt.geom.Point2D;

import model.Message;
import model.Party;

public interface Observer {
	public void onDeleteParty(Party party);
	public void onChangeParty(Party party, Party partyNew);
	public void onAddParty(Party party, Point2D position);
	public void onDeleteMessage(Message message);
	public void onAddMessage(Message message, Point2D position);
}
