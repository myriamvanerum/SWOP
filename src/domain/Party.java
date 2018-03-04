package domain;

import java.awt.geom.Point2D;

import domain.Component;

/*
 * A Party class
 * 
 * @author SWOP groep 03
 */
public abstract class Party extends Component {

    private Point2D posInCommDiagram;
    private Integer posInSeqDiagram;
    private ComponentType type;
    private String instanceName;
    private String className;
    private String label;
    private Message sendingMessage;

    public Party(int x, int y, ComponentType type, String label) {
        posInCommDiagram = new Point2D.Double(x,y);
        posInSeqDiagram = x;
        this.type = type;
        this.label = label;
    }

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

    public String getLabel(){
        return label;
    }

    public void setLabel(String label){
        this.label = label;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public ComponentType getType() {
        return type;
    }

    public void setType(ComponentType type) {
        this.type = type;
    }

    public Message getSendingMessage() {
        return sendingMessage;
    }

    public void setSendingMessage(Message sendingMessage) {
        this.sendingMessage = sendingMessage;
    }
}