package view;

import controller.Controller;
import model.*;
import model.Object;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public class DiagramWindow extends CanvasWindow {

    private Controller controller = new Controller();
    private Selectable selectedParty = null;
    private DiagramType diagramType = DiagramType.SEQUENCE;

    public enum DiagramType {SEQUENCE, COMMUNICATION};

    public DiagramWindow(String title) {
        super(title);
    }
    
    public DiagramType getDiagramType() {
        return diagramType;
    }

    public void setDiagramType(DiagramType diagramType) {
        this.diagramType = diagramType;
        repaint();
    }

    @Override
    protected void paint(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        
        g.drawString(this.getDiagramType().toString() + " DIAGRAM", 10, 10);
        
        if (this.getDiagramType() == DiagramType.COMMUNICATION) {
        	for (Party component : controller.getParties()) {
        		if (component instanceof Actor)
                	drawStickman(g2, component.getXCom(), component.getYCom(), component.getActorLabel(), 20, 120);
        		else if (component instanceof Object)
        			drawObject(g2,component.getXCom(), component.getYCom(), component.getLabel(), 80, 80);
        	}

            /*for (Message message : controller.getMessages()) {
                message.paintComponentCom(g2);
            }*/
    	} else if (this.getDiagramType() == DiagramType.SEQUENCE) {
    		for (Party component : controller.getParties()) 
    			drawComponent(g, component);

            /*for (Message message : controller.getMessages()) {
                message.paintComponentSeq(g2);
            }*/
    	}
    }    

	@Override
    protected void handleMouseEvent(int id, int x, int y, int clickCount) {
        if (id == MouseEvent.MOUSE_CLICKED && clickCount == 1) {
            if (checkCoordinate(x, y) == null && selectedParty != null) {
                unselectComponent();
            }
            repaint();
        }
        if (id == MouseEvent.MOUSE_PRESSED) {
            checkAndSelect(x, y);
            repaint();
        }
        if (id == MouseEvent.MOUSE_CLICKED && clickCount == 2) {      
        	// dubbel klik op label of lifeline mag geen effect hebben   
            Party party = checkCoordinate(x, y);
            
            if (party == null) {
                addComponent(x, y);
            } else {
            	if (this.getDiagramType() == DiagramType.COMMUNICATION) {
            		makeNewParty(party, (int)party.getXCom(), (int)party.getYCom());
            		
            	} else if (this.getDiagramType() == DiagramType.SEQUENCE){
            		makeNewParty(party, (int)party.getXSeq(), (int)party.getYSeq());
            	}                
                repaint();
            }
        }

        if (id == MouseEvent.MOUSE_DRAGGED) {
            if (selectedParty != null && selectedParty instanceof Party) {
                moveComponent((Party) selectedParty, x, y);
            }
        }
    }   
    
    // Keyboard keys
    @Override
    protected void handleKeyEvent(int id, int keyCode, char keyChar) {
        switch (keyCode) {
            case KeyEvent.VK_I:
                controller.addMessage(new InvocationMessage("Hello", controller.getParties().get(0), controller.getParties().get(1)));
                repaint();
                break;

            case KeyEvent.VK_TAB:
                switchDiagram();
                break;

            case KeyEvent.VK_BACK_SPACE:
                deleteSelectedComponent();
                break;
        }
    }


    // check if there is a component in the clicked coordinates
    private Party checkCoordinate(int x, int y) {
        for (Party component : controller.getParties()) {
        	if (this.getDiagramType() == DiagramType.COMMUNICATION) {
        		 if (isComponent(component, x, y, component.getXCom(), component.getYCom()))
        	        	return component;        			
        	} else if (this.getDiagramType() == DiagramType.SEQUENCE) {
        		if (isComponent(component, x, y, component.getXSeq(), component.getYSeq()))
    	        	return component;  
        	}
        }
        return null;
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

	private void moveComponent(Party component, int x, int y) {
    	if (this.getDiagramType() == DiagramType.COMMUNICATION) {
    		component.setXCom(x);
            component.setY(y);
    	} else {
    		component.setXSeq(x);
    	}
        
        repaint();
    }

    private void checkAndSelect(int x, int y) {
        Selectable selectable = checkCoordinate(x, y);

        if (selectedParty != null) {
            unselectComponent();
        }
        selectComponent(selectable);
    }

    private void selectComponent(Selectable component) {

        if(component != null) {
            selectedParty = component;
            selectedParty.setSelected();
            repaint();
        }
    }

    private void unselectComponent() {
        selectedParty.unselect();
        selectedParty = null;
        repaint();
    }

    private void switchDiagram() {
        switch (this.diagramType) {
            case SEQUENCE:
                this.setDiagramType(DiagramType.COMMUNICATION);
                break;
            case COMMUNICATION:
                this.setDiagramType(DiagramType.SEQUENCE);
                break;
            default:
                break;
        }
    }

    private void deleteSelectedComponent() {
        controller.removeSelectable(selectedParty);
        unselectComponent();
        repaint();
    }

    private void addComponent(int x, int y) {
    	Party component = new Object(x, y, ComponentType.OBJECT, "Object");
    	controller.addParty(component);
        repaint();
    }
    
    private void drawStickman(Graphics2D g, double x, double y, String label, int size, int totalHeight) {
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
    
    public void makeNewParty(Party party, int x, int y) {
    	if (party.getType() == ComponentType.ACTOR) {
            Object object = new Object(x, y, ComponentType.OBJECT, "Empty");
            controller.removeParty(party);
            controller.addParty(object);
        } else {
            Actor actor = new Actor(x, y, ComponentType.ACTOR, "instance", "class");
            controller.removeParty(party);
            controller.addParty(actor);
        }
    }
    
    private void drawComponent(Graphics g, Party component) {
    	Graphics2D g2 = (Graphics2D) g;
    	int startLifelineX = 0, startLifelineY = 0;
    	
        if (component instanceof Actor) {
        	drawStickman(g2, component.getXSeq(), component.getYSeq(), component.getActorLabel(), 20, 120);
        	startLifelineX = (int) component.getXSeq();
        	startLifelineY = (int) component.getYSeq() + 125;
        }else if (component instanceof Object) {
			drawObject(g2,component.getXSeq(), component.getYSeq(), component.getLabel(), 80, 80);
			startLifelineX = (int)component.getXSeq() + 40;
        	startLifelineY = (int)component.getYSeq() + 80;
        }
        
        drawLifeline(g, startLifelineX, startLifelineY, startLifelineY + 400);
	}
}
