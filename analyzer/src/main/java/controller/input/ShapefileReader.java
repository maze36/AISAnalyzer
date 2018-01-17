package controller.input;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FeatureSource;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.geometry.jts.JTS;
import org.geotools.graph.build.line.BasicLineGraphGenerator;
import org.geotools.graph.structure.Graph;
import org.geotools.referencing.CRS;
import org.opengis.feature.Feature;
import org.opengis.feature.GeometryAttribute;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineSegment;

/**
 * Reads in a .shp file of a road network.
 * 
 * @author msteidel
 *
 */
public class ShapefileReader {

	public static Graph getRTM(String locationOfShapefile) {
		ArrayList<Geometry> shapes = readInShapefile(locationOfShapefile);
		BasicLineGraphGenerator gen = new BasicLineGraphGenerator();

		for (Geometry geo : shapes) {
			LineSegment segment = new LineSegment(geo.getCoordinates()[0], geo.getCoordinates()[1]);
			gen.add(segment);
		}

		Graph graph = gen.getGraph();

		return graph;
	}

	/**
	 * Reads in a shape file and extracts the {@link Geometry} that is contained
	 * in the file.
	 * 
	 * @param locationOfShapefile
	 *            The location of the shape file. File has to end with .shp
	 * @return An {@link ArrayList} that contains the {@link Geometry} of the
	 *         shape file.
	 */
	private static ArrayList<Geometry> readInShapefile(String locationOfShapefile) {

		File shapefile = getFile(locationOfShapefile);

		HashMap<String, URL> connect = new HashMap<String, URL>();
		ArrayList<Geometry> result = new ArrayList<Geometry>();
		try {
			connect.put("url", shapefile.toURI().toURL());
			DataStore dataStore = DataStoreFinder.getDataStore(connect);
			String[] typeNames = dataStore.getTypeNames();
			String typeName = typeNames[0];

			FeatureSource<?, ?> featureSource = dataStore.getFeatureSource(typeName);
			FeatureCollection<?, ?> collection = featureSource.getFeatures();
			SimpleFeatureType schema = (SimpleFeatureType) featureSource.getSchema();
			CoordinateReferenceSystem dataCRS = schema.getCoordinateReferenceSystem();
			CoordinateReferenceSystem worldCRS = CRS.decode("EPSG:4326");
			MathTransform transform = CRS.findMathTransform(dataCRS, worldCRS);
			FeatureIterator<?> iterator = collection.features();

			while (iterator.hasNext()) {
				Feature feature = iterator.next();
				GeometryAttribute geometryAttr = feature.getDefaultGeometryProperty();
				Geometry geometry = (Geometry) geometryAttr.getValue();
				if (geometry != null) {
					Geometry resultGeometry = JTS.transform(geometry, transform);
					result.add(resultGeometry);
				}
			}
			iterator.close();
			return result;

		} catch (IOException | FactoryException | MismatchedDimensionException | TransformException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static File getFile(String path) {
		return new File(path);
	}

}
