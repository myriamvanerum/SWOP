package view.windows;

import java.util.ArrayList;

import domain.message.Message;
import view.components.ViewLabel;
import view.formelements.TextBox;
import view.formelements.WindowControl;

public class ResultBox extends DialogBox {
	private Message message;
	private ViewLabel viewLabel;
		
	public ResultBox(Message message, int x, int y) {
		super(x, y, 250, 50, new Titlebar(x,y,250));
		this.message = message;
		this.viewLabel = new ViewLabel(message.getLabel());
		this.controls = new ArrayList<WindowControl>();
		controls.add(new TextBox("Label: ", getX()+10, getY()+20, viewLabel));
		this.currentControl = 0;
		setTitle("Result message");
	}
	
	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}
}
