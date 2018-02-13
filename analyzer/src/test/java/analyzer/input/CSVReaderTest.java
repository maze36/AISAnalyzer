package analyzer.input;

import org.junit.Ignore;
import org.junit.Test;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.util.Assert;

import controller.input.CSVReader;
import model.port.PortContainer;
import model.quadtree.newTree.Quadtree;
import model.vessel.VesselContainer;

public class CSVReaderTest {

	private final String csvLocationDynamicRelative = "historicData/DynamicData.csv";
	private final String csvLocationStaticRelative = "historicData/voyageData.csv";
	private final String csvLocationPortList = "portList/portList.csv";
	private final String csvLocationNodes = "historicNodes/Nodes.csv";

	@Test
	public void testNodeReader() {
		Quadtree result = CSVReader.readHistoricNodes(csvLocationNodes);

		Coordinate newNode = new Coordinate(55.78010730195087, 12.658772286887615);

		Coordinate foundNode = result.findNearestRoadNetworkNode(newNode);

		Assert.isTrue(foundNode != null);

	}

	@Ignore
	@Test
	public void testCSVReaderStaticData() {
		String path = csvLocationStaticRelative;
		PortContainer container = CSVReader.readPortList(csvLocationPortList);
		VesselContainer result = CSVReader.readStaticAISMessages(path, container);

		Assert.isTrue(!result.isEmpty());

	}

	@Ignore
	@Test
	public void testCSVReaderDynamicData() {
		String path = csvLocationDynamicRelative;
		VesselContainer vesselContainer = new VesselContainer();

		CSVReader.readDynamicAISMessage(path, vesselContainer);
	}

	@Test
	public void testCSVReaderPortData() {
		PortContainer container = CSVReader.readPortList(csvLocationPortList);

		Assert.isTrue(!container.isEmpty());

	}

}
