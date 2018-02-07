package view;

import java.awt.Color;

import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

public class NodeLabel extends DefaultWaypoint {

	private final String label;
	private final Color color;

	/**
	 * @param label the text
	 * @param color the color
	 * @param coord the coordinate
	 */
	public NodeLabel(String label, Color color, GeoPosition coord)
	{
		super(coord);
		this.label = label;
		this.color = color;
	}

	/**
	 * @return the label text
	 */
	public String getLabel()
	{
		return label;
	}

	/**
	 * @return the color
	 */
	public Color getColor()
	{
		return color;
	}
}
