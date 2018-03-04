package controller;

import model.Actor;
import model.ComponentType;
import model.Object;
import model.Party;

public class PartyController extends Controller {
	public void makeNewParty(Party party, int x, int y) {
    	if (party.getType() == ComponentType.ACTOR) {
            Object object = new Object(x, y, ComponentType.OBJECT, "Empty");
            removeParty(party);
            addParty(object);
        } else {
            Actor actor = new Actor(x, y, ComponentType.ACTOR, "instance", "class");
            removeParty(party);
            addParty(actor);
        }
    }
}
