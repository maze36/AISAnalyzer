package model.ais;

import java.util.Date;

/**
 * The representation of an AIS-Message.
 * 
 * @author msteidel
 *
 */
public class AISMessage {

	private long mmsi;

	private double cog;

	private double sog;

	private double lat;

	private double lon;

	private Date timestamp;

	private AISMessageType messageType;

	public AISMessage() {

	}

	public AISMessage(long mmsi, double cog, double sog, double lat, double lon, Date timestamp,
			AISMessageType messageType) {
		this.mmsi = mmsi;
		this.cog = cog;
		this.sog = sog;
		this.lat = lat;
		this.lon = lon;
		this.timestamp = timestamp;
		this.messageType = messageType;
	}

	public long getMmsi() {
		return mmsi;
	}

	public double getCog() {
		return cog;
	}

	public double getSog() {
		return sog;
	}

	public double getLat() {
		return lat;
	}

	public double getLon() {
		return lon;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setMmsi(long mmsi) {
		this.mmsi = mmsi;
	}

	public void setCog(double cog) {
		this.cog = cog;
	}

	public void setSog(double sog) {
		this.sog = sog;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public AISMessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(AISMessageType messageType) {
		this.messageType = messageType;
	}

}
