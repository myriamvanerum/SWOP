/**
 * A test to see if the message has the right input.
 */
package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author groep 03
 *
 */
class MessageTest { 

	@Test
	void testMessageBasic() {
		Party actor = new Actor("test");
		Party object = new Object("test");
		
		Message invocation = new InvocationMessage("message", actor, object);		
			
		assertEquals(invocation, actor.getSendingMessage());
		assertEquals(actor, invocation.getSender());
		assertEquals(object, invocation.getReceiver());
		
		Message result = new ResultMessage("result", object, actor);		
			
		assertEquals("result", result.getLabel());
	}
	

}
