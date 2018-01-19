package analyzer.statisticalAIS;

import org.junit.Before;
import org.junit.Test;

import model.quadtree.RoadNetworkQuadtree;
import model.vessel.VesselContainer;

@SuppressWarnings("unused")
public class AISStatisticalAnalyzerTest {

	private VesselContainer vesselContainer = new VesselContainer();
	private final String csvLocationDynamicBigFile = "C:/Users/Matthias/Desktop/historische Daten/dynamicData.csv";
	private final String csvLocationStaticBigFile = "C:/Users/Matthias/Desktop/historische Daten/staticData.csv";
	private final String csvLocationDynamicSmallFile = "C:/Users/Matthias/Desktop/historische Daten/historische AIS Daten/DynamicData.csv";
	private final String csvLocationStaticSmallFile = "C:/Users/Matthias/Desktop/historische Daten/historische AIS Daten/voyageData.csv";
	private final String locationOfShapefile = "C:/Users/Matthias/Downloads/RTM_MWotS_jun14/RTM_MWotS_jun14/RTM_MWotS_jun14_clean.shp";
	private final String csvLocationDynamicRelative = "DynamicData.csv";
	private final String csvLocationStaticRelative = "voyageData.csv";
	private RoadNetworkQuadtree quadtree;

	@Before
	public void init() {
		// this.vesselContainer =
		// CSVReader.readStaticAISMessages(csvLocationStaticSmallFile);
		// this.vesselContainer =
		// CSVReader.readDynamicAISMessage(csvLocationDynamicSmallFile,
		// this.vesselContainer);
		//
		// this.quadtree = new RoadNetworkQuadtree(new Envelope(0.0, 100.0, 0.0,
		// 100.0), 100, 100);
		// Graph graph = ShapefileReader.getRTM(locationOfShapefile);
		//
		// @SuppressWarnings("unchecked")
		// Iterator<Node> iterator = graph.getNodes().iterator();
		//
		// while (iterator.hasNext()) {
		// quadtree.insert(iterator.next());
		// }
	}

	@Test
	public void testStatisticalAnalyzer() {
		// AISStatisticalAnalyzer analyzer = new AISStatisticalAnalyzer();
		//
		// StatisticalNodeContainer resultContainer =
		// analyzer.augmentNodes(quadtree, vesselContainer);
		// CSVWriter.saveData(resultContainer);

	}
}
