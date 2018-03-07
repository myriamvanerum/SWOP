package model;

/*
 * A result message class
 * 
 * @author SWOP groep 03
 */
public class ResultMessage extends Message {

    public ResultMessage(String label, Party sender, Party receiver) {
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
//            g.setPaint(super.getFocusColor());
//        }else{
//            g.setPaint(super.getDefaultColor());
//        }
//
//        Stroke dashed = new BasicStroke(getLineWidth(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
//        g.setStroke(dashed);
//        g.drawLine((int) xSender,(int) ySender,(int) xReceiver,(int) yReceiver);    }
//
//
//    private int getLineWidth(){
//        if (focused()){
//            return 5;
//        }
//        return 3;
//    }
}
