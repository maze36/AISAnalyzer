package model.quadtree.jadeTree;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.graph.structure.Node;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;

import controller.util.GeoCalculations;
import model.jadeNode.JadeNode;
import model.units.LengthUnit;
import model.units.enums.MathematicalOperation;

public class JadeQuadtree {

	private JadeQuadtreeNode root;
	private ArrayList<JadeNode> store;
	private int maxLevel;
	private int maxElementsPerNode;

	public JadeQuadtree() {
		super();
	}

	/**
	 * 
	 * @param env
	 *            area that is represented by the FeatureStoreImpl(Quadtree)
	 * @param maxLevel
	 *            represents the maximum depth of the tree
	 * @param maxElementsPerNode
	 *            represents the maximum of elements in one Node before there
	 *            will be a splitting
	 */
	public JadeQuadtree(Envelope env, int maxLevel, int maxElementsPerNode) {
		this.root = new JadeQuadtreeNode(env, this);
		this.maxLevel = maxLevel;
		this.maxElementsPerNode = maxElementsPerNode;
		this.store = new ArrayList<JadeNode>();
	}

	/**
	 * 
	 * @param node
	 *            will be inserted into roadNetworkQuadtree
	 */
	public void insert(JadeNode node) {

		if (store.contains(node) || !root.intersectRoadNetworkNode(node)) {
			return;
		}
		store.add(node);
		traverseTree(root, node);
	}

	public boolean delete(Node node) {
		// Bei Bedarf implementieren
		return false;
	}

	/**
	 * finds the nearest node to the given coordinate in the road network
	 * 
	 * @param vesselPosition
	 *            {@link Coordinate} of vessel in which proximity the next node
	 *            should be found
	 * @return nearest node to given coordinate
	 */
	public JadeNode findNearestJadeNode(Coordinate vesselPosition) {

		Envelope env = new Envelope(vesselPosition);

		List<JadeNode> list = new ArrayList<JadeNode>();
		list = deepSearchSingleNode(env, root, list);

		JadeNode result = null;

		if (!list.isEmpty()) {
			result = list.get(0);

			Iterator<JadeNode> iterator = list.iterator();
			double currentNearestDistanceToVesselLocation = GeoCalculations.calculateDistance(result.getPosition(),
					vesselPosition, LengthUnit.NAUTICALMILES);

			while (iterator.hasNext()) {

				JadeNode node = (JadeNode) iterator.next();

				double distanceOfVesselLocationToRoadNetworkNode = GeoCalculations.calculateDistance(node.getPosition(),
						vesselPosition, LengthUnit.NAUTICALMILES);

				if (currentNearestDistanceToVesselLocation > distanceOfVesselLocationToRoadNetworkNode) {
					result = node;
					currentNearestDistanceToVesselLocation = distanceOfVesselLocationToRoadNetworkNode;
				}
			}
		}
		return result;
	}

	/**
	 * 
	 * finds the nearest node to the given coordinate in the road network that
	 * is lying in the travel direction of the vessel
	 * 
	 * @param vesselPosition
	 *            {@link Coordinate} of vessel in which proximity the next node
	 *            should be found
	 * @param cog
	 *            course of the vessel in which proximity the next node should
	 *            be found, number in degree
	 * @param heightOfFunnel
	 *            number in nautical miles
	 * @param widthOfFunnel
	 *            number in nautical miles
	 * @return nearest node to vessel position in its direction
	 */
	public JadeNode findNearestJadeNodeInDirection(Coordinate vesselPosition, double cog, double heightOfFunnel,
			double widthOfFunnel) {

		GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();

		double opposite_leg = widthOfFunnel / 2.0;
		double hypotenuse = Math.sqrt(((heightOfFunnel * heightOfFunnel) + (opposite_leg * opposite_leg)));
		double alpha = Math.asin((opposite_leg / hypotenuse));
		double angleToLeftLimes = GeoCalculations.addOrSubtractAzimuth(cog, alpha, MathematicalOperation.SUBTRACTING);
		double angleToRightLimes = GeoCalculations.addOrSubtractAzimuth(cog, alpha, MathematicalOperation.ADDING);

		Point2D leftPointOfFunnel = GeoCalculations.calculateNewPosition(vesselPosition, hypotenuse, angleToLeftLimes);
		Point2D rightPointOfFunnel = GeoCalculations.calculateNewPosition(vesselPosition, hypotenuse,
				angleToRightLimes);

		Coordinate leftPoint = new Coordinate(leftPointOfFunnel.getX(), leftPointOfFunnel.getY());
		Coordinate rightPoint = new Coordinate(rightPointOfFunnel.getX(), rightPointOfFunnel.getY());

		Coordinate[] coords = new Coordinate[] { vesselPosition, leftPoint, rightPoint, vesselPosition };
		Geometry geo = geometryFactory.createPolygon(coords);

		List<JadeNode> list = new ArrayList<JadeNode>();
		list = deepSearch(geo.getEnvelopeInternal(), root, list);

		JadeNode result = null;

		if (!list.isEmpty()) {
			result = list.get(0);

			Iterator<JadeNode> iterator = list.iterator();
			double currentNearestDistanceToVesselLocation = GeoCalculations.calculateDistance(result.getPosition(),
					vesselPosition, LengthUnit.NAUTICALMILES);

			while (iterator.hasNext()) {

				JadeNode node = (JadeNode) iterator.next();

				double distanceOfVesselLocationToRoadNetworkNode = GeoCalculations.calculateDistance(node.getPosition(),
						vesselPosition, LengthUnit.NAUTICALMILES);

				if (currentNearestDistanceToVesselLocation > distanceOfVesselLocationToRoadNetworkNode) {
					result = node;
					currentNearestDistanceToVesselLocation = distanceOfVesselLocationToRoadNetworkNode;
				}
			}
		}
		return result;
	}

