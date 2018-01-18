package application;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.geotools.graph.structure.Graph;
import org.geotools.graph.structure.Node;

import com.vividsolutions.jts.geom.Envelope;

import controller.analyzing.AISStaticalAnalyzer;
import controller.input.CSVReader;
import controller.input.ShapefileReader;
import controller.output.CSVWriter;
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
	private final static String csvLocationDynamicBigFile = "C:/Users/Matthias/Desktop/historische Daten/dynamicData.csv";
	private final static String csvLocationStaticBigFile = "C:/Users/Matthias/Desktop/historische Daten/staticData.csv";
	private final static String csvLocationDynamicSmallFile = "C:/Users/Matthias/Desktop/historische Daten/historische AIS Daten/DynamicData.csv";
	private final static String csvLocationStaticSmallFile = "C:/Users/Matthias/Desktop/historische Daten/historische AIS Daten/voyageData.csv";
	private final static String locationOfShapefile = "C:/Users/Matthias/Downloads/RTM_MWotS_jun14/RTM_MWotS_jun14/RTM_MWotS_jun14_clean.shp";
	private static RoadNetworkQuadtree quadtree;

	public static void main(String[] args) {
		long currentTime = System.currentTimeMillis();
		System.out.println("Startin app at " + new Timestamp(currentTime));
		init();

		AISStaticalAnalyzer analyzer = new AISStaticalAnalyzer();
		StatisticalNodeContainer nodeContainer = analyzer.augmentNodes(quadtree, vesselContainer);
		CSVWriter.saveData(nodeContainer);

		long endTime = System.currentTimeMillis();

		long duration = endTime - currentTime;

		System.out.println("Calculation finished at " + new Timestamp(endTime));
		System.out.print("Duration in Minutes: " + TimeUnit.MILLISECONDS.toMinutes(duration));

	}

	public static void init() {

		vesselContainer = CSVReader.readStaticAISMessages(csvLocationStaticSmallFile);
		vesselContainer = CSVReader.readDynamicAISMessage(csvLocationDynamicSmallFile, vesselContainer);

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
