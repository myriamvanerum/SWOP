package view.controls;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import view.components.ViewLabel;
import view.labelstate.EditLabelState;
import view.windows.SubWindow;

public class TextBox extends WindowControl{
	private String description;
	private ViewLabel label;
	private EditLabelState state;

	public TextBox(String description, int x, int y, EditLabelState state) {
		super();
		this.description = description;
		this.label = new ViewLabel("");
		this.state = state;
		setX(x);
		setY(y);
		setHeight(20);
		setWidth(150);
	}
	
	public TextBox(String description, int x, int y, ViewLabel label, EditLabelState state) {
		super();
		this.description = description;
		this.label = label;
		this.state = state;
		setX(x);
		setY(y);
		setHeight(20);
		setWidth(150);
	}
	
	public EditLabelState getState() {
		return state;
	}

	public ViewLabel getLabel() {
		return label;
	}

	public void setLabel(ViewLabel label) {
		this.label = label;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawString(getDescription(), getX() , getY() + 4);		
		
		int boxX = getX() + g.getFontMetrics().stringWidth(getDescription()) + 5; 
		int boxY = getY() - getHeight()/2;
				
		Rectangle box = new Rectangle(boxX, boxY, getWidth(), getHeight());
		
		if (isActive())
			g.setColor(new Color(255, 145, 70));

		g.draw(box);
		g.setColor(Color.BLACK);
		
		if (getLabel() != null) {
			String value = getLabel().getOutput();
			g.drawString(value, boxX + 10, boxY + 15);
		}
	}
	
	@Override
	public ViewLabel getViewLabel() {
		return getLabel();
	}
	
	@Override
	public void currentControl(SubWindow subwindow) {
		if (isActive()) {
			String output = getViewLabel().getOutput();
			getViewLabel().setOutput(output + "|");
			subwindow.setLabelState(getState());
		} else subwindow.changeLabelState("SHOW");
	}
}
