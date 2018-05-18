package domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import domain.message.InvocationMessage;
import domain.message.MessageSequence;
import domain.message.ResultMessage;
import domain.party.Actor;
import domain.party.Object;
import domain.party.Party;

class MessageSequenceTest {
	MessageSequence messageSequence = new MessageSequence();
	Party actor = new Actor("test");
	Party object = new Object("test");
	Party object2 = new Object("test2");
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
	}
	
	@Test
	void testAddMessage() {
		setup();
		
		messageSequence.addMessage(invocation, null);
		assertTrue(messageSequence.getMessages().contains(invocation));
		assertTrue(messageSequence.getMessages().contains(result));
	}

	@Test
	void testAddMessages() {
		setup();
		
		messageSequence.addMessage(invocation, null);
		assertTrue(messageSequence.getMessages().contains(invocation));
		assertTrue(messageSequence.getMessages().contains(result));
		
		messageSequence.addMessage(invocation2, result);
		assertTrue(messageSequence.getMessages().contains(invocation2));
		assertTrue(messageSequence.getMessages().contains(result2));
		
		messageSequence.addMessage(invocation3, invocation2);
		assertTrue(messageSequence.getMessages().contains(invocation3));
		assertTrue(messageSequence.getMessages().contains(result3));
	}
	
	@Test
	void testAddMessageFail() {
		setup();
		
		messageSequence.addMessage(invocation, null);
		
		messageSequence.addMessage(invocation2, invocation);
		assertFalse(messageSequence.getMessages().contains(invocation2));
		assertFalse(messageSequence.getMessages().contains(result2));
	}
	
	@Test
	void testRemovePartyDependents() {
		setup();
		
		messageSequence.addMessage(invocation, null);
		
		messageSequence.addMessage(invocation2, result);
		
		messageSequence.removePartyDependents(actor);
		assertTrue(messageSequence.getMessages().isEmpty());
	}
	
	@Test
	void testRemoveMessageNullError() {
		setup();
		
		messageSequence.addMessage(invocation, null);
		
		try {
			messageSequence.removeMessageAndDependents(invocation2);
	        assert false;
	    } catch (IllegalArgumentException e) {
	        assert true;
	    }
	}
}
