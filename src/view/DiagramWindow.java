package view;

import controller.*;
import domain.*;
import domain.Component;
import domain.Object;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public class DiagramWindow extends CanvasWindow {

    private Controller controller = new Controller();
    
    private String label = "";

    public DiagramWindow(String title) {
        super(title);
    }

    @Override
    protected void paint(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        
        g.drawString(controller.getDiagramType().toString() + " DIAGRAM", 10, 10);
        
        controller.paintScreen(g2);
        
        if (label != null) {
        	g.drawString(label, 50, 50);
        }
    }    

	@Override
    protected void handleMouseEvent(int id, int x, int y, int clickCount) {
        if (id == MouseEvent.MOUSE_CLICKED && clickCount == 1) {
            if (controller.checkCoordinate(x, y) == null && controller.selectedParty != null) {
                controller.unselectComponent();
            }
        }
        if (id == MouseEvent.MOUSE_PRESSED) {
            controller.checkAndSelect(x, y);
        }
        
        if (id == MouseEvent.MOUSE_CLICKED && clickCount == 2) {      
        	// dubbel klik op label of lifeline mag geen effect hebben   
            Party party = controller.checkCoordinate(x, y);
            
            if (party == null) {
                controller.addComponent(x, y);
            } else {
            	if (controller.getDiagramType() == DiagramType.COMMUNICATION) {
            		controller.makeNewParty(party, (int)party.getXCom(), (int)party.getYCom());
            		
            	} else if (controller.getDiagramType() == DiagramType.SEQUENCE){
            		controller.makeNewParty(party, (int)party.getXSeq(), (int)party.getYSeq());
            	}                
            }
        }

        if (id == MouseEvent.MOUSE_DRAGGED) {
            if (controller.selectedParty != null && controller.selectedParty instanceof Party) {
                controller.moveComponent((Party) controller.selectedParty, x, y);
            }
        }
        
        repaint();
    }   
    
    // Keyboard keys
    @Override
    protected void handleKeyEvent(int id, int keyCode, char keyChar) {
        switch (keyCode) {
//            case KeyEvent.VK_I:
//                controller.addMessage(new InvocationMessage("Hello", controller.getParties().get(0), controller.getParties().get(1)));
//                repaint();
//                break;
            case KeyEvent.VK_TAB:
                controller.switchDiagram();
                break;
            case KeyEvent.VK_DELETE:
                controller.deleteSelectedComponent();
                break;
        }
        
        // Enkel letters (hoofdletters & kleineletters, de 'i' toets is nog verbonden aan de messages
        if (keyCode >= 65 && keyCode <= 90) {
        	this.label += keyChar;
        }
        
        repaint();
    }
    

    
    
    
}
