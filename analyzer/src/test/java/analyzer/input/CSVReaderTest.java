package analyzer.input;

import org.junit.Test;

import com.vividsolutions.jts.util.Assert;

import controller.input.CSVReader;
import model.vessel.VesselContainer;

public class CSVReaderTest {

	private final String csvLocationDynamicRelative = "historicData/DynamicData.csv";
	private final String csvLocationStaticRelative = "historicData/voyageData.csv";

	@Test
	public void testCSVReaderStaticData() {
		String path = csvLocationStaticRelative;

		VesselContainer result = CSVReader.readStaticAISMessages(path);

		Assert.isTrue(!result.isEmpty());

	}

	@Test
	public void testCSVReaderDynamicData() {
		String path = csvLocationDynamicRelative;
		VesselContainer vesselContainer = new VesselContainer();

		CSVReader.readDynamicAISMessage(path, vesselContainer);
	}

}
