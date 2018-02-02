package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.Border;

import model.quadtree.RoadNetworkQuadtree;

public class MapWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3420834668117110524L;

	private WorldMap worldMap;
	private JPanel fullPanel;
	private Font font;
	private JLabel mainLabel;
	private JList<String> menu;
	private JPanel north;
	private JPanel west;
	private JPanel center;
	private JPanel east;
	private int width;
	private int height;

	public MapWindow(RoadNetworkQuadtree quadtree) {

		super("MTCAS");

		this.width = 1800;
		this.height = 1000;
		this.font = new Font("Verdana", Font.BOLD, 40);
		this.worldMap = new WorldMap();
		this.fullPanel = new JPanel();
		this.north = new JPanel();
		this.west = new JPanel();
		this.center = new JPanel();
		this.east = new JPanel();
		this.mainLabel = new JLabel("AIS Analyzer");
		this.mainLabel.setFont(font);
		this.mainLabel
				.setPreferredSize(new Dimension((int) mainLabel.getPreferredSize().getWidth(), (int) (height * 0.2)));
		this.menu = new JList<String>();
		this.menu.setListData(new String[] { "SourceFiles", "Map" });
		this.menu.setFont(new Font("Verdana", Font.BOLD, 20));
		this.menu.setPreferredSize(new Dimension((int) (width * 0.2), height));
		Border loweredBorder = BorderFactory.createLoweredBevelBorder();
		this.menu.setBorder(loweredBorder);
		this.menu.setBackground(this.getBackground());

		this.getContentPane().add(worldMap.getWorldMap(quadtree));

		north.add(mainLabel);
		west.add(menu);
		center.add(worldMap.getWorldMap(quadtree));

		fullPanel.setLayout(new BorderLayout());

		fullPanel.add(center, BorderLayout.CENTER);

		this.setSize(width, height);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

}
