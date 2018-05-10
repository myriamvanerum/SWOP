/**
 * A test to see if the message has the right input.
 */
package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.message.InvocationMessage;
import model.message.ResultMessage;
import model.party.Actor;
import model.party.Object;
import model.party.Party;

/**
 * @author groep 03
 *
 */
class MessageTest { 

	@Test
	void testMessageBasic() {
		Party actor = new Actor("test");
		Party object = new Object("test");
		
		ResultMessage result = new ResultMessage("result", object, actor);		
		
		assertEquals("result", result.getLabel());
		
		InvocationMessage invocation = new InvocationMessage("message", actor, object, result);		
			
		assertEquals(invocation, actor.getSendingMessage());
		assertEquals(actor, invocation.getSender());
		assertEquals(object, invocation.getReceiver());
		
		
	}
	

}
