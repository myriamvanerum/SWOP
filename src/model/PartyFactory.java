package model;
/**
 * Factory class for creating Parties
 * @author groep 03
 */
public class PartyFactory {
	/**
	 * Creates a new party
	 * @param partyType
	 * 			string contains a party type, depending on what kind of party needs to be created
	 * @return a new party object (actor or object)
	 */
	public Party createParty(String partyType) {
		if (partyType.equalsIgnoreCase("ACTOR")) {
			return new Actor("|");
		} else if (partyType.equalsIgnoreCase("Object")){
			return new Object("|");
		} 
		return null;
	}
}