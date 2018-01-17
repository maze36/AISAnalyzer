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

	/**
	 * Checks whether the given {@link Date} belongs to the current track or
	 * not. If the time difference between the last message of the {@link Track}
	 * and the given {@link Date} is smaller than 15000 ms, true is returned.
	 * 
	 * @param timestamp
	 *            The {@link Date} to check.
	 * @return
	 */
	public boolean messageBelongsToTrack(Date timestamp) {

		long milliCurrentEndDate = this.endDate.getTime();
		long milliNewMessage = timestamp.getTime();
		long timeDiff = milliNewMessage - milliCurrentEndDate;
		if (timeDiff <= 150000) {
			return true;
		} else {
			return false;
		}
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
