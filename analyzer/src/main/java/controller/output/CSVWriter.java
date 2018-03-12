package controller.output;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import model.jadeNode.JadeNode;
import model.statistics.StatisticalNode;
import model.statistics.StatisticalNodeContainer;

/**
 * Writes data to a CSV file.
 * 
 * @author msteidel
 *
 */
public class CSVWriter {

	public static boolean saveData(StatisticalNodeContainer nodeContainer) {

		// For each node a new file

		System.out.println("Writing CSV files");

		File directory = createDirectory();

		int counter = 0;

		for (StatisticalNode node : nodeContainer.getNodeList()) {
			try {
				writeNodeToCSV(node, counter, directory);
				counter++;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return false;
			}

		}

		System.out.println("Finished writing CSV files");
		return true;

	}

	@SuppressWarnings("rawtypes")
	private static void writeNodeToCSV(StatisticalNode node, int counter, File directory) throws FileNotFoundException {

		DecimalFormat decimalFormat = new DecimalFormat("###.##");

		String dirPath = directory.getPath();

		PrintWriter pw = new PrintWriter(
				new File(dirPath + "/StatisticalNode_" + "counter_" + node.getLat() + "_" + node.getLon() + ".csv"));
		StringBuilder sb = new StringBuilder();
		sb.append("Category");
		sb.append('+');
		sb.append("Feature");
		sb.append('+');
		sb.append("Count");
		sb.append('\n');

		sb.append("Position");
		sb.append('+');
		sb.append(node.getLat());
		sb.append('+');
		sb.append(node.getLon());
		sb.append('\n');

		// COG Distribution
		Iterator it = node.getCogDistribution().entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			sb.append("CogDistribution");
			sb.append('+');
			sb.append(decimalFormat.format(Double.valueOf((String) pair.getKey())));
			sb.append('+');
			sb.append(pair.getValue());
			sb.append('\n');
		}

		it = node.getShipTypeDistribution().entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			sb.append("ShipTypeDistribution");
			sb.append('+');
			sb.append(pair.getKey());
			sb.append('+');
			sb.append(pair.getValue());
			sb.append('\n');
		}

		it = node.getLengthDistribution().entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			sb.append("LengthDistribution");
			sb.append('+');
			sb.append(pair.getKey());
			sb.append('+');
			sb.append(pair.getValue());
			sb.append('\n');
		}

		it = node.getSogDistribution().entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			sb.append("SOGDistribution");
			sb.append('+');
			sb.append(decimalFormat.format(Double.valueOf((String) pair.getKey())));
			sb.append('+');
			sb.append(pair.getValue());
			sb.append('\n');
		}

		it = node.getDestinationDistribution().entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			sb.append("Destinations");
			sb.append('+');
			sb.append(pair.getKey());
			sb.append('+');
			sb.append(pair.getValue());
			sb.append('\n');
		}

		pw.write(sb.toString());
		pw.close();

	}

	private static File createDirectory() {
		File file = new File("Ergebnisse");

		if (!file.exists()) {
			file.mkdir();
		}

		return file;
	}

	private static File createJadeDirectory() {
		File file = new File("JadeResults");

		if (!file.exists()) {
			file.mkdir();
		}

		return file;
	}

	public static void writeJadeNodesCSV(ArrayList<JadeNode> result) throws FileNotFoundException {
		File directory = createJadeDirectory();

		String dirPath = directory.getPath();

		for (JadeNode node : result) {

			String name = node.getName().replaceAll("\"", "");

			PrintWriter pw = new PrintWriter(new File(dirPath + "/JadeNode_" + name + ".csv"));
			StringBuilder sb = new StringBuilder();
			sb.append("Name");
			sb.append('+');
			sb.append("Position lat");
			sb.append('+');
			sb.append("Position lon");
			sb.append('+');
			sb.append("mmsi");
			sb.append('+');
			sb.append("heading");
			sb.append('+');
			sb.append("sog");
			sb.append('+');
			sb.append("cog");
			sb.append('+');
			sb.append("rot");
			sb.append('+');
			sb.append("theme");
			sb.append('+');
			sb.append("timestamp");
			sb.append('+');
			sb.append("lat");
			sb.append('+');
			sb.append("lon");
			sb.append('+');
			sb.append("cog [Binned]");
			sb.append('+');
			sb.append("hourOfDay");
			sb.append('+');
			sb.append("DailySegment");
			sb.append('+');
			sb.append("length");
			sb.append('+');
			sb.append("shiptype");
			sb.append('+');
			sb.append("mmsi");
			sb.append('\n');

			for (String[] dataPoint : node.getDataPoints()) {

				sb.append(node.getName());
				sb.append('+');
				sb.append(node.getPosition().x);
				sb.append('+');
				sb.append(node.getPosition().y);
				sb.append('+');
				sb.append(dataPoint[0]);
				sb.append('+');
				sb.append(dataPoint[1]);
				sb.append('+');
				sb.append(dataPoint[2]);
				sb.append('+');
				sb.append(dataPoint[3]);
				sb.append('+');
				sb.append(dataPoint[4]);
				sb.append('+');
				sb.append(dataPoint[5]);
				sb.append('+');
				sb.append(dataPoint[6]);
				sb.append('+');
				sb.append(dataPoint[7]);
				sb.append('+');
				sb.append(dataPoint[8]);
				sb.append('+');
				sb.append(dataPoint[9]);
				sb.append('+');
				sb.append(dataPoint[10]);
				sb.append('+');
				sb.append(dataPoint[11]);
				sb.append('+');
				sb.append(dataPoint[12]);
				sb.append('+');
				sb.append(dataPoint[13]);
				sb.append('+');
				sb.append(dataPoint[14]);
				sb.append('+');
				sb.append(dataPoint[15]);
				sb.append('\n');
			}
			pw.write(sb.toString());
			pw.close();
		}

	}

}
