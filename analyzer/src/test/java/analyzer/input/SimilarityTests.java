package analyzer.input;

import org.junit.Test;

import controller.input.CSVReader;
import controller.input.stringSimilarity.DestinationIdentifier;
import model.port.Port;
import model.port.PortContainer;

public class SimilarityTests {

	@Test
	public void testSimilarityMethods() {

		PortContainer container = CSVReader.readPortList("portList/portList.csv");

		String s3 = "PLGDY,";

		Port result = DestinationIdentifier.identifyHarbour(s3, container);

		System.out.println(result.getHarbourName());

	}

}
