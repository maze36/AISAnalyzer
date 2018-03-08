package model.jadeNode;

import com.vividsolutions.jts.geom.Coordinate;

public class JadeNode {

	private Coordinate position;

	private JadeNodeType jadeNodeType;

	private String name;

	public JadeNode(Coordinate position, JadeNodeType jadeNodeType, String name) {
		this.position = position;
		this.jadeNodeType = jadeNodeType;
		this.name = name;
	}

	public Coordinate getPosition() {
		return position;
	}

	public void setPosition(Coordinate position) {
		this.position = position;
	}

	public JadeNodeType getJadeNodeType() {
		return jadeNodeType;
	}

	public void setJadeNodeType(JadeNodeType jadeNodeType) {
		this.jadeNodeType = jadeNodeType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
