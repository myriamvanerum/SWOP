package model;

import java.awt.geom.Point2D;

import model.DiagramComponent;

/*
 * A Party class
 * 
 * @author SWOP groep 03
 */
public abstract class Party extends DiagramComponent {

    private Point2D posInCommDiagram;
    private Integer posInSeqDiagram;
    private Message sendingMessage;

    /**
     * Party constructor
     * @param x
	 * 		The x coordinate of the party
	 * @param y
	 * 		The y coordinate of the party
	 * @param label
	 * 		The party's label
     */
    public Party(int x, int y, Label label) {
        posInCommDiagram = new Point2D.Double(x,y);
        posInSeqDiagram = x;
        this.label = label;
    }

    /* GETTERS AND SETTERS */
    
    public double getXCom() {
        return posInCommDiagram.getX();
    }
    
    public double getXSeq() {
        return posInSeqDiagram;
    }

    public void setXCom(int x) {
        this.posInCommDiagram.setLocation(x,posInCommDiagram.getY());
    }
    
    public void setXSeq(int x) {
        this.posInSeqDiagram = x;
    }

    public double getYCom() {
        return posInCommDiagram.getY();
    }
    
    public double getYSeq() {
        return 50;
    }

    public void setY(int y) {
        this.posInCommDiagram.setLocation(posInCommDiagram.getX(),y);
    }

    public Message getSendingMessage() {
        return sendingMessage;
    }

    public void setSendingMessage(Message sendingMessage) {
        this.sendingMessage = sendingMessage;
    }
    
    public String getLabelText() {
    	return this.label.getText();
    }
}