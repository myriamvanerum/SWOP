package view.windows;

import java.util.ArrayList;

import view.ViewInteraction;
import view.components.ViewLabel;
import view.components.ViewMessage;
import view.formelements.TextBox;
import view.formelements.WindowControl;
import view.labelstate.LabelState;

public class ResultBox extends DialogBox {
	//private Message message;
	private ViewMessage viewMessage;
	//private ViewLabel messageLabel;
		
	public ResultBox(ViewInteraction viewInteraction, ViewMessage viewMessage, int x, int y) {
		super(viewInteraction, x, y, 250, 60, new Titlebar(x,y,250));
		this.viewMessage = viewMessage;
		//ViewLabel new ViewLabel(message.getLabel());
		setTitle("Result message");
		this.controls = new ArrayList<WindowControl>();
		controls.add(new TextBox("Label: ", getX()+10, getY()+40, viewMessage.getViewLabel()));
		this.currentControlIndex = 0;
		getCurrentControl().changeLabelState(this);
		/*setLabelState(new LabelState(this, messageLabel));
		setCurrentViewLabel(messageLabel);*/
		setViewInteraction(viewInteraction);
	}
	
	public ViewMessage getViewMessage() {
		return viewMessage;
	}

	public void setMessage(ViewMessage viewMessage) {
		this.viewMessage = viewMessage;
	}
	
	@Override
	public void setDialogBoxState(ViewLabel viewLabel) {
		if (viewLabel == null)
			throw new IllegalArgumentException();
			
		setLabelState(new LabelState(this, viewLabel));
	}
	
	@Override
	public void confirmLabel() {
		if (!actionAllowed()) {
			getLabelState().setViewLabel(getCurrentViewLabel());
			getLabelState().confirmLabel(getViewMessage().getMessage());
		}
	}
}
