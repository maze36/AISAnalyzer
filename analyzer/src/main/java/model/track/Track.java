package model.track;

import java.util.ArrayList;
import java.util.Date;

import model.ais.AISMessage;

public class Track {

	private Date startDate;

	private Date endDate;

	private ArrayList<AISMessage> aisMessages;

	public Track(AISMessage aisMessage) {
		this.aisMessages = new ArrayList<AISMessage>();
		this.aisMessages.add(aisMessage);
		this.endDate = aisMessage.getTimestamp();
		this.startDate = aisMessage.getTimestamp();
	}

	public boolean addMessage(AISMessage aisMessage) {
		return this.aisMessages.add(aisMessage);
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public ArrayList<AISMessage> getAisMessages() {
		return aisMessages;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setAisMessages(ArrayList<AISMessage> aisMessages) {
		this.aisMessages = aisMessages;
	}

}
