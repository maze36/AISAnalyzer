package controller.input;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import model.vessel.ShipType;
import model.vessel.Vessel;
import model.vessel.VesselContainer;

/**
 * To use for generating input data.
 * 
 * @author msteidel
 *
 */
public class CSVReader {

	private static String LINE = "";
	private final static String SPLITTER = ",";

	/**
	 * 
	 * @param csvLocation
	 * @return
	 */
	public static VesselContainer readStaticAISMessages(String csvLocation) {

		VesselContainer result = new VesselContainer();

		try {
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new FileReader(csvLocation));

			while ((LINE = reader.readLine()) != null) {
				String[] aisMessage = LINE.split(SPLITTER);

				// check if header
				if (!aisMessage[0].contains("name")) {
					if (result.isEmpty()) {
						String name = aisMessage[0].replaceAll("\"", "");
						Integer mmsi = Integer.valueOf(aisMessage[1].replaceAll("\"", ""));
						double length = Double.valueOf(aisMessage[2].replaceAll("\"", ""));
						double width = Double.valueOf(aisMessage[3].replaceAll("\"", ""));
						ShipType shipType = extractShipType(aisMessage[5].replaceAll("\"", ""));
						Integer imo = Integer.valueOf(aisMessage[6].replaceAll("\"", ""));
						Date firstDate = transformDate(aisMessage[7].replaceAll("\"", ""));
						Date endDate = transformDate(aisMessage[8].replaceAll("\"", ""));

						Vessel vessel = new Vessel(mmsi, length, width, shipType, name, imo, firstDate, endDate);
						result.addVessel(vessel);
					} else {
						Integer mmsi = Integer.valueOf(aisMessage[1].replaceAll("\"", ""));

						if (!result.vesselExists(mmsi)) {
							String name = aisMessage[0].replaceAll("\"", "");
							double length = Double.valueOf(aisMessage[2].replaceAll("\"", ""));
							double width = Double.valueOf(aisMessage[3].replaceAll("\"", ""));
							ShipType shipType = extractShipType(aisMessage[5].replaceAll("\"", ""));
							Integer imo = Integer.valueOf(aisMessage[6].replaceAll("\"", ""));
							Date firstDate = transformDate(aisMessage[7].replaceAll("\"", ""));
							Date endDate = transformDate(aisMessage[8].replaceAll("\"", ""));

							Vessel vessel = new Vessel(mmsi, length, width, shipType, name, imo, firstDate, endDate);
							result.addVessel(vessel);
						}

					}
				}

				System.out.println();
			}

			return result;

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param aisShipType
	 * @return
	 */
	private static ShipType extractShipType(String aisShipType) {
		String ais = aisShipType.toLowerCase().trim();

		for (ShipType shipType : ShipType.values()) {
			if (shipType.equalsName(ais)) {
				return shipType;
			}
		}
		return ShipType.OTHER;
	}

	/**
	 * Transforms the date in the desired format.
	 * 
	 * @param timestampString
	 * @return
	 */
	private static Date transformDate(String timestampString) {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);

		String newDateString = timestampString.replace(timestampString.charAt(0), ' ');
		newDateString = newDateString.replaceAll(" ", "");

		try {
			Date result = df.parse(newDateString);
			return result;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
}
