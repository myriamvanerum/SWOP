package controller;

import model.Message;
import model.Party;
import model.Selectable;

import java.util.ArrayList;

public class Controller {

    public ArrayList<Party> parties = new ArrayList<>();
    public ArrayList<Message> messages = new ArrayList<>();


    public void addParty(Party party){
        parties.add(party);
        System.out.println(parties);
    }

    public void removeParty(Party party){
        parties.remove(party);
        messages.remove(party.getSendingMessage());
        System.out.println(parties);
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

}
