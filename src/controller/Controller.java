package controller;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import domain.*;
import domain.Object;

/*
 * A controller class
 * 
 * @author SWOP groep 03
 */
public class Controller {
	
	private DiagramType diagramType = DiagramType.SEQUENCE;
	
    public ArrayList<Party> parties = new ArrayList<>();
    public ArrayList<Message> messages = new ArrayList<>();
    
    public Component selectedParty = null;

    public DiagramType getDiagramType() {
        return diagramType;
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

    public void removeComponent(Component object){
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
    
    private void makeNewParty(Party party, int x, int y) {
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
    
    public void checkAndSelect(int x, int y) {
        Component selectable = checkCoordinate(x, y);

        if (selectedParty != null) {
            unselectComponent();
        }
        selectComponent(selectable);
    }
    
    private void selectComponent(Component component) {

        if(component != null) {
            selectedParty = component;
            selectedParty.setSelected();
        }
    }

    private void unselectComponent() {
        selectedParty.unselect();
        selectedParty = null;
    }

    public void deleteSelectedComponent() {
        removeComponent(selectedParty);
        unselectComponent();
    }
    
    private void drawActor(Graphics2D g, double x, double y, String label, int size, int totalHeight) {
    	Shape c = new Ellipse2D.Double(x - size, y - size, 2.0 * size, 2.0 * size);
        g.draw(c);
        // Draw body actor
        g.draw(new Line2D.Double(x, y + size, x, y + size + 50));
        // Draw arms actor
        g.draw(new Line2D.Double(x - 20, y + size + 25, x, y + size + 5));
        g.draw(new Line2D.Double(x, y + size + 5, x+20, y + size + 25));
        // draw legs actor
        g.draw(new Line2D.Double(x - 20, y + size + 70, x, y + size + 50));
        g.draw(new Line2D.Double(x , y + size + 50, x + 20, y + size + 70));
        // draw label
        g.drawString(label,(int)(x+ ((size/2) - label.length() * 3)),(int)(y) + totalHeight);
    }
    
    private void drawObject(Graphics2D g, double x, double y, String label, int height, int width) {
    	Rectangle r = new Rectangle((int)x,(int)y,width,height);
        g.drawString(label,(int)(x+ ((width/2) - label.length() * 2)),(int)(y) + height/2);
        g.draw(r);
    }
    
    private void drawLifeline(Graphics g, int x, int startY, int endY) {
    	Graphics2D g2d = (Graphics2D) g.create();
        Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
        g2d.setStroke(dashed);
        g2d.draw(new Line2D.Double(x, startY, x, endY));
    }
    
    private void drawComponent(Graphics g, Party component) {
    	Graphics2D g2 = (Graphics2D) g;
    	int startLifelineX = 0, startLifelineY = 0;
    	
        if (component instanceof Actor) {
        	drawActor(g2, component.getXSeq(), component.getYSeq(), component.getLabel(), 20, 120);
        	startLifelineX = (int) component.getXSeq();
        	startLifelineY = (int) component.getYSeq() + 125;
        }else if (component instanceof Object) {
			drawObject(g2,component.getXSeq(), component.getYSeq(), component.getLabel(), 80, 80);
			startLifelineX = (int)component.getXSeq() + 40;
        	startLifelineY = (int)component.getYSeq() + 80;
        }
        
        drawLifeline(g, startLifelineX, startLifelineY, startLifelineY + 400);
	}
    
    public void paintScreen(Graphics2D g) {
    	if (getDiagramType() == DiagramType.COMMUNICATION) {
        	for (Party component : getParties()) {
        		if (component instanceof Actor)
                	drawActor(g, component.getXCom(), component.getYCom(), component.getLabel(), 20, 120);
        		else if (component instanceof Object)
        			drawObject(g,component.getXCom(), component.getYCom(), component.getLabel(), 80, 80);
        	}

            /*for (Message message : controller.getMessages()) {
                message.paintComponentCom(g2);
            }*/
    	} else if (getDiagramType() == DiagramType.SEQUENCE) {
    		for (Party component : getParties()) 
    			drawComponent(g, component);

            /*for (Message message : controller.getMessages()) {
                message.paintComponentSeq(g2);
            }*/
    	}
    }
    
    public void doubleClick(int x, int y) {
    	Party party = checkCoordinate(x, y);
        
        if (party == null) {
            addComponent(x, y);
        } else {
        	if (getDiagramType() == DiagramType.COMMUNICATION) {
        		makeNewParty(party, (int)party.getXCom(), (int)party.getYCom());
        		
        	} else if (getDiagramType() == DiagramType.SEQUENCE){
        		makeNewParty(party, (int)party.getXSeq(), (int)party.getYSeq());
        	}                
        }
    }
    
    public void singleClick(int x, int y) {
    	if (checkCoordinate(x, y) == null && selectedParty != null) {
            unselectComponent();
        }
    }
    
    public void drag(int x, int y) {
    	if (selectedParty != null && selectedParty instanceof Party) {
            moveComponent((Party) selectedParty, x, y);
        }
    }

}
