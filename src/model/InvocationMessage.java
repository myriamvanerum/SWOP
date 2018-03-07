package model;

/*
 * An Invocation Message class
 * 
 * @author SWOP groep 03
 */
public class InvocationMessage extends Message {

    public InvocationMessage(String label, Party sender, Party receiver) {
        super(label, sender, receiver);
    }

    
//    @Override
//    public void paintComponentCom(Graphics2D g) {
//    	draw(g, getSender().getXCom(), getSender().getYCom(), getReceiver().getXCom(), getReceiver().getYCom());
//    }
//    
//    @Override
//    public void paintComponentSeq(Graphics2D g) {
//    	draw(g, getSender().getXSeq(), getSender().getYSeq(), getReceiver().getXSeq(), getReceiver().getYSeq());
//    }
//    
//    private void draw(Graphics2D g, double xSender, double ySender, double xReceiver, double yReceiver) {
//    	if(focused()){
//            g.setPaint(getFocusColor());
//        }else{
//            g.setPaint(getDefaultColor());
//        }
//
//        Stroke full = new BasicStroke(getLineWidth());
//        g.setStroke(full);
//        g.drawLine((int) xSender,(int) ySender,(int) xReceiver,(int) yReceiver);
//    }
//
//    private int getLineWidth(){
//        if (focused()){
//            return 8;
//        }
//        return 5;
//    }
}
