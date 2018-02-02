package model.quadtree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.geotools.graph.structure.Node;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;

public class QuadtreeNode {

	private RoadNetworkQuadtree roadNetwork;
	private QuadtreeNode[] children;
	private Envelope area;
	private HashMap<Node, ArrayList<Integer>> data;
	private boolean isChild;
	private int level;
	private int size;
	
	/**
	 *	Default constructor
	 */
	public QuadtreeNode(){
		super();
	}
	

	/**
	 * Creates a childNode for a given parentNode
	 * 
	 * @param parent
	 *            parentNode of this node
	 * @param quadrant
	 *            represents the index of the divided envelope from the
	 *            parentNode
	 */
	QuadtreeNode(QuadtreeNode parent, int quadrant) {
		this.children = new QuadtreeNode[4];
		this.area = getQuadrant(parent.area, quadrant);
		this.level = parent.level++;
		this.size = 0;
		this.roadNetwork = parent.roadNetwork;
		this.data = getData(parent);
		this.isChild = true;
	}

	/**
	 * Creates the rootNode of a FeatureStoreImpl(QuadTree)
	 * 
	 * @param env
	 *            The area which is represented by the
	 *            FeatureStoreImpl(QuadTree)
	 * @param fS
	 *            FeatureStoreImpl
	 */
	QuadtreeNode(Envelope env, RoadNetworkQuadtree roadNetwork) {
		this.children = new QuadtreeNode[4];
		this.area = env;
		this.level = 0;
		this.size = 0;
		this.data = new HashMap<Node, ArrayList<Integer>>();
		this.roadNetwork = roadNetwork;
		this.isChild = true;
	}

	private Envelope getQuadrant(Envelope env, int quadrant) {

		Coordinate center = env.centre();
		double width = (env.getMaxX() - env.getMinX()) / 2.0;
		double height = (env.getMaxY() - env.getMinY()) / 2.0;
		Coordinate minPt = null;
		Coordinate maxPt = null;
		Envelope envReturn;
		

		if (quadrant == 0) {
			minPt = new Coordinate(center.x - width, center.y, center.z);
			maxPt = new Coordinate(center.x, center.y + height, center.z);
		} else if (quadrant == 1) {
			minPt = new Coordinate(center.x, center.y, center.z);
			maxPt = new Coordinate(center.x + width, center.y + height, center.z);
		} else if (quadrant == 2) {
			minPt = new Coordinate(center.x - width, center.y - height, center.z);
			maxPt = new Coordinate(center.x, center.y, center.z);
		} else if (quadrant == 3) {
			minPt = new Coordinate(center.x, center.y - height, center.z);
			maxPt = new Coordinate(center.x + width, center.y, center.z);
		} else {
			// TODO throw Exception
		}
		envReturn = new Envelope(minPt, maxPt);
		return envReturn;
	}

	private HashMap<Node, ArrayList<Integer>> getData(QuadtreeNode parent) {

		if (parent.data == null) {
			return null;
		}

		HashMap<Node, ArrayList<Integer>> mapReturn = new HashMap<Node, ArrayList<Integer>>();

		for (Node key : parent.data.keySet()) {
			ArrayList<Integer> list = parent.data.get(key);
			ArrayList<Integer> newList = new ArrayList<Integer>();
			for (int i : list) {
				Node roadNetworkNode = roadNetwork.getRoadNetworkNode(i);
				if (intersectRoadNetworkNode(roadNetworkNode)) {
					newList.add(i);
					size++;
				}
			}
			mapReturn.put(key, newList);
		}
		return mapReturn;
	}

	/**
	 * adds a Node to this QuadtreeNode
	 * 
	 * @param node
	 *            Node to add
	 * @param index
	 *            index of the feature in the data structure
	 */
	public void addRoadNetworkNode(Node node, int index) {
		ArrayList<Integer> list = data.get(node);

		if (list == null) {
			list = new ArrayList<Integer>();
			data.put(node, list);
		}
		list.add(index);
		size++;
	}

	/**
	 * checks whether the node is a child or not
	 * 
	 * @return true if it is a child, else false
	 */
	public boolean isChild() {
		return isChild;
	}

	/**
	 * checks if the node can add more elements or not
	 * 
	 * @return true if it is full, else false
	 */
	public boolean isFull() {
		return size >= roadNetwork.getMaxElementsPerNode();
	}

	/**
	 * Checks if the area of this node intersects the given feature
	 * 
	 * @param feature
	 *            feature to be checked for
	 * @return true if it intersects, else false
	 */
	public boolean intersectRoadNetworkNode(Node node) {
		
		Envelope wayPointEnvelope = new Envelope((Coordinate)node.getObject());
		return (area.contains(wayPointEnvelope) || area.intersects(wayPointEnvelope));
	}
	
	private boolean intersectRoadNetworkNode(Node node, Envelope env) {
		
		Envelope wayPointEnvelope = new Envelope((Coordinate)node.getObject());
		return (env.contains(wayPointEnvelope) || env.intersects(wayPointEnvelope));
	}

	/**
	 * Checks if the area of this node intersects the given envelope
	 * 
	 * @param env
	 *            envelope to be checked for
	 * @return true if it intersects, else false
	 */
	public boolean intersectFeature(Envelope env) {
		return (area.contains(env) || area.intersects(env));
	}

	public QuadtreeNode[] getChildren() {
		return children;
	}

	/**
	 * Splits the node in four children
	 */
	public void splitNode() {
		for (int i = 0; i < 4; i++) {
			children[i] = new QuadtreeNode(this, i);
		}

		this.data = null;
		this.isChild = false;
	}

	/**
	 * Returns a List with all Elements of the area in the given Envelope
	 * 
	 * @param env
	 *            envelope to be checked
	 * @return List with elements
	 */
	public List<Node> getAllElementsOfArea(Envelope env) {
		ArrayList<Node> newList = new ArrayList<Node>();
		for (Node key : data.keySet()) {
			ArrayList<Integer> list = data.get(key);
			for (int i : list) {
				Node node = roadNetwork.getRoadNetworkNode(i);
				if (intersectRoadNetworkNode(node, env)) {
					newList.add(roadNetwork.getRoadNetworkNode(i));
				}
			}
		}
		return newList;
	}

	/**
	 * Returns a List with all Elements of this node
	 * 
	 * @return List with elements
	 */
	public List<Node> getAllElementsOfNode() {
		ArrayList<Node> newList = new ArrayList<Node>();
		for (Node key : data.keySet()) {
			ArrayList<Integer> list = data.get(key);
			for (int i : list) {
				newList.add(roadNetwork.getRoadNetworkNode(i));
			}
		}
		return newList;
	}
	
	public int getLevel() {
		return level;
	}

	public Envelope getArea() {
		return this.area;
	}
}
