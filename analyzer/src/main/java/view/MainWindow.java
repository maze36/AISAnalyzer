package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.geotools.graph.structure.Graph;
import org.geotools.graph.structure.Node;

import com.vividsolutions.jts.geom.Envelope;

import controller.input.ShapefileReader;
import model.quadtree.RoadNetworkQuadtree;


public class MainWindow extends JFrame {

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
	
	public MainWindow() {
		
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
		this.mainLabel.setPreferredSize(new Dimension((int)mainLabel.getPreferredSize().getWidth(), (int)(height * 0.2)));
		this.menu = new JList<String>();
		this.menu.setListData(new String[]{"SourceFiles", "Map"});
		this.menu.setFont(new Font("Verdana", Font.BOLD, 20));
		this.menu.setPreferredSize(new Dimension((int)(width * 0.2), height));
		Border loweredBorder = BorderFactory.createLoweredBevelBorder();
		this.menu.setBorder(loweredBorder);
		this.menu.setBackground(this.getBackground());
		
		RoadNetworkQuadtree quadtree = new RoadNetworkQuadtree(new Envelope(0.0, 100.0, 0.0, 100.0), 100, 100);
		Graph graph = ShapefileReader.getRTM("C:/Users/Markus/Desktop/RTM_MWotS_jun14/RTM_MWotS_jun14_clean.shp");

		@SuppressWarnings("unchecked")
		Iterator<Node> iterator = graph.getNodes().iterator();

		while (iterator.hasNext()) {
			quadtree.insert(iterator.next());
		}
		
		if(quadtree.getAllElements().isEmpty()) {
			System.out.println("Empty");
		}
		
		this.getContentPane().add(worldMap.getWorldMap(quadtree));
		
		north.add(mainLabel);
		west.add(menu);
		center.add(worldMap.getWorldMap(quadtree));
		
		fullPanel.setLayout(new BorderLayout());
//		fullPanel.add(north, BorderLayout.NORTH);
//		fullPanel.add(west, BorderLayout.WEST);
		
		fullPanel.add(center, BorderLayout.CENTER);
//		this.add(fullPanel);
		
		
		this.setSize(width, height);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	public static void main(String[] args) {
		MainWindow w = new MainWindow();
	}
	
	public static void startUI() {
		
	}
}
