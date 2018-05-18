/**
 * A test to see if the message has the right input.
 */
package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

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
	Party actor = new Actor("test");
	Party object = new Object("test");
	InvocationMessage invocation = new InvocationMessage("message", actor, object);	
	ResultMessage result = new ResultMessage("result", object, actor);	
	
	@Test
	void testMessageBasic() {
		invocation.setCompanion(result);
		result.setCompanion(invocation);
		
		assertEquals(result, invocation.getCompanion());
		assertEquals(invocation, result.getCompanion());
		
		assertEquals("message", invocation.getLabel());
		assertEquals("result", result.getLabel());
			
		assertEquals(actor, invocation.getSender());
		assertEquals(object, invocation.getReceiver());
	}
	
	@Test
	void testMessageLabel() {
		invocation.setMessageNumber("1");
		assertEquals("1", invocation.getMessageNumber());
		
		String method = "method";
		invocation.setMethod(method);
		assertEquals(method, invocation.getMethod());
		
		ArrayList<String> arguments = new ArrayList<>();
		arguments.add("arg1");
		arguments.add("arg2");
		invocation.setArguments(arguments);
		assertEquals(arguments, invocation.getArguments());
		assertEquals("arg1, arg2", invocation.argumentsToString());
		
		String label = "methodName123(arg1_here,arg2IsThis,arg3_as_well)";
		String methodName = "methodName123";
		String arg1 = "arg1_here";
		String arg2 = "arg2IsThis";
		String arg3 = "arg3_as_well";
		ArrayList<String> args = new ArrayList<>();
		args.add(arg1);
		args.add(arg2);
		args.add(arg3);
		assertEquals(methodName, invocation.getMethodFromLabel(label));
		assertEquals(args, invocation.getArgumentsFromLabel(label));
	}
	

}
