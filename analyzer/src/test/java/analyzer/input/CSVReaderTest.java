package analyzer.input;

import org.junit.Ignore;
import org.junit.Test;

import com.vividsolutions.jts.util.Assert;

import controller.input.CSVReader;
import model.port.PortContainer;
import model.vessel.VesselContainer;

public class CSVReaderTest {

	private final String csvLocationDynamicRelative = "historicData/DynamicData.csv";
	private final String csvLocationStaticRelative = "historicData/voyageData.csv";
	private final String csvLocationPortList = "portList/portList.csv";

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
