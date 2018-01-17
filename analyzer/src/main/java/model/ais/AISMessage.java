package model.ais;

import java.util.Date;

/**
 * The representation of an AIS-Message.
 * 
 * @author msteidel
 *
 */
public class AISMessage {

	private Integer mmsi;

	private double cog;

	private double sog;

	private double lat;

	private double lon;

	private Date timestamp;

	private double heading;

	private double rot;

	private String destination;

	public AISMessage() {

	}

	public AISMessage(Integer mmsi, double cog, double sog, double lat, double lon, Date timestamp, double rot,
			double heading) {
		this.mmsi = mmsi;
		this.cog = cog;
		this.sog = sog;
		this.lat = lat;
		this.lon = lon;
		this.timestamp = timestamp;
	}

	public Integer getMmsi() {
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

	public void setMmsi(Integer mmsi) {
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

	public double getHeading() {
		return heading;
	}

	public void setHeading(double heading) {
		this.heading = heading;
	}

	public double getRot() {
		return rot;
	}

	public void setRot(double rot) {
		this.rot = rot;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

}
