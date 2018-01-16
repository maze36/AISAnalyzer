package model.vessel;

import java.util.ArrayList;

import model.track.Track;


public class Vessel {

	private double length;
	
	private double width;
	
	private Integer mmsi;
	
	private ShipType shipType;
	
	private ArrayList<Track> tracks;
	
	public Vessel() {
		this.tracks = new ArrayList<Track>();
	}
	
	public Vessel(Integer mmsi, double length, double width, ShipType shipType, ArrayList<Track> tracks) {
		this.mmsi = mmsi;
		this.length = length;
		this.width = width;
		this.shipType = shipType;
		this.tracks = tracks;
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
	
}
