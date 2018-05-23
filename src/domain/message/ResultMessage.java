package domain.message;

import java.util.Stack;

import domain.party.Party;

/**
 * A result message class
 * @author  groep 03
 */
public class ResultMessage extends Message {	
	/**
	 * Result message constructor
	 * @param label
	 * 		The message label
	 * @param sender
	 * 		The party that sends the message
	 * @param receiver
	 * 		The party that receives the message
	 */
    public ResultMessage(String label, Party sender, Party receiver) {
        super(label, sender, receiver);
    }

	@Override
	public Object[] setMessageNumber(Stack<Integer> messageNumberStack, int count, boolean foundRes) {
		if (foundRes) {
			messageNumberStack.pop();
    		count -= 1;
    	} else
    		foundRes = true;
		
		Object temp[] = new Object[3];
        temp[0] = messageNumberStack;
        temp[1] = count;
        temp[2] = foundRes;
        return temp;
	}
}
