package domain;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.geom.Point2D;

import org.junit.jupiter.api.Test;

import domain.message.Message;
import domain.party.Actor;
import domain.party.Object;
import domain.party.Party;
import view.ViewInteraction;
import view.windows.DiagramWindow;

class InteractrTest {
	
	ViewInteraction viewInteraction = new ViewInteraction();
	DiagramWindow window = new DiagramWindow(viewInteraction, 20, 20);
	Interactr interactr = new Interactr(viewInteraction);

	public void setup() {
		viewInteraction.addWindow(window);
	//	viewInteraction.setLastClickedPosition(new Point2D.Double(50,50));
	}
	
	@Test
	void InteractrTestAddParty() {
		setup();
		interactr.addParty();
		assertEquals(1, interactr.interaction.getParties().size());
	}
	
	@Test
	void InteractrTestChangePartyType() {
		setup();
		interactr.addParty();
		Party party = interactr.interaction.getParties().get(0);
		assertEquals(1, interactr.interaction.getParties().size());
		assertTrue(interactr.interaction.getParties().get(0) instanceof Object);
		interactr.changePartyType(party);
		assertEquals(1, interactr.interaction.getParties().size());
		assertTrue(interactr.interaction.getParties().get(0) instanceof Actor);
	}

	@Test
	void InteractrTestChangePartyTypeNull() {
		setup();
		
		try {
			interactr.changePartyType(null);
	        assert false;
	    } catch (NullPointerException e) {
	        assert true;
	    }
	}
	
	@Test
	void InteractrTestAddMessage() {
		setup();
		interactr.addParty();
		interactr.addParty();
		Party sender = interactr.interaction.getParties().get(0);
		Party receiver = interactr.interaction.getParties().get(1);
		interactr.addMessage(sender, receiver, null);
		assertEquals(2, interactr.interaction.getMessageSequence().getMessages().size());
	}
	
	@Test
	void InteractrTestAddMessageNull() {
		setup();
		
		try {
			interactr.addMessage(null, null, null);
	        assert false;
	    } catch (NullPointerException e) {
	        assert true;
	    }
	}
	
	@Test
	void InteractrTestRemoveParty() {
		setup();
		interactr.addParty();
		Party party = interactr.interaction.getParties().get(0);
		interactr.deleteComponent(party);
		assertEquals(0, interactr.interaction.getParties().size());
	}
	
	@Test
	void InteractrTestRemoveMessage() {
		setup();
		interactr.addParty();
		interactr.addParty();
		Party sender = interactr.interaction.getParties().get(0);
		Party receiver = interactr.interaction.getParties().get(1);
		interactr.addMessage(sender, receiver, null);
		Message message = interactr.interaction.getMessageSequence().getMessages().getFirst();
		assertEquals(2, interactr.interaction.getMessageSequence().getMessages().size());
		interactr.deleteComponent(message);
		assertEquals(0, interactr.interaction.getMessageSequence().getMessages().size());
	}
	
	@Test
	void InteractrTestRemoveNull() {
		setup();
		
		try {
			interactr.deleteComponent(null);
	        assert false;
	    } catch (NullPointerException e) {
	        assert true;
	    }
	}
	
	@Test
	void InteractrTestEditPartyLabel() {
		setup();
		interactr.addParty();
		Party party = interactr.interaction.getParties().get(0);
		interactr.editLabel(party, "newLabel:Label");
		assertEquals("newLabel:Label", interactr.interaction.getParties().get(0).getLabel());
	}
	
	@Test
	void InteractrTestEditMessageLabel() {
		setup();
		interactr.addParty();
		interactr.addParty();
		Party sender = interactr.interaction.getParties().get(0);
		Party receiver = interactr.interaction.getParties().get(1);
		interactr.addMessage(sender, receiver, null);
		Message message = interactr.interaction.getMessageSequence().getMessages().getFirst();
		interactr.editLabel(message, "newLabel()");
		assertEquals("newLabel()", interactr.interaction.getMessageSequence().getMessages().getFirst().getLabel());
		
		Message resMessage = interactr.interaction.getMessageSequence().getMessages().get(1);
		interactr.editLabel(resMessage, "re$MessageLabel123");
		assertEquals("re$MessageLabel123", interactr.interaction.getMessageSequence().getMessages().get(1).getLabel());
	}
}
