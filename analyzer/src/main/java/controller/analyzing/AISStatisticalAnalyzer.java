package controller.analyzing;

import com.vividsolutions.jts.geom.Coordinate;

import model.ais.AISMessage;
import model.port.PortContainer;
import model.quadtree.newTree.Quadtree;
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
public class AISStatisticalAnalyzer {

	public StatisticalNodeContainer augmentNodes(Quadtree roadNetwork, VesselContainer vesselContainer,
			PortContainer portContainer) {
		// First step: find nearest node to ais

		System.out.println("Starting to augment nodes");

		StatisticalNodeContainer nodeContainer = new StatisticalNodeContainer();

		for (Vessel vessel : vesselContainer.getList()) {
			for (Track track : vessel.getTracks()) {
				for (AISMessage message : track.getAisMessages()) {
					Coordinate nearestNode = findNearestNode(roadNetwork, message);
					if (nearestNode != null) {
						addNodeToContainer(nearestNode, message, nodeContainer, vessel);
					}
				}
			}
		}

		System.out.println("Finished augmenting nodes");

		return nodeContainer;

	}

	public static void addNodeToContainer(Coordinate nearestNode, AISMessage message,
			StatisticalNodeContainer nodeContainer, Vessel vessel) {
		Coordinate coordNearestNode = nearestNode;
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

	public static Coordinate findNearestNode(Quadtree roadNetwork, AISMessage message) {

		double lat = message.getLat();
		double lon = message.getLon();

		if (!(lat > 90 || lat < -90) && !(lon > 180 || lon < -180)) {
			Coordinate vesselPosition = new Coordinate(message.getLat(), message.getLon());
			double cog = message.getCog();
			return roadNetwork.findNearestRoadNetworkNodeInDirection(vesselPosition, cog, 2, 2.5);
		}
		return null;

	}
}
