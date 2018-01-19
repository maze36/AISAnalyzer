package analyzer.input;

import org.geotools.graph.structure.Graph;
import org.geotools.graph.structure.Node;
import org.junit.Test;

import com.vividsolutions.jts.geom.Coordinate;

import controller.input.ShapefileReader;

public class ShapefileReaderTest {

	@SuppressWarnings("unused")
	@Test
	public void testShapefileReader() {
		String path = "C:/Users/Matthias/Downloads/RTM_MWotS_jun14/RTM_MWotS_jun14/RTM_MWotS_jun14_clean.shp";

		Graph graph = ShapefileReader.getRTM(path);

		for (Object node : graph.getNodes()) {
			Node nde = (Node) node;
			Coordinate coord = (Coordinate) nde.getObject();

			System.out.println();
		}

		System.out.println(graph.getNodes().size());

	}
}
