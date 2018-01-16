package controller.util;

import java.util.ArrayList;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;

public class UtilMethods {

	/**
	 * Converts an {@link ArrayList} of {@link LineString} into {@link ArrayList} of {@link Coordinate.
	 * @param lineStrings 
	 * @return
	 */
	public static ArrayList<Coordinate> convertLineStringIntoCoordinate(ArrayList<Geometry> lineStrings) {

		ArrayList<Coordinate> result = new ArrayList<Coordinate>();

		for (Geometry geo : lineStrings) {
			for (int i = 0; i < geo.getCoordinates().length; i++) {
				Coordinate coord = geo.getCoordinates()[i];
				result.add(coord);
			}
		}

		return result;
	}
	
	
}
