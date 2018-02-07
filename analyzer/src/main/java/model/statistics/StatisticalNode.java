package model.statistics;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import model.ais.AISMessage;
import model.vessel.ShipType;
import model.vessel.Vessel;

/**
 * A {@link StatisticalNode} conforms exactly one node in the road network
 * model. It contains all statistical data.
 * 
 * @author msteidel
 *
 */
public class StatisticalNode {

	private int id;

	private double lat;

	private double lon;

	private HashMap<String, Double> cogDistribution;

	private HashMap<String, Double> sogDistribution;

	private HashMap<ShipType, Double> shipTypeDistribution;

	private HashMap<String, Double> lengthDistribution;

	private HashMap<String, Double> destinationDistribution;

	private HashMap<String, Double> rotDistribution;

	public StatisticalNode(double lat, double lon) {
		this.cogDistribution = new HashMap<>();
		this.sogDistribution = new HashMap<>();
		this.shipTypeDistribution = new HashMap<>();
		this.destinationDistribution = new HashMap<>();
		this.lengthDistribution = new HashMap<>();
		this.rotDistribution = new HashMap<>();
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

	public HashMap<String, Double> getDestinationDistribution() {
		return this.destinationDistribution;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void updateNode(AISMessage message, Vessel vessel) {

		Double numberShipType = this.shipTypeDistribution.get(vessel.getShipType());
		Double numberLengthDistribution = this.lengthDistribution.get(String.valueOf(vessel.getLength()));
		Double numberCogDistribution = this.cogDistribution.get(String.valueOf(message.getCog()));
		Double numberSogDistribution = this.sogDistribution.get(String.valueOf(message.getSog()));
		Double numberRotDistribution = this.rotDistribution.get(String.valueOf(message.getRot()));
		Double numberDestinationDistribution = this.destinationDistribution.get(message.getDestination());

		if (numberShipType != null) {
			numberShipType = numberShipType + 1;
			this.shipTypeDistribution.put(vessel.getShipType(), numberShipType);
		} else {
			this.shipTypeDistribution.put(vessel.getShipType(), new Double(1));
		}

		if (numberLengthDistribution != null) {
			numberLengthDistribution = numberLengthDistribution + 1;
			this.lengthDistribution.put(String.valueOf(vessel.getLength()), numberLengthDistribution);
		} else {
			this.lengthDistribution.put(String.valueOf(vessel.getLength()), new Double(1));
		}

		if (numberCogDistribution != null) {
			numberCogDistribution = numberCogDistribution + 1;
			this.cogDistribution.put(String.valueOf(message.getCog()), numberCogDistribution);
		} else {
			this.cogDistribution.put(String.valueOf(message.getCog()), new Double(1));
		}

		if (numberSogDistribution != null) {
			numberSogDistribution = numberSogDistribution + 1;
			this.sogDistribution.put(String.valueOf(message.getSog()), numberSogDistribution);
		} else {
			this.sogDistribution.put(String.valueOf(message.getSog()), new Double(1));
		}

		if (numberRotDistribution != null) {
			numberRotDistribution = numberRotDistribution + 1;
			this.rotDistribution.put(String.valueOf(message.getRot()), numberRotDistribution);
		} else {
			this.rotDistribution.put(String.valueOf(message.getRot()), new Double(1));
		}

		if (numberDestinationDistribution != null) {
			numberDestinationDistribution = numberDestinationDistribution + 1;
			String destination = findDestination(message, vessel);
			this.destinationDistribution.put(destination, numberDestinationDistribution);

		} else {
			String destination = findDestination(message, vessel);
			this.destinationDistribution.put(destination, new Double(1));
		}

	}

	private String findDestination(AISMessage message, Vessel vessel) {
		HashMap<String, Date> destinations = vessel.getDestinations();
		long minTimeDiff = -1;
		String result = "";
		Iterator iterator = destinations.entrySet().iterator();
		boolean firstIteration = true;
		while (iterator.hasNext()) {
			Map.Entry pair = (Map.Entry) iterator.next();

			long timeMessage = message.getTimestamp().getTime();
			Date timestampDest = (Date) pair.getValue();
			long timeDest = timestampDest.getTime();

			// min time

			long timeDiff = Math.abs(timeDest - timeMessage);

			if (firstIteration) {
				minTimeDiff = timeDiff;
				result = (String) pair.getKey();
			} else if (minTimeDiff > timeDiff) {
				minTimeDiff = timeDiff;
				result = (String) pair.getKey();
			}

		}

		return result;
	}

}
