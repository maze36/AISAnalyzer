package controller.input.stringSimilarity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import info.debatty.java.stringsimilarity.Damerau;
import model.port.Port;
import model.port.PortContainer;

public class DestinationIdentifier {

	public static Port identifyHarbour(String ais, PortContainer portContainer) {

		boolean hasSpecial = hasSpecialCharacters(ais);
		Port result = new Port();

		if (!hasSpecial) {
			double minDistance = -1;

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

		}

		return result;
	}

	private static boolean hasSpecialCharacters(String ais) {

		Pattern special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]'");
		Pattern digit = Pattern.compile("[0-9]");
		boolean dots = ais.contains(".");
		boolean commas = ais.contains(",");
		Matcher hasSpecial = special.matcher(ais);
		Matcher hasDigit = digit.matcher(ais);

		if (hasSpecial.find() || hasDigit.find() || dots || commas) {
			return true;
		} else {
			return false;
		}

	}

	private static double compareStringsWithDamerau(String string1, String string2) {

		Damerau damerau = new Damerau();

		double distance = damerau.distance(string1, string2);

		return distance;

	}

}
