package view.windows;

import java.util.ArrayList;

import domain.message.Message;
import view.ViewInteraction;
import view.components.ViewLabel;
import view.formelements.TextBox;
import view.formelements.WindowControl;
import view.labelstate.LabelState;

public class ResultBox extends DialogBox {
	private Message message;
	private ViewLabel messageLabel;
		
	public ResultBox(ViewInteraction viewInteraction, Message message, int x, int y) {
		super(viewInteraction, x, y, 250, 60, new Titlebar(x,y,250));
		this.message = message;
		this.messageLabel = new ViewLabel(message.getLabel());
		setTitle("Result message");
		this.controls = new ArrayList<WindowControl>();
		controls.add(new TextBox("Label: ", getX()+10, getY()+40, messageLabel));
		this.currentControl = 0;
		setLabelState(new LabelState(this, messageLabel));
		setCurrentViewLabel(messageLabel);
		setViewInteraction(viewInteraction);
	}
	
	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}
	
	@Override
	public void confirmLabel() {
		if (!actionAllowed()) {
			getLabelState().setViewLabel(getCurrentViewLabel());
			getLabelState().confirmLabel(getMessage());
		}
	}
}
