package controller;

import model.Actor;
import model.ComponentType;
import model.Message;
import model.Object;
import model.Party;
import model.Selectable;

import java.util.ArrayList;

public class Controller {

    public ArrayList<Party> parties = new ArrayList<>();
    public ArrayList<Message> messages = new ArrayList<>();


    public void addParty(Party party){
        parties.add(party);
    }

    public void removeParty(Party party){
        parties.remove(party);
        messages.remove(party.getSendingMessage());
    }

    public void addMessage(Message message){
        messages.add(message);
    }

    public void removeMessage(Message message){
        messages.remove(message);
    }

    public void removeSelectable(Selectable object){
        if (object instanceof Party){
            removeParty((Party) object);
        }else {
            removeMessage((Message) object);
        }
    }

    public ArrayList<Party> getParties(){
        return parties;
    }

    public ArrayList<Message> getMessages(){
        return messages;
    }
    
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
