package domain;

/*
 * An Invocation Message class
 * 
 * @author SWOP groep 03
 */
public class InvocationMessage extends Message {

    public InvocationMessage(String label, Party sender, Party receiver) {
        super(label, sender, receiver);
    }
}