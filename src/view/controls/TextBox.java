package view.controls;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import view.components.ViewLabel;
import view.labelstate.EditLabelState;
import view.windows.SubWindow;

/**
 * TextBox class.
 * Represents a textbox control used to get user input
 * @author groep 03
 */
public class TextBox extends WindowControl {
	private String description;
	private ViewLabel label;
	private EditLabelState state;

	/**
	 * TextBox constructor
	 * @param description
	 * 			Description for the textbox
	 * @param x
	 * 			The x coordinate of the textbox
	 * @param y
	 * 			The y coordinate of the textbox
	 * @param state
	 * 		 	LabelState of the textbox
	 */
	public TextBox(String description, int x, int y, EditLabelState state) {
		super();
		setParameters(description, x, y, state);
		this.label = new ViewLabel("");
	}
	/**
	 * TextBox constructor
	 * @param description
	 * 			Description for the textbox
	 * @param x
	 * 			The x coordinate of the textbox
	 * @param y
	 * 			The y coordinate of the textbox
	 * @param label
	 * 			viewlabel that contains the value of the textbox
	 * @param state
	 * 		 	LabelState of the textbox
	 */
	public TextBox(String description, int x, int y, ViewLabel label, EditLabelState state) {
		super();
		setParameters(description, x, y, state);
		this.label = label;
	}
	
	/* Getters & Setters */
	public void setParameters(String description, int x, int y, EditLabelState state) {
		this.description = description;
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
	public ViewLabel getViewLabel() {
		return getLabel();
	}
	
	public String getValue() {
		return getViewLabel().getOutput();
	}

	/**
	 * Draw the window control
	 * @param gOrig
	 * 			Graphics class
	 */
	@Override
	public void draw(Graphics2D gOrig) {
		Graphics2D g =  (Graphics2D) gOrig.create();
		g.drawString(getDescription(), getX() , getY() + 4);		
		
		int boxX = getX() + g.getFontMetrics().stringWidth(getDescription()) + 5; 
		int boxY = getY() - getHeight()/2;
				
		Rectangle box = new Rectangle(boxX, boxY, getWidth(), getHeight());
		
		if (isActive())
			g.setColor(new Color(255, 145, 70));

		g.draw(box);
		g.setColor(Color.BLACK);
		g.setClip(box);
				
		if (getLabel() != null) {
			String value = getLabel().getOutput();
			if (getLabel().getWidth() > getWidth()) {
				int counter = (getLabel().getWidth() - getWidth())/2;
				g.drawString(value.substring(counter, value.length()-1), boxX + 10, boxY + 15);
			} else {
				g.drawString(value, boxX + 10, boxY + 15);
			}
		}
		
		g.dispose();
	}
	
	/**
	 * When current control is changed, label state is set
	 * @param subwindow
	 * 			The subwindow to which the window control belongs
	 */
	@Override
	public void currentControl(SubWindow subwindow) {
		String output = getViewLabel().getOutput();
		
		if (isActive()) {
			getViewLabel().setOutput(output + "|");
			subwindow.setLabelState(getState());
		} else {
						
			if (output.indexOf("|") > -1) {
				getViewLabel().setOutput(output.substring(0, output.length()-1));
			}
				
			subwindow.changeLabelState("SHOW");
		}
	}
	
	/**
	 * Add the user input as an argument
	 * @return true if success
	 */
	public boolean add() {
		if (getValue().trim().length() <= 0) return false;
		getState().addArgument();
		return true;
	}
}
