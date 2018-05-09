package view.windows;

import java.util.ArrayList;

import view.components.ViewResultMessage;
import view.formelements.TextBox;
import view.formelements.WindowControl;

public class ResultBox extends DialogBox {
	private ViewResultMessage message;
		
	public ResultBox(ViewResultMessage message, int x, int y) {
		super(x, y, 250, 50, new Titlebar(x,y,250));
		this.message = message;
		this.controls = new ArrayList<WindowControl>();
		controls.add(new TextBox("Label: ", getX()+10, getY()+20, message.getViewLabel()));
		this.currentControl = 0;
	}
	
	public ViewResultMessage getMessage() {
		return message;
	}

	public void setMessage(ViewResultMessage message) {
		this.message = message;
	}
}
