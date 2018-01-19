package model.port;

/**
 * Represents a port.
 * 
 * @author msteidel
 *
 */
public class Port {

	private double lat;

	private double lon;

	private String harbourName;

	private String Country;

	private String unctad;

	private String alias;

	public double getLat() {
		return lat;
	}

	public double getLon() {
		return lon;
	}

	public String getCountry() {
		return Country;
	}

	public String getUnctad() {
		return unctad;
	}

	public String getAlias() {
		return alias;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public void setCountry(String country) {
		Country = country;
	}

	public void setUnctad(String unctad) {
		this.unctad = unctad;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Port(double lat, double lon, String country, String unctad, String alias, String harbourName) {
		this.lat = lat;
		this.lon = lon;
		Country = country;
		this.unctad = unctad;
		this.alias = alias;
		this.harbourName = harbourName;
	}

	public String getHarbourName() {
		return harbourName;
	}

	public void setHarbourName(String harbourName) {
		this.harbourName = harbourName;
	}

}
