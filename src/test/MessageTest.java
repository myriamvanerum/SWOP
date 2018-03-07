/**
 * 
 */
package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Actor;
import model.InvocationMessage;
import model.Label;
import model.Message;
import model.Party;
import model.ResultMessage;

/**
 * @author groep 03
 *
 */
class MessageTest {

	Label label = new Label();
	Label label2 = new Label();
	Party sender = new Actor(1,2, label);
	Party receiver = new Actor(3,4, label);
	Message invmessage = new InvocationMessage(label,sender,receiver);
	Message resmessage = new ResultMessage(label,sender,receiver);
	
	@Test
	void test() {
		
	}
	
	@Test
	void testInvocationMessage() {
		Label l = invmessage.getLabel();
		
	}
	
	//

}
