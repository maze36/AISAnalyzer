package model.ais;

import java.util.ArrayList;

/**
 * Contains all AIS-messages.
 * 
 * @author msteidel
 *
 */
public class AISMessageContainer {

	private ArrayList<AISMessage> aisMessages;

	public AISMessageContainer() {
		this.aisMessages = new ArrayList<AISMessage>();
	}

	public ArrayList<AISMessage> getAisMessages() {
		return aisMessages;
	}

	public void setAisMessages(ArrayList<AISMessage> aisMessages) {
		this.aisMessages = aisMessages;
	}

}
