package controller;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import model.*;
import model.Object;
import view.DiagramWindow;

/*
 * A controller class
 * 
 * @author SWOP groep 03
 */
public class Controller extends ObjectFocusListener implements Draw {
	
	private DiagramType diagramType = DiagramType.SEQUENCE;
	
    public ArrayList<Party> parties = new ArrayList<>();
    public ArrayList<Message> messages = new ArrayList<>();
    
    public DiagramComponent selectedParty = null;

    public DiagramType getDiagramType() {
        return diagramType;
    }
    
    private DiagramWindow view;

    public Controller(DiagramWindow view) {
        this.view = view;
    }

    public void setDiagramType(DiagramType diagramType) {
        this.diagramType = diagramType;
        //repaint();
    }
    
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

    public void removeComponent(DiagramComponent object){
        if (object instanceof Party){
            removeParty((Party) object);
        } else {
            removeMessage((Message) object);
        }
    }

    public ArrayList<Party> getParties(){
        return parties;
    }

    public ArrayList<Message> getMessages(){
        return messages;
    }
    
    private void changeParty(Party party, int x, int y) {
    	if (party.getType() == ComponentType.ACTOR) {
            Object object = new Object(x, y, ComponentType.OBJECT, "Empty");
            removeParty(party);
            addParty(object);
        } else {
            Actor actor = new Actor(x, y, ComponentType.ACTOR, "label");
            removeParty(party);
            addParty(actor);
        }
    }
    
    private boolean isComponent(Party component, int x, int y, double componentX, double componentY) {
		if (component instanceof Actor) {
    		return x >= x - 20 / 2 && x <= x + 20 / 2 && y <= 140;
    	} else if (component instanceof Object) {
			return x >= componentX && 
					x <= componentX + 80 &&
					y >= componentY &&
					y <= componentY + 80;
    	} else return false;
	}
    
 // check if there is a component in the clicked coordinates
    private Party checkCoordinate(int x, int y) {
        for (Party component : getParties()) {
        	if (getDiagramType() == DiagramType.COMMUNICATION) {
        		 if (isComponent(component, x, y, component.getXCom(), component.getYCom()))
        	        	return component;        			
        	} else if (getDiagramType() == DiagramType.SEQUENCE) {
        		if (isComponent(component, x, y, component.getXSeq(), component.getYSeq()))
    	        	return component;  
        	}
        }
        return null;
    }
    
    public void switchDiagram() {
        switch (getDiagramType()) {
            case SEQUENCE:
            	setDiagramType(DiagramType.COMMUNICATION);
                break;
            case COMMUNICATION:
            	setDiagramType(DiagramType.SEQUENCE);
                break;
            default:
                break;
        }
    }
    
    private void addComponent(int x, int y) {
    	Party component = new Object(x, y, ComponentType.OBJECT, "Object");
    	System.out.println("ADDED COMP X:"+x+" Y:" + y);
    	addParty(component);
    }
    
    private void moveComponent(Party component, int x, int y) {
    	if (getDiagramType() == DiagramType.COMMUNICATION) {
    		component.setXCom(x);
            component.setY(y);
    	} else {
    		component.setXSeq(x);
    	}
    }
    
    public void paintScreen(Graphics2D g) {
    	if (getDiagramType() == DiagramType.COMMUNICATION) {
        	for (Party component : getParties()) {
        		setColor(component, g);
        		if (component instanceof Actor)
                	drawActor(g, component.getXCom(), component.getYCom(), component.getLabel(), 20, 120);
        		else if (component instanceof Object)
        			drawObject(g,component.getXCom(), component.getYCom(), component.getLabel(), 80, 80);
        	}     
    	} else if (getDiagramType() == DiagramType.SEQUENCE) {
    		for (Party component : getParties()) {
    			setColor(component, g);
    			drawComponent(g, component);
    		}
    	}
    	
    	for (Message message : getMessages()) {
        	if (message instanceof InvocationMessage) {
        		drawInvocationMessage(g, message.focused(), message.getSender().getXCom(), message.getSender().getYCom(), message.getReceiver().getXCom(), message.getReceiver().getYCom());
        	} else if (message instanceof ResultMessage) {
        		drawResultMessage(g, message.focused(), message.getSender().getXCom(), message.getSender().getYCom(), message.getReceiver().getXCom(), message.getReceiver().getYCom());
        	}
        }
    }
   
    public void handleKeyEvent(int id, int keyCode, char keyChar) {

        switch (keyCode) {
            case KeyEvent.VK_I:
                addMessage(new InvocationMessage("Hello", parties.get(0), parties.get(1)));
                break;

            case KeyEvent.VK_TAB:
                switchDiagram();
                break;

            case KeyEvent.VK_BACK_SPACE:
                deleteFocused();
                break;
        }
    }

    public void handleMouseEvent(int id, int x, int y, int clickCount) {
        
        switch (id) {

            case MouseEvent.MOUSE_PRESSED:
                checkAndFocus(x, y);
                break;

            case MouseEvent.MOUSE_DRAGGED:
                if (getFocusedObject() != null && getFocusedObject() instanceof Party) {
                    moveComponent((Party) getFocusedObject(), x, y);
                }
                break;

            case MouseEvent.MOUSE_CLICKED:
                Party party = checkCoordinate(x, y);
                switch (clickCount) {
                    case 1:
                        if (party == null && getFocusedObject() != null) {
                            unFocus();
                        }
                        break;

                    case 2:
                        if (party == null) {
                            addComponent(x, y);
                        } else {
                            if (getDiagramType() == DiagramType.COMMUNICATION) {
                                changeParty(party, (int) party.getXCom(), (int) party.getYCom());

                            } else if (getDiagramType() == DiagramType.SEQUENCE) {
                                changeParty(party, (int) party.getXSeq(), (int) party.getYSeq());
                            }
                        }
                }
        }
    }

    /* FOCUS METHODS */

    private void focusComponent(Focusable component) {

        if (component != null) {
            focusGained(component);
        }
    }

    private void unFocus() {
        focusLost();
    }
    
    private void checkAndFocus(int x, int y) {
        Focusable selectable = checkCoordinate(x, y);

        if (getFocusedObject() != null) {
            focusLost();
        }
        focusComponent(selectable);
    }
    
    private void deleteFocused() {
        Focusable focusable = getFocusedObject();
        assert focusable != null;
        if (focusable instanceof Party) {
            removeParty((Party) focusable);
        } else
            removeMessage((Message) focusable);
    }
}


