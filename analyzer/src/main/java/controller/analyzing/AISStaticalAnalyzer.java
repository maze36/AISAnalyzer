package controller.analyzing;

import org.geotools.graph.structure.Node;

import com.vividsolutions.jts.geom.Coordinate;

import model.ais.AISMessage;
import model.quadtree.RoadNetworkQuadtree;
import model.statistics.StatisticalNode;
import model.statistics.StatisticalNodeContainer;
import model.track.Track;
import model.vessel.Vessel;
import model.vessel.VesselContainer;

/**
 * This class provides methods which are augmenting nodes of a road network with
 * additional statistical information.
 * 
 * @author msteidel
 *
 */
public class AISStaticalAnalyzer {

	public StatisticalNodeContainer augmentNodes(RoadNetworkQuadtree roadNetwork, VesselContainer vesselContainer) {
		// First step: find nearest node to ais

		StatisticalNodeContainer nodeContainer = new StatisticalNodeContainer();

		for (Vessel vessel : vesselContainer.getList()) {
			for (Track track : vessel.getTracks()) {
				for (AISMessage message : track.getAisMessages()) {
					Node nearestNode = findNearestNode(roadNetwork, message);
					if (nearestNode != null) {
						addNodeToContainer(nearestNode, message, nodeContainer, vessel);
					}
				}
			}
		}

		return nodeContainer;

	}

	private void addNodeToContainer(Node nearestNode, AISMessage message, StatisticalNodeContainer nodeContainer,
			Vessel vessel) {
		Coordinate coordNearestNode = (Coordinate) nearestNode.getObject();
		double lat = coordNearestNode.x;
		double lon = coordNearestNode.y;

		if (nodeContainer.isEmpty()) {
			StatisticalNode firstStatNode = new StatisticalNode(lat, lon);
			firstStatNode.updateNode(message, vessel);
			nodeContainer.addNode(firstStatNode);
		} else {
			StatisticalNode nodeToUpdate = nodeContainer.findNodeByCoordinate(coordNearestNode);
			if (nodeToUpdate == null) {
				nodeToUpdate = new StatisticalNode(lat, lon);
				nodeToUpdate.updateNode(message, vessel);
				nodeContainer.addNode(nodeToUpdate);
			} else {
				nodeToUpdate.updateNode(message, vessel);
			}
		}

	}

	private Node findNearestNode(RoadNetworkQuadtree roadNetwork, AISMessage message) {
		Coordinate vesselPosition = new Coordinate(message.getLat(), message.getLon());
		double cog = message.getCog();
		return roadNetwork.findNearestRoadNetworkNodeInDirection(vesselPosition, cog, 2, 2);
	}
}
