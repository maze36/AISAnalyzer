package view;

import java.awt.Color;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import org.geotools.graph.structure.Node;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.input.CenterMapListener;
import org.jxmapviewer.input.PanKeyListener;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.LocalResponseCache;
import org.jxmapviewer.viewer.TileFactoryInfo;

import com.vividsolutions.jts.geom.Coordinate;

import model.quadtree.RoadNetworkQuadtree;

public class WorldMap {

	public JPanel getWorldMap(RoadNetworkQuadtree quadtree) {
		TileFactoryInfo info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);
		DefaultTileFactory tileFactory = new DefaultTileFactory(info);
		tileFactory.setThreadPoolSize(8);

		File cacheDir = new File(System.getProperty("user.home") + File.separator + ".jxmapviewer2");
		LocalResponseCache.installResponseCache(info.getBaseURL(), cacheDir, false);

		JXMapViewer mapViewer = new JXMapViewer();
		mapViewer.setTileFactory(tileFactory);

		Set<NodeLabel> nodes = new HashSet<NodeLabel>();

		for (Node node : quadtree.getAllElements()) {
			double lat = ((Coordinate) node.getObject()).x;
			double lon = ((Coordinate) node.getObject()).y;
			nodes.add(new NodeLabel("", Color.CYAN, new GeoPosition(lat, lon)));
		}

		GeoPosition frankfurt = new GeoPosition(50, 7, 0, 8, 41, 0);

		mapViewer.setZoom(10);
		mapViewer.setAddressLocation(frankfurt);

		// Add interactions
		MouseInputListener mIL = new PanMouseInputListener(mapViewer);
		mapViewer.addMouseListener(mIL);
		mapViewer.addMouseMotionListener(mIL);
		mapViewer.addMouseListener(new CenterMapListener(mapViewer));
		mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(mapViewer));
		mapViewer.addKeyListener(new PanKeyListener(mapViewer));

		NodeLabelPainter<NodeLabel> nodeLabelPainter = new NodeLabelPainter<NodeLabel>();
		nodeLabelPainter.setWaypoints(nodes);
		nodeLabelPainter.setRenderer(new NodeLabelRenderer());

		mapViewer.setOverlayPainter(nodeLabelPainter);

		return mapViewer;
	}

}
