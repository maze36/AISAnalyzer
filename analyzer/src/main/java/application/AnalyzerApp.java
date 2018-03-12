package application;

import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import controller.analyzing.AISStatisticalAnalyzer;
import controller.input.CSVReader;
import controller.output.CSVWriter;
import model.jadeNode.JadeNode;
import model.port.PortContainer;
import model.quadtree.jadeTree.JadeQuadtree;
import model.quadtree.newTree.Quadtree;
import model.statistics.StatisticalNodeContainer;
import model.vessel.VesselContainer;

/**
 * Run this class in order to start the application.
 * 
 * @author msteidel
 *
 */
public class AnalyzerApp {

	private static VesselContainer vesselContainer = new VesselContainer();
	private static PortContainer portContainer = new PortContainer();
	private final static String csvLocationDynamicFile = "historicData/dynamicData.csv";
	private final static String csvLocationStaticFile = "historicData/staticData.csv";
	private final static String csvLocationJadeData = "historicData/jadeData.csv";
	private final static String csvLocationJadeNodes = "jadeNodes/jadeNodes.csv";
	private final static String csvLocationNodes = "historicNodes/Nodes.csv";
	private static Quadtree quadtree;

	public static void main(String[] args) {
		long currentTime = System.currentTimeMillis();
		System.out.println("Starting app at " + new Timestamp(currentTime));

		Scanner scanner = new Scanner(System.in);

		System.out.println("Analyze RTM-Nodes(1) or Jade-Nodes(2)?");

		String input = scanner.nextLine();

		int in = Integer.valueOf(input);

		switch (in) {
		case 1:
			scanner.close();
			initRTMAnalysis();
			break;
		case 2:
			scanner.close();
			initJadeAnalysis();
			break;
		default:
			break;
		}

		long endTime = System.currentTimeMillis();

		long duration = endTime - currentTime;

		System.out.println("Calculation finished at " + new Timestamp(endTime));
		System.out.println("Total duration in seconds: " + TimeUnit.MILLISECONDS.toSeconds(duration));
	}

	private static void initJadeAnalysis() {
		JadeQuadtree jadeNodes = CSVReader.readJadeNodes(csvLocationJadeNodes);
		ArrayList<JadeNode> result = CSVReader.readAndProcessHistoricJadeData(csvLocationJadeData, jadeNodes);
		try {
			CSVWriter.writeJadeNodesCSV(result);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public static void runLogic() {
		AISStatisticalAnalyzer analyzer = new AISStatisticalAnalyzer();
		StatisticalNodeContainer nodeContainer = analyzer.augmentNodes(quadtree, vesselContainer, portContainer);
		CSVWriter.saveData(nodeContainer);
	}

	public static void initRTMAnalysis() {

		System.out.println("Building Quadtree");
		quadtree = CSVReader.readHistoricNodes(csvLocationNodes);

		vesselContainer = CSVReader.readStaticAISMessages(csvLocationStaticFile, portContainer);
		CSVReader.readAndProcessDynamicAISMessages(csvLocationDynamicFile, vesselContainer, quadtree);

	}

}
