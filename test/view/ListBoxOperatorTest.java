package view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

import domain.message.InvocationMessage;
import view.components.ViewInvocationMessage;
import view.controls.Button;
import view.controls.ListBox;
import view.controls.TextBox;
import view.controls.WindowControl;
import view.listboxcommand.ListBoxOperator;
import view.windows.InvocationBox;

public class ListBoxOperatorTest {
	ListBoxOperator listBoxOperator;
	InvocationBox invocationbox;
	InvocationMessage invocationMessage;
	ViewInteraction interaction;
	
	@Before
	public void listbox() {
		ArrayList<String> items = new ArrayList<>();
		
		items.add("test1");
		items.add("test2");
		items.add("test3");
		items.add("test4");
		
		invocationMessage = new InvocationMessage("Message", null, null);
		invocationMessage.setArguments(items);
		
		ViewInvocationMessage viewInvocationMessage = new ViewInvocationMessage(invocationMessage, new Point2D.Double(30,30), new Point2D.Double(10,10), null, null);
		invocationbox = new InvocationBox(null, viewInvocationMessage, 15, 15);
		
		interaction = new ViewInteraction();
		interaction.getSubWindows().add(invocationbox);
		interaction.setActiveWindow(invocationbox);
		
		invocationbox.setViewInteraction(interaction);
	}
	
	@Test
    public void testAddItemSuccess() {
		// Get textbox
		invocationbox.setCurrentControlIndex(1);
		TextBox textbox = (TextBox)invocationbox.getCurrentControl();
		textbox.getViewLabel().setOutput("New");
		textbox.getState().setViewLabel(textbox.getViewLabel());
		
		// get add button
		WindowControl addButton = getButton(2);
		
		// Get listbox
		ListBox listbox = getListbox();
		
		assertEquals(4, listbox.getItems().size());
		addButton.click();		
		assertEquals(5, listbox.getItems().size());
    }
	
	@Test
    public void testDeleteItemSuccess() {
		// get delete button
		WindowControl deleteButton = getButton(6);	
		
		// Get listbox
		ListBox listbox = getListbox();

		listbox.setSelectedItem(2);
		listbox.setAvailabilityButtons();
				
		assertEquals(4, listbox.getItems().size());
		deleteButton.click();
		assertEquals(3, listbox.getItems().size());
    }
	
	@Test
    public void testMoveItemUpSuccess() {
		// get item up button
		WindowControl button = getButton(4);		
				
		// Get listbox
		ListBox listbox = getListbox();

		listbox.setSelectedItem(2);
		listbox.setAvailabilityButtons();
		
		assertEquals("test3", listbox.getItems().get(2));
		assertEquals("test2", listbox.getItems().get(1));
		button.click();
		assertEquals("test3", listbox.getItems().get(1));
		assertEquals("test2", listbox.getItems().get(2));
    }
	
	@Test
    public void testMoveItemDownSuccess() {
		// get item up button	
		WindowControl button = getButton(5);			
				
		// Get listbox
		ListBox listbox = getListbox();
		
		listbox.setSelectedItem(2);
		listbox.setAvailabilityButtons();

		assertEquals("test3", listbox.getItems().get(2));
		assertEquals("test4", listbox.getItems().get(3));
		button.click();
		assertEquals("test3", listbox.getItems().get(3));
		assertEquals("test4", listbox.getItems().get(2));
    }

	@Test
    public void testScrollDownSuccess() {
		ListBox listbox = getListbox();
		listbox.setSelectedItem(0);
		invocationbox.scrollDown();
		assertEquals(1, listbox.getSelectedItem());
		invocationbox.scrollDown();
		assertEquals(2, listbox.getSelectedItem());
		invocationbox.scrollDown();
		assertEquals(3, listbox.getSelectedItem());
    }
	
	@Test
    public void testScrollUpSuccess() {
		ListBox listbox = getListbox();
		listbox.setSelectedItem(3);
		invocationbox.scrollUp();
		assertEquals(2, listbox.getSelectedItem());
		invocationbox.scrollUp();
		assertEquals(1, listbox.getSelectedItem());
		invocationbox.scrollUp();
		assertEquals(0, listbox.getSelectedItem());
    }

	private Button getButton(int index) {
		invocationbox.setCurrentControlIndex(index);
		return (Button) invocationbox.getCurrentControl();
	}
	
	private ListBox getListbox() {
		invocationbox.setCurrentControlIndex(3);
		return  (ListBox)invocationbox.getCurrentControl();
	}	
}
