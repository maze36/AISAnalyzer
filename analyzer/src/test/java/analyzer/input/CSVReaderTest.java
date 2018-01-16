package analyzer.input;

import org.junit.Test;

import com.vividsolutions.jts.util.Assert;

import controller.input.CSVReader;
import model.vessel.VesselContainer;

public class CSVReaderTest {

	@Test
	public void testCSVReaderStaticData() {
		String path = "C:/Users/msteidel/Desktop/voyageData.csv";

		VesselContainer result = CSVReader.readStaticAISMessages(path);

		Assert.isTrue(!result.isEmpty());

	}

}
