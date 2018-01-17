package analyzer.input;

import org.junit.Ignore;
import org.junit.Test;

import com.vividsolutions.jts.util.Assert;

import controller.input.CSVReader;
import model.vessel.VesselContainer;

public class CSVReaderTest {

	@Ignore
	@Test
	public void testCSVReaderStaticData() {
		String path = "C:/Users/msteidel/Desktop/voyageData.csv";

		VesselContainer result = CSVReader.readStaticAISMessages(path);

		Assert.isTrue(!result.isEmpty());

	}

	@Test
	public void testCSVReaderDynamicData() {
		String path = "C:/Users/Matthias/Desktop/historische Daten/historische AIS Daten/DynamicData.csv";

		VesselContainer vesselContainer = new VesselContainer();

		CSVReader.readDynamicAISMessage(path, vesselContainer);
	}

}
