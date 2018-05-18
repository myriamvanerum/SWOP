package view.windows;

import java.util.ArrayList;

import view.ViewInteraction;
import view.components.ViewMessage;
import view.controls.TextBox;
import view.controls.WindowControl;
import view.labelstate.EditLabelState;

/**
 * ResultBox class.
 * Represents a dialogbox used for editing the characteristics of a result message, subclass of the DialogBox class
 * @author groep 03
 *
 */
public class ResultBox extends DialogBox {
	private ViewMessage viewMessage;
		
	public ResultBox(ViewInteraction viewInteraction, ViewMessage viewMessage, int x, int y) {
		super(viewInteraction, x, y, 250, 60, new Titlebar(x,y,250));
		this.viewMessage = viewMessage;
		setTitle("Result message");
		this.controls = new ArrayList<WindowControl>();
		controls.add(new TextBox("Label: ", getX()+10, getY()+40, viewMessage.getViewLabel(), new EditLabelState(this, null)));
		this.currentControlIndex = 0;
		getCurrentControl().setActive(true);
		getCurrentControl().currentControl(this);
		setViewInteraction(viewInteraction);
		setSelectedComponent(viewMessage);
	}
	
	public ViewMessage getViewMessage() {
		return viewMessage;
	}

	public void setMessage(ViewMessage viewMessage) {
		this.viewMessage = viewMessage;
	}
		
	/**
	 * Confirm that the entered label should be set as new label
	 */
	@Override
	public void confirmLabel() {
		if (!actionAllowed()) {
			getLabelState().setViewLabel(getCurrentViewLabel());
			getLabelState().confirmLabel();
		}
	}
}
