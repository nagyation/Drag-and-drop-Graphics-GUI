package gui;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Router extends JPanel implements MouseMotionListener, MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Point offset = new Point(0,0);
	public RoutingTable routingTable = null;
	private int routerImageWidth = 50;
	private int routerImageHeight = 50;
	public Point position = new Point(0,0);
	public Point cursorPosition = new Point(0,0); 
	public int index;
	public JLabel label = null;
	public JLabel list = null;
	private DrawPad drawPad = null;
	private BufferedImage router_image = null;
	public Rectangle routerImageRectangle = null;
	
	public Router(Point position, int index, DrawPad drawPad) {
		addMouseListener(this);
		addMouseMotionListener(this);
		this.setLayout(null);
		
		try {
			router_image = ImageIO.read(new File("/home/samir/eclipse-workspace/Routing/src/router_symbol.png"));
		} catch(Exception e) {
			e.printStackTrace();
		}
		this.position = new Point(position);
		this.index = index;

		label = new JLabel();
		//label.setText("router" + String.valueOf(index));
		label.setText("(" + String.valueOf(position.x) + ", " + String.valueOf(position.y) + ")");
		list = new JLabel("router" + String.valueOf(index) + " connected to" + "-");
		this.drawPad = drawPad;
		add(label);
		add(list);
		
		this.setBounds(position.x,position.y,50,70);
		label.setBounds(0, 60, 100, 10);
		list.setBounds(0, 80, 100, 10);
		
		this.drawPad.add(this);
		routerImageRectangle = new Rectangle(new Point(0,0),new Point(50,50));
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage (router_image.getScaledInstance(routerImageWidth, routerImageHeight, Image.SCALE_SMOOTH), 0, 0, this);
	}
	
	public void draw(Graphics g) {
		g.drawImage (router_image.getScaledInstance(routerImageWidth, routerImageHeight, Image.SCALE_SMOOTH), position.x, position.y, this);
		label.setBounds(position.x, position.y + 10, 100, 100);
		list.setBounds(position.x + 50, position.y + 60, 100, 100);
	}
	
	public void setPosition(Point p) {
		position.x = p.x;
		position.y = p.y;
		this.setBounds(position.x,position.y,50,70);
		list.setBounds(0, 60, 100, 10);
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent event) {
    	if(event.getButton() == 1) {
    		//left click
    		this.setBounds(position.x,position.y,100,100);
    	}
		//label.setText("<html>This is<br>a multi-line string");
	}

	@Override
	public void mousePressed(MouseEvent event) {
    	int x = event.getX();
    	int y = event.getY();
    	offset.set(x, y);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		list.setVisible(true);
		setBounds(position.x, position.y, 150,500);
		drawPad.currentRouterIndex = index;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		list.setVisible(false);
		setBounds(position.x, position.y, 50,70);
		drawPad.currentRouterIndex = -1;
	}

	@Override
	public void mouseDragged(MouseEvent event) {
    	Point position = new Point(drawPad.getMousePosition().x, drawPad.getMousePosition().y);
    	Point rp = new Point(position);
    	rp.add(-offset.x, -offset.y);
    	setPosition(rp);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
    	cursorPosition.set(x, y);
    	if (routerImageRectangle.isInRectangleArea(cursorPosition)) {
    		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    	}else {
    		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    	}
	}
}