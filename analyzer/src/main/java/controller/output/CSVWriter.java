package controller.output;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Map;

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
		sb.append(',');
		sb.append("Feature");
		sb.append(',');
		sb.append("Count");
		sb.append('\n');

		sb.append("Position");
		sb.append(',');
		sb.append(node.getLat());
		sb.append(',');
		sb.append(node.getLon());
		sb.append('\n');

		// COG Distribution
		Iterator it = node.getCogDistribution().entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			sb.append("CogDistribution");
			sb.append(',');
			sb.append(decimalFormat.format(Double.valueOf((String) pair.getKey())));
			sb.append(',');
			sb.append(pair.getValue());
			sb.append('\n');
		}

		it = node.getShipTypeDistribution().entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			sb.append("ShipTypeDistribution");
			sb.append(',');
			sb.append(pair.getKey());
			sb.append(',');
			sb.append(pair.getValue());
			sb.append('\n');
		}

		it = node.getLengthDistribution().entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			sb.append("LengthDistribution");
			sb.append(',');
			sb.append(pair.getKey());
			sb.append(',');
			sb.append(pair.getValue());
			sb.append('\n');
		}

		it = node.getSogDistribution().entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			sb.append("SOGDistribution");
			sb.append(',');
			sb.append(decimalFormat.format(Double.valueOf((String) pair.getKey())));
			sb.append(',');
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

}
