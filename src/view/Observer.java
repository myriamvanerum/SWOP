package view;

import model.Party;

public interface Observer {
	public void onDeleteParty(Party party);
	public void onChangeParty(Party party, Party partyNew);
}
