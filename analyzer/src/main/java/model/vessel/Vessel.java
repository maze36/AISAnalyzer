package model.vessel;

import java.util.ArrayList;
import java.util.Date;

import model.track.Track;

public class Vessel {

	private double length;

	private double width;

	private Integer mmsi;

	private ShipType shipType;

	private ArrayList<Track> tracks;

	private String name;

	private Integer imo;

	private Date firstDate;

	private Date endDate;

	public Vessel() {
		this.tracks = new ArrayList<Track>();
	}

	public Vessel(Integer mmsi, double length, double width, ShipType shipType, String name, Integer imo,
			Date firstDate, Date endDate) {
		this.mmsi = mmsi;
		this.length = length;
		this.width = width;
		this.shipType = shipType;
		this.firstDate = firstDate;
		this.endDate = endDate;
		this.imo = imo;
		this.name = name;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public Integer getMmsi() {
		return mmsi;
	}

	public void setMmsi(Integer mmsi) {
		this.mmsi = mmsi;
	}

	public ShipType getShipType() {
		return shipType;
	}

	public void setShipType(ShipType shipType) {
		this.shipType = shipType;
	}

	public ArrayList<Track> getTracks() {
		return tracks;
	}

	public void setTracks(ArrayList<Track> tracks) {
		this.tracks = tracks;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getName() {
		return name;
	}

	public Integer getImo() {
		return imo;
	}

	public Date getFirstDate() {
		return firstDate;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setImo(Integer imo) {
		this.imo = imo;
	}

	public void setFirstDate(Date firstDate) {
		this.firstDate = firstDate;
	}

}
