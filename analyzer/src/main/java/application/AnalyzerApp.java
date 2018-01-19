package application;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.geotools.graph.structure.Graph;
import org.geotools.graph.structure.Node;

import com.vividsolutions.jts.geom.Envelope;

import controller.analyzing.AISStatisticalAnalyzer;
import controller.input.CSVReader;
import controller.input.ShapefileReader;
import controller.output.CSVWriter;
import model.port.PortContainer;
import model.quadtree.RoadNetworkQuadtree;
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
	private final static String csvLocationPortFile = "portList/portList.csv";
	private final static String locationOfShapefile = "shapefile/RTM_MWotS_jun14_clean.shp";
	private static RoadNetworkQuadtree quadtree;

	public static void main(String[] args) {
		long currentTime = System.currentTimeMillis();
		System.out.println("Starting app at " + new Timestamp(currentTime));
		init();
		runLogic();

		long endTime = System.currentTimeMillis();

		long duration = endTime - currentTime;

		System.out.println("Calculation finished at " + new Timestamp(endTime));
		System.out.println("Total duration in seconds: " + TimeUnit.MILLISECONDS.toSeconds(duration));
	}

	public static void runLogic() {
		AISStatisticalAnalyzer analyzer = new AISStatisticalAnalyzer();
		StatisticalNodeContainer nodeContainer = analyzer.augmentNodes(quadtree, vesselContainer, portContainer);
		CSVWriter.saveData(nodeContainer);
	}

	public static void init() {
		portContainer = CSVReader.readPortList(csvLocationPortFile);
		vesselContainer = CSVReader.readStaticAISMessages(csvLocationStaticFile, portContainer);
		vesselContainer = CSVReader.readDynamicAISMessage(csvLocationDynamicFile, vesselContainer);

		quadtree = new RoadNetworkQuadtree(new Envelope(0.0, 100.0, 0.0, 100.0), 100, 100);
		Graph graph = ShapefileReader.getRTM(locationOfShapefile);

		System.out.println("Building Quadtree");

		@SuppressWarnings("unchecked")
		Iterator<Node> iterator = graph.getNodes().iterator();

		while (iterator.hasNext()) {
			quadtree.insert(iterator.next());
		}
	}

}
