/**
 * A test to see if the message has the right input.
 */
package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import domain.message.InvocationMessage;
import domain.message.ResultMessage;
import domain.party.Actor;
import domain.party.Object;
import domain.party.Party;

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
			
		assertEquals(actor, invocation.getSender());
		assertEquals(object, invocation.getReceiver());
		
		
	}
	

}
