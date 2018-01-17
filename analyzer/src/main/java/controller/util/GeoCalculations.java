package controller.util;

import java.awt.geom.Point2D;

import org.geotools.referencing.GeodeticCalculator;

import com.vividsolutions.jts.geom.Coordinate;

import model.units.LengthUnit;
import model.units.enums.MathematicalOperation;

/**
 * Provides methods in order to calculate with geospatial data.
 * @author Matthias
 *
 */
public class GeoCalculations {

	/**
	 * Calculates the distance between a two given points and returns the result
	 * in the desired unit.
	 * 
	 * @param start
	 *            The start {@link Coordinate}.
	 * @param end
	 *            The target {@link Coordinate}.
	 * @param unit
	 *            The desired {@link LengthUnit} of the result.
	 * @return
	 */
	public static double calculateDistance(Coordinate start, Coordinate end, LengthUnit unit) {
		GeodeticCalculator calculator = new GeodeticCalculator();
		calculator.setStartingGeographicPoint(start.y, start.x);
		calculator.setDestinationGeographicPoint(end.y, end.x);
		double distance = calculator.getOrthodromicDistance();

		switch (unit) {
		case NAUTICALMILES:
			distance = distance * 0.000539957;
			return distance;
		case METER:
			return distance;
		}

		return distance;
	}
	
	/**
	 * Adds or subtracts a value to/from a given COG correctly.
	 * 
	 * @param cog
	 *            The given COG.
	 * @param value
	 *            The value which has to be added/subtracted.
	 * @param operation
	 *            The {@link MathematicalOperation}.
	 * @return
	 */
	public static double addOrSubtractAzimuth(double cog, double value, MathematicalOperation operation) {
		switch (operation) {
		case ADDING:
			if ((cog + value) > 360) {
				double rest = 360 - cog;
				rest = value - rest;
				double result = 0 + rest;
				return result;
			} else {
				double result = cog + value;
				return result;
			}
		case SUBTRACTING:
			if ((cog - value) < 0) {
				double rest = value - cog;
				double result = 360 - rest;
				return result;
			} else {
				double result = cog - value;
				return result;
			}
		}
		return 0;
	}
	
	/**
	 * Calculates a new position based on a given starting point, a distance and
	 * a direction.
	 * 
	 * @param start
	 *            The start {@link Coordinate} for the calculation.
	 * @param distance
	 *            The distance in NM of the new position based on the start.
	 * @param direction
	 *            The direction in which the new position shall be located.
	 * @return The new position as a {@link Point2D}.
	 */
	public static Point2D calculateNewPosition(Coordinate start, double distance, double direction) {

		GeodeticCalculator calculator = new GeodeticCalculator();

		calculator.setStartingGeographicPoint(start.y, start.x);

		distance = distance * 1852;

		calculator.setDirection(direction, distance);

		Point2D result = calculator.getDestinationGeographicPoint();
		result.setLocation(result.getY(), result.getX());

		return result;
	}
}
