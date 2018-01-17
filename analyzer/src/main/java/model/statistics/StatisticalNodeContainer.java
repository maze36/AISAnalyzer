package model.statistics;

import java.util.ArrayList;

import com.vividsolutions.jts.geom.Coordinate;

/**
 * Stores a collection of {@link StatisticalNode}.
 * 
 * @author msteidel
 *
 */
public class StatisticalNodeContainer {

	private ArrayList<StatisticalNode> statNodes;

	public StatisticalNodeContainer() {
		this.statNodes = new ArrayList<StatisticalNode>();
	}

	public StatisticalNode getNode(int index) {
		return this.statNodes.get(index);
	}

	public boolean addNode(StatisticalNode statisticalNode) {
		return this.statNodes.add(statisticalNode);
	}

	public StatisticalNode findNodeByCoordinate(Coordinate coordToSearchFor) {
		for (StatisticalNode node : this.statNodes) {
			if (node.getLat() == coordToSearchFor.x && node.getLon() == coordToSearchFor.y) {
				return node;
			}
		}

		return null;
	}

	public StatisticalNode findNodeById(int id) {
		for (StatisticalNode node : this.statNodes) {
			if (node.getId() == id) {
				return node;
			}
		}

		return null;
	}

	public boolean isEmpty() {
		return this.statNodes.isEmpty();
	}

}
