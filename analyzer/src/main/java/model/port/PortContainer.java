package model.port;

import java.util.ArrayList;

public class PortContainer {

	private ArrayList<Port> ports;

	public PortContainer() {
		this.ports = new ArrayList<Port>();
	}

	public PortContainer(ArrayList<Port> ports) {
		this.ports = ports;
	}

	public ArrayList<Port> getPorts() {
		return this.ports;
	}

	public ArrayList<Port> findPortsByCountry(String country) {

		ArrayList<Port> result = new ArrayList<Port>();

		for (Port port : ports) {
			if (port.getCountry().equals(country)) {
				result.add(port);
			}
		}
		return result;
	}

	public Port findPortByHarbourName(String harbourName) {
		for (Port port : ports) {
			if (port.getHarbourName().equals(harbourName)) {
				return port;
			}
		}
		return null;
	}

	public Port findPortByAlias(String alias) {
		for (Port port : ports) {
			if (port.getAlias().equals(alias)) {
				return port;
			}
		}
		return null;
	}

}
