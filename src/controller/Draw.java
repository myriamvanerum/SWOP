package controller;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import model.Actor;
import model.Object;
import model.Party;

public interface Draw {
	default void drawActor(Graphics2D g, double x, double y, String label, int size, int totalHeight) {
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
    
	default void drawObject(Graphics2D g, double x, double y, String label, int height, int width) {
    	Rectangle r = new Rectangle((int)x,(int)y,width,height);
        g.drawString(label,(int)(x+ ((width/2) - label.length() * 2)),(int)(y) + height/2);
        g.draw(r);
    }
    
    default void drawLifeline(Graphics g, int x, int startY, int endY) {
    	Graphics2D g2d = (Graphics2D) g.create();
        Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
        g2d.setStroke(dashed);
        g2d.draw(new Line2D.Double(x, startY, x, endY));
    }
    
    default void drawComponent(Graphics g, Party component) {
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
    
    default void setColor(Party component, Graphics2D g) {
    	if(component.focused()){
            g.setPaint(new Color(70, 170, 220));
        } else {
            g.setPaint(new Color(0, 0, 0));
        }
    }
}
