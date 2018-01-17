package model.statistics;

import java.util.HashMap;

import model.vessel.ShipType;

/**
 * A {@link StatisticalNode} conforms exactly one node in the road network
 * model. It contains all statistical data.
 * 
 * @author msteidel
 *
 */
public class StatisticalNode {

	private double lat;

	private double lon;

	private HashMap<String, Double> cogDistribution;

	private HashMap<String, Double> sogDistribution;

	private HashMap<ShipType, Double> shipTypeDistribution;

	private HashMap<String, Double> lengthDistribution;

	public StatisticalNode(double lat, double lon) {
		this.cogDistribution = new HashMap<>();
		this.sogDistribution = new HashMap<>();
		this.shipTypeDistribution = new HashMap<>();
		this.lat = lat;
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public HashMap<String, Double> getCogDistribution() {
		return cogDistribution;
	}

	public HashMap<String, Double> getSogDistribution() {
		return sogDistribution;
	}

	public HashMap<ShipType, Double> getShipTypeDistribution() {
		return shipTypeDistribution;
	}

	public HashMap<String, Double> getLengthDistribution() {
		return lengthDistribution;
	}

}
