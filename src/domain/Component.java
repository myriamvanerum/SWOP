package domain;

import model.message.MessageSequence;

/**
 * Component Class. Parent class for Parties and Messages
 * @author groep 03
 */
public class Component {

    public String label;
    
    public void remove(Interaction interaction) {}
    
    public void removeDependents(MessageSequence messageSequence) {}
        
    /* GETTERS AND SETTERS */

    public String getLabel(){
        return label;
    }

    public void setLabel(String label){
        this.label = label;
    }
    
    /**
	 * Edit the Label of a Component
	 * @param component
	 * @param label
	 */
	public void editLabel(Interaction interaction, String label) {}
}
