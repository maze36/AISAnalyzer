package controller.input;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import model.ais.AISMessage;
import model.port.Port;
import model.port.PortContainer;
import model.track.Track;
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
	private final static String AIS_SPLITTER = ",";
	private final static String PORT_SPLITTER = ";";

	/**
	 * Reads a CSV file that contains static aisMessages. A
	 * {@link VesselContainer} is created, filled with {@link Vessel} and
	 * returned.
	 * 
	 * @param csvLocation
	 *            The path of the CSV file.
	 * @return
	 */
	public static VesselContainer readStaticAISMessages(String csvLocation, PortContainer ports) {

		System.out.println("Reading static ais message from " + csvLocation);

		VesselContainer result = new VesselContainer();

		try {
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new FileReader(csvLocation));

			while ((LINE = reader.readLine()) != null) {
				String[] aisMessage = LINE.split(AIS_SPLITTER);

				// check if header
				if (!aisMessage[0].contains("name")) {
					if (result.isEmpty()) {
						String name = aisMessage[0].replaceAll("\"", "");
						Integer mmsi = Integer.valueOf(aisMessage[1].replaceAll("\"", ""));
						double length = Double.valueOf(aisMessage[2].replaceAll("\"", ""));
						double width = Double.valueOf(aisMessage[3].replaceAll("\"", ""));
						ShipType shipType = extractShipType(aisMessage[7].replaceAll("\"", ""));
						Integer imo = Integer.valueOf(aisMessage[8].replaceAll("\"", ""));
						Date timestamp = transformDate(aisMessage[9].replaceAll("\"", ""));
						String destination = aisMessage[10].replaceAll("\"", "");
						Vessel vessel = new Vessel(mmsi, length, width, shipType, name, imo);
						vessel.addDestination(destination, timestamp);
						result.addVessel(vessel);
					} else {
						Integer mmsi = Integer.valueOf(aisMessage[1].replaceAll("\"", ""));

						if (!result.vesselExists(mmsi)) {
							String name = aisMessage[0].replaceAll("\"", "");
							double length = Double.valueOf(aisMessage[2].replaceAll("\"", ""));
							double width = Double.valueOf(aisMessage[3].replaceAll("\"", ""));
							ShipType shipType = extractShipType(aisMessage[5].replaceAll("\"", ""));
							Integer imo = Integer.valueOf(aisMessage[8].replaceAll("\"", ""));
							Date timestamp = transformDate(aisMessage[9].replaceAll("\"", ""));
							String destination = aisMessage[10].replaceAll("\"", "");
							Vessel vessel = new Vessel(mmsi, length, width, shipType, name, imo);
							vessel.addDestination(destination, timestamp);
							result.addVessel(vessel);
						} else {
							Vessel vessel = result.findVesselByMMSI(mmsi);
							if (vessel != null) {
								Date timestamp = transformDate(aisMessage[9].replaceAll("\"", ""));
								String destination = aisMessage[10].replaceAll("\"", "");
								vessel.addDestination(destination, timestamp);
							}
						}

					}
				}
			}
			System.out.println("Finished reading static ais messages");

			return result;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Reads a CSV file that contains dynamic aisMessages. The {@link Track}
	 * list of each vessel is filled with the respective {@link AISMessage}.
	 * 
	 * @param csvLocation
	 * @param vesselContainer
	 * @return True if successful, otherwise false.
	 */
	public static VesselContainer readDynamicAISMessage(String csvLocation, VesselContainer vesselContainer) {

		System.out.println("Reading dynamic ais messages from " + csvLocation);

		// TODO find Port for Track

		try {
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new FileReader(csvLocation));

			while ((LINE = reader.readLine()) != null) {
				String[] aisMessage = LINE.split(AIS_SPLITTER);
				if (!aisMessage[0].contains("mmsi")) {

					Integer mmsi = Integer.valueOf(aisMessage[0].replaceAll("\"", ""));
					double heading = Double.valueOf(aisMessage[1].replaceAll("\"", ""));
					double sog = Double.valueOf(aisMessage[2].replaceAll("\"", ""));
					double cog = Double.valueOf(aisMessage[3].replaceAll("\"", ""));
					double rot = Double.valueOf(aisMessage[4].replaceAll("\"", ""));
					Date timestamp = transformDate(aisMessage[6].replaceAll("\"", ""));
					double lat = Double.valueOf(aisMessage[7].replaceAll("\"", ""));
					double lon = Double.valueOf(aisMessage[8].replaceAll("\"", ""));

					AISMessage message = new AISMessage(mmsi, cog, sog, lat, lon, timestamp, rot, heading);

					for (Vessel vessel : vesselContainer.getList()) {
						if (vessel.getMmsi().intValue() == message.getMmsi().intValue()) {
							if (vessel.getTracks().isEmpty()) {
								Track track = new Track(message);
								vessel.getTracks().add(track);
							} else {
								boolean trackFound = false;
								for (Track track : vessel.getTracks()) {
									if (track.messageBelongsToTrack(message.getTimestamp())) {
										track.addMessage(message);
										trackFound = true;
										break;
									}
								}
								if (!trackFound) {
									Track newTrack = new Track(message);
									vessel.getTracks().add(newTrack);
								}
							}
						}
					}
				}
			}

			for (Vessel vessel : vesselContainer.getList()) {
				if (!vessel.getTracks().isEmpty()) {
					ArrayList<Track> updatedTracks = cleanTrackList(vessel.getTracks());
					vessel.setTracks(updatedTracks);
					addDestinationsToTracks(vessel);
				}
			}

			System.out.println("Finished reading dynamic ais messages");

			return vesselContainer;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static PortContainer readPortList(String csvLocation) {
		PortContainer container = new PortContainer();

		System.out.println("Reading port list " + csvLocation);

		try {
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new FileReader(csvLocation));

			while ((LINE = reader.readLine()) != null) {
				String[] row = LINE.split(PORT_SPLITTER);
				if (!row[0].contains("Harbour")) {
					String harbourName = row[0];
					String country = row[1];
					double lat = Double.valueOf(row[2]);
					double lon = Double.valueOf(row[3]);
					String unctad = row[4];
					String alias = row[5];

					container.addPort(new Port(lat, lon, country, unctad, alias, harbourName));

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Finished reading port list");

		return container;
	}

	@SuppressWarnings("rawtypes")
	private static Vessel addDestinationsToTracks(Vessel vessel) {
		for (Track track : vessel.getTracks()) {
			Iterator it = vessel.getDestinations().entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				Date dateOfDestination = (Date) pair.getValue();
				if (isInInterval(track.getStartDate(), track.getEndDate(), dateOfDestination)) {
					track.setDestination((String) pair.getKey());
				}
			}
		}
		return vessel;
	}

	private static boolean isInInterval(Date start, Date end, Date check) {
		long timeStart = start.getTime();
		long timeEnd = end.getTime();
		long timeDate = check.getTime();

		if (timeStart <= timeDate && timeDate <= timeEnd) {
			return true;
		} else {
			return false;
		}
	}

	private static ArrayList<Track> cleanTrackList(ArrayList<Track> tracks) {
		ArrayList<Track> tracksToRemove = new ArrayList<Track>();
		for (Track track : tracks) {
			long intervalLenght = track.getEndDate().getTime() - track.getStartDate().getTime();
			if (intervalLenght < 1200000) {
				tracksToRemove.add(track);
			}
		}
		tracks.removeAll(tracksToRemove);
		return tracks;
	}

	/**
	 * Checks which {@link ShipType} is contained in the given {@link String}.
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

		String newDateString = timestampString.replaceAll(" ", "");

		try {
			Date result = df.parse(newDateString);
			return result;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
}
