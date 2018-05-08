package view.windows;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import view.components.ViewResultMessage;
import view.formelements.TextBox;
import view.formelements.WindowControl;

public class ResultBox extends DialogBox {
	private ViewResultMessage message;;
	private String label;
	
	public ResultBox() {
		this.message = null;
		this.label = "Label: ";		
		this.controls = new ArrayList<WindowControl>();
		controls.add(new TextBox(""));
		this.currentControl = 0;
	}	
	
	public ResultBox(ViewResultMessage message) {
		this.message = message;
		this.label = "Label: ";	
		this.controls = new ArrayList<WindowControl>();
		controls.add(new TextBox("", message.getViewLabel()));
		this.currentControl = 0;
	}
	
	protected void draw(Graphics2D g, Point2D position) {
		if (position.getX() < 0 || position.getY() < 0)
			throw new IllegalArgumentException();		
		
		drawControls(g);
	}

	public ViewResultMessage getMessage() {
		return message;
	}

	public void setMessage(ViewResultMessage message) {
		this.message = message;
	}
	
	public String getLabel() {
		return label;
	}
}
