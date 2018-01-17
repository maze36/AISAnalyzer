package analyzer.statisticalAIS;

import java.util.Iterator;

import org.geotools.graph.structure.Graph;
import org.geotools.graph.structure.Node;
import org.junit.Before;
import org.junit.Test;

import com.vividsolutions.jts.geom.Envelope;

import controller.analyzing.AISStaticalAnalyzer;
import controller.input.CSVReader;
import controller.input.ShapefileReader;
import model.quadtree.RoadNetworkQuadtree;
import model.statistics.StatisticalNodeContainer;
import model.vessel.VesselContainer;

public class AISStatisticalAnalyzerTest {

	private VesselContainer vesselContainer;
	private final String csvLocationDynamicBigFile = "C:/Users/Matthias/Desktop/historische Daten/dynamicData.csv";
	private final String csvLocationStaticBigFile = "C:/Users/Matthias/Desktop/historische Daten/staticData.csv";
	private final String csvLocationDynamicSmallFile = "C:/Users/Matthias/Desktop/historische Daten/historische AIS Daten/DynamicData.csv";
	private final String csvLocationStaticSmallFile = "C:/Users/Matthias/Desktop/historische Daten/historische AIS Daten/voyageData.csv";
	private final String locationOfShapefile = "C:/Users/Matthias/Downloads/RTM_MWotS_jun14/RTM_MWotS_jun14/RTM_MWotS_jun14_clean.shp";
	private RoadNetworkQuadtree quadtree;

	@Before
	public void init() {
		this.vesselContainer = CSVReader.readStaticAISMessages(csvLocationStaticSmallFile);
		this.vesselContainer = CSVReader.readDynamicAISMessage(csvLocationDynamicSmallFile, this.vesselContainer);
		this.quadtree = new RoadNetworkQuadtree(new Envelope(0.0, 100.0, 0.0, 100.0), 100, 100);
		Graph graph = ShapefileReader.getRTM(locationOfShapefile);

		@SuppressWarnings("unchecked")
		Iterator<Node> iterator = graph.getNodes().iterator();

		while (iterator.hasNext()) {
			quadtree.insert(iterator.next());
		}
	}

	@Test
	public void testStatisticalAnalyzer() {
		AISStaticalAnalyzer analyzer = new AISStaticalAnalyzer();

		StatisticalNodeContainer resultContainer = analyzer.augmentNodes(quadtree, vesselContainer);

	}
}
