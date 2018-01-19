package model.vessel;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import model.track.Track;

public class Vessel {

	private double length;

	private double width;

	private Integer mmsi;

	private ShipType shipType;

	private ArrayList<Track> tracks;

	private String name;

	private Integer imo;

	private HashMap<String, Date> destinations;

	public Vessel() {
		this.tracks = new ArrayList<Track>();
		this.destinations = new HashMap<>();
	}

	public Vessel(Integer mmsi, double length, double width, ShipType shipType, String name, Integer imo) {
		this.mmsi = mmsi;
		this.length = length;
		this.width = width;
		this.shipType = shipType;
		this.imo = imo;
		this.name = name;
		this.tracks = new ArrayList<Track>();
		this.destinations = new HashMap<>();
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

	public String getName() {
		return name;
	}

	public Integer getImo() {
		return imo;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setImo(Integer imo) {
		this.imo = imo;
	}

	public void addDestination(String port, Date timestamp) {
		this.destinations.put(port, timestamp);
	}

	public HashMap<String, Date> getDestinations() {
		return destinations;
	}

	public void setDestinations(HashMap<String, Date> destinations) {
		this.destinations = destinations;
	}

}
