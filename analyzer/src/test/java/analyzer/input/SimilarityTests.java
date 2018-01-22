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

		String s1 = "BREMERHAVEN    ";
		String s2 = "BREMERHAVEM  ";
		String s3 = "PLGDY";
		String s4 = "BREMERHEAVEN";
		String s5 = "BREMEN";
		String s6 = "BREMERHAVVENJF";

		Port result = DestinationIdentifier.identifyHarbour(s3, container);

		System.out.println(result.getHarbourName());

	}

}
