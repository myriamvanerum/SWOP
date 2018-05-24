package domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import domain.message.InvocationMessage;
import domain.message.MessageSequence;
import domain.message.ResultMessage;
import domain.party.Actor;
import domain.party.Object;
import domain.party.Party;

/**
 * @author groep 03
 *
 */
class InteractionTest {
	Interaction interaction = new Interaction();
	Party actor = new Actor("actor");
	Party object = new Object("object");
	Party object2 = new Object("object");
	ArrayList<Party> parties = new ArrayList<>();
	
	MessageSequence messageSequence = new MessageSequence();

	InvocationMessage invocation = new InvocationMessage("message", actor, object);	
	ResultMessage result = new ResultMessage("result", object, actor);
	InvocationMessage invocation2 = new InvocationMessage("message2", actor, object);	
	ResultMessage result2 = new ResultMessage("result2", object, actor);
	InvocationMessage invocation3 = new InvocationMessage("message3", object, object2);	
	ResultMessage result3 = new ResultMessage("result2", object2, object); 
	
	public void setup() {
		invocation.setCompanion(result);
		result.setCompanion(invocation);
		
		invocation2.setCompanion(result2);
		result2.setCompanion(invocation2);
		
		invocation3.setCompanion(result3);
		result3.setCompanion(invocation3);
		
		messageSequence.addMessage(invocation, null);
		messageSequence.addMessage(invocation2, result);
		messageSequence.addMessage(invocation3, invocation2);
		
		parties.add(actor);
		parties.add(object);
		parties.add(object2);
	}
	
	@Test
	void InteractionTestBasic() {
		setup();
		
		interaction.setParties(parties);
		assertEquals(parties, interaction.getParties());
		
		interaction.setMessageSequence(messageSequence);
		assertEquals(messageSequence, interaction.getMessageSequence());
	}
	
	@Test
	void InteractionTestAddParty() {
		setup();
		
		interaction.addParty();
		assertEquals(1, interaction.getParties().size());
	}
	
	@Test
	void InteractionTestAddMessage() {
		setup();
		
		interaction.setParties(parties);
		interaction.setMessageSequence(new MessageSequence());
		
		interaction.addMessage(actor, object, null);
		assertEquals(2, interaction.getMessageSequence().getMessages().size());
	}
	
	@Test
	void InteractionTestAddMessageFail() {
		setup();
		
		interaction.setParties(parties);
		interaction.setMessageSequence(new MessageSequence());
		
		interaction.addMessage(actor, object, null);
		assertEquals(2, interaction.getMessageSequence().getMessages().size());
		interaction.addMessage(object, object2, null);
		assertNotEquals(4, interaction.getMessageSequence().getMessages().size());
	}
	
	@Test
	void InteractionTestChangePartyType() {
		setup();
		
		interaction.setParties(parties);
		interaction.changePartyType(actor);
		assertTrue(interaction.getParties().get(0) instanceof Object);
	}
	
	@Test
	void InteractionTestChangePartyTypeNull() {
		setup();
		
		try {
			interaction.changePartyType(null);
	        assert false;
	    } catch (NullPointerException e) {
	        assert true;
	    }
	}
	
	@Test
	void InteractionTestRemoveParty() {
		setup();
		
		interaction.setParties(parties);
		interaction.setMessageSequence(messageSequence);
		
		interaction.removeComponent(actor);
		assertFalse(interaction.getParties().contains(actor));
		assertTrue(interaction.getMessageSequence().getMessages().isEmpty());
	}
	
	@Test
	void InteractionTestRemoveMessage() {
		setup();
		
		interaction.setParties(parties);
		interaction.setMessageSequence(messageSequence);
		
		interaction.removeComponent(invocation2);
		assertTrue(interaction.getMessageSequence().getMessages().contains(invocation));
		assertTrue(interaction.getMessageSequence().getMessages().contains(result));
		assertFalse(interaction.getMessageSequence().getMessages().contains(invocation2));
		assertFalse(interaction.getMessageSequence().getMessages().contains(result2));
		assertFalse(interaction.getMessageSequence().getMessages().contains(invocation3));
		assertFalse(interaction.getMessageSequence().getMessages().contains(result3));
	}
}
