package controller.input.stringSimilarity;

import info.debatty.java.stringsimilarity.Damerau;
import model.port.Port;
import model.port.PortContainer;

public class DestinationIdentifier {

	public static Port identifyHarbour(String ais, PortContainer portContainer) {

		double minDistance = -1;

		Port result = new Port();

		String compare = ais.toLowerCase().trim();

		for (Port port : portContainer.getPorts()) {

			double distanceName = compareStringsWithDamerau(port.getHarbourName().toLowerCase(), compare);
			double distanceAlias = compareStringsWithDamerau(port.getAlias().toLowerCase().trim(), compare);

			if (minDistance == -1) {
				minDistance = distanceName;
				result = port;
			} else if (minDistance > distanceName && distanceName < distanceAlias) {
				minDistance = distanceName;
				result = port;
			} else if (minDistance > distanceAlias && distanceAlias < distanceName) {
				minDistance = distanceAlias;
				result = port;
			}

		}
		return result;
	}

	private static double compareStringsWithDamerau(String string1, String string2) {

		Damerau damerau = new Damerau();

		double distance = damerau.distance(string1, string2);

		return distance;

	}

}