	/**
	 * 
	 * finds the nearest nodes to the given coordinate in the road network that
	 * is lying in the travel direction of the vessel
	 * 
	 * @param vesselPosition
	 *            {@link Coordinate} of vessel in which proximity defined by a
	 *            funnel the nearest nodes should be found
	 * @param cog
	 *            course of the vessel in which proximity the next node should
	 *            be found, number in degree
	 * @param heightOfFunnel
	 *            number in nautical miles
	 * @param widthOfFunnel
	 *            number in nautical miles
	 * @return nearest nodes to vessel position in its direction
	 */
	public ArrayList<JadeNode> findNearestJadeNodesInDirection(Coordinate vesselPosition, double cog,
			double heightOfFunnel, double widthOfFunnel) {

		GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();

		double opposite_leg = widthOfFunnel / 2.0;
		double hypotenuse = Math.sqrt(((heightOfFunnel * heightOfFunnel) + (opposite_leg * opposite_leg)));
		double alpha = Math.asin((opposite_leg / hypotenuse));
		double angleToLeftLimes = GeoCalculations.addOrSubtractAzimuth(cog, alpha, MathematicalOperation.SUBTRACTING);
		double angleToRightLimes = GeoCalculations.addOrSubtractAzimuth(cog, alpha, MathematicalOperation.ADDING);

		Point2D leftPointOfFunnel = GeoCalculations.calculateNewPosition(vesselPosition, hypotenuse, angleToLeftLimes);
		Point2D rightPointOfFunnel = GeoCalculations.calculateNewPosition(vesselPosition, hypotenuse,
				angleToRightLimes);

		Coordinate leftPoint = new Coordinate(leftPointOfFunnel.getX(), leftPointOfFunnel.getY());
		Coordinate rightPoint = new Coordinate(rightPointOfFunnel.getX(), rightPointOfFunnel.getY());

		Coordinate[] coords = new Coordinate[] { vesselPosition, leftPoint, rightPoint, vesselPosition };
		Geometry geo = geometryFactory.createPolygon(coords);

		List<JadeNode> list = new ArrayList<JadeNode>();
		list = deepSearch(geo.getEnvelopeInternal(), root, list);

		return (ArrayList<JadeNode>) list;
	}

	/**
	 * 
	 * finds the nearest nodes to the given coordinate in the road network that
	 * is lying in the bounding box around of the vessel
	 * 
	 * @param vesselPosition
	 *            {@link Coordinate} of vessel in which proximity the nearest
	 *            node should be found
	 * @param diameter
	 *            width of bounding box to check for nodes
	 * @return list of nodes in a boundingbox around the vessel
	 */
	public ArrayList<JadeNode> findNearestJadeNodesInRange(Coordinate vesselPosition, double diameter) {

		double hypotenuse = (Math.sin(Math.toRadians(45.0)) * diameter) / 2.0;

		Point2D minPoint = GeoCalculations.calculateNewPosition(vesselPosition, hypotenuse, 225.0);
		Point2D maxPoint = GeoCalculations.calculateNewPosition(vesselPosition, hypotenuse, 45.0);

		Coordinate leftLowerCorner = new Coordinate(minPoint.getX(), minPoint.getY());
		Coordinate rightUpperCorner = new Coordinate(maxPoint.getX(), maxPoint.getY());

		Envelope boundingBox = new Envelope(leftLowerCorner, rightUpperCorner);

		List<JadeNode> list = new ArrayList<JadeNode>();
		list = deepSearch(boundingBox, root, list);

		return (ArrayList<JadeNode>) list;
	}

	public ArrayList<JadeNode> getAllElements() {

		List<JadeNode> elements = new ArrayList<JadeNode>();
		elements = deepSearch(root.getArea(), root, elements);
		return (ArrayList<JadeNode>) elements;
	}

	public int getMaxElementsPerNode() {
		return this.maxElementsPerNode;
	}

	public int getMaxLevel() {
		return this.maxLevel;
	}

	public JadeNode getRoadNetworkNode(int i) {
		return store.get(i);
	}

	private void traverseTree(JadeQuadtreeNode node, JadeNode roadNetworkNode) {
		if (!node.intersectRoadNetworkNode(roadNetworkNode)) {
			return;
		}
		if (node.isChild()) {
			node.addRoadNetworkNode(roadNetworkNode, store.size() - 1);
			if (node.isFull()) {
				if (node.getLevel() < maxLevel)
					node.splitNode();
			}
		} else {
			for (JadeQuadtreeNode child : node.getChildren()) {
				traverseTree(child, roadNetworkNode);
			}
		}
	}

	private List<JadeNode> deepSearch(Envelope env, JadeQuadtreeNode node, List<JadeNode> list) {
		if (!node.intersectFeature(env)) {
			return list;
		}
		if (node.isChild()) {
			list.addAll(node.getAllElementsOfArea(env));
		} else {
			for (JadeQuadtreeNode child : node.getChildren()) {
				deepSearch(env, child, list);
			}
		}
		return list;
	}

	private List<JadeNode> deepSearchSingleNode(Envelope env, JadeQuadtreeNode node, List<JadeNode> list) {
		if (!node.intersectFeature(env)) {
			return list;
		}
		if (node.isChild()) {
			list.addAll(node.getAllElementsOfNode());
		} else {
			for (JadeQuadtreeNode child : node.getChildren()) {
				deepSearchSingleNode(env, child, list);
			}
		}
		return list;
	}

}
