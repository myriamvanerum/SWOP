package view.windows;

import java.util.ArrayList;

import view.ViewInteraction;
import view.components.ViewMessage;
import view.controls.TextBox;
import view.controls.WindowControl;
import view.labelstate.EditLabelState;

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
		
	@Override
	public void confirmLabel() {
		if (!actionAllowed()) {
			getLabelState().setViewLabel(getCurrentViewLabel());
			getLabelState().confirmLabel();
		}
	}
}
