package view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.geom.Point2D;

import org.junit.Test;

import domain.message.InvocationMessage;
import domain.message.ResultMessage;
import view.components.ViewInvocationMessage;
import view.components.ViewResultMessage;
import view.controls.TextBox;
import view.labelstate.EditInvocationMessageArgumentState;
import view.labelstate.EditLabelState;
import view.windows.InvocationBox;
import view.windows.ResultBox;

public class EditLabelStateTest {
	
	@Test
	public void EditLabelStateSuccessTest() {
		ViewInteraction interaction = new ViewInteraction();
		ResultMessage result = new ResultMessage("test", null, null);
		ViewResultMessage viewResultMessage = new ViewResultMessage(result, new Point2D.Double(30,30), new Point2D.Double(10,10), null, null);
		ResultBox resultbox = new ResultBox(interaction, viewResultMessage, 10, 10);
		EditLabelState state = new EditLabelState(resultbox, viewResultMessage.getViewLabel());
		
		state.addCharacter(97, '1');
		assertEquals("test1|", viewResultMessage.getViewLabel().getOutput());	
		state.addCharacter(97, '2');
		assertEquals("test12|", viewResultMessage.getViewLabel().getOutput());
		state.addCharacter(97, '3');	
		assertEquals("test123|", viewResultMessage.getViewLabel().getOutput());	
		state.removeCharacter();
		assertEquals("test12|", viewResultMessage.getViewLabel().getOutput());	
		state.confirmLabel();
		assertEquals("test12", viewResultMessage.getViewLabel().getOutput());
		assertEquals("test12", viewResultMessage.getMessage().getLabel());
	}
	
	@Test
	public void EditInvocationMessageArgumentStateSuccessTest() {
		ViewInteraction viewInteraction = new ViewInteraction();
		InvocationMessage invocationMessage = new InvocationMessage("method(arg)", null, null);
		invocationMessage.setArguments(invocationMessage.getArgumentsFromLabel(invocationMessage.getLabel()));
		invocationMessage.setMethod(invocationMessage.getMethodFromLabel(invocationMessage.getLabel()));
		ViewInvocationMessage viewInvocationMessage = new ViewInvocationMessage(invocationMessage, new Point2D.Double(30,30), new Point2D.Double(10,10), null, null);
		InvocationBox invocationBox = new InvocationBox(viewInteraction, viewInvocationMessage, 5, 5);
		invocationBox.setCurrentControlIndex(1);
		assertEquals(1, invocationMessage.getArguments().size());
		TextBox textbox = (TextBox)invocationBox.getCurrentControl();
		textbox.getViewLabel().setOutput("new");
		EditInvocationMessageArgumentState state = new EditInvocationMessageArgumentState(invocationBox, textbox.getViewLabel(), invocationMessage);
		state.enterLabel();
		
		assertEquals("method(arg, new)", invocationMessage.getLabel());
		assertEquals(2, invocationMessage.getArguments().size());
	}
}
