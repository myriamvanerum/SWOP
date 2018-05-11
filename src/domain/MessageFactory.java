package domain;

import domain.message.InvocationMessage;
import domain.message.ResultMessage;
import domain.party.Party;

public class MessageFactory {
	public InvocationMessage createMessage(Party sender, Party receiver) {
		InvocationMessage invocationMessage = new InvocationMessage("|", sender, receiver);
		ResultMessage resultMessage = new ResultMessage("", receiver, sender);
		invocationMessage.setCompanion(resultMessage);
		resultMessage.setCompanion(invocationMessage);
		
		return invocationMessage;
	}
}
