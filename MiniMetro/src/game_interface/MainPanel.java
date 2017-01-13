package game_interface;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import game_engine.GameMap;
import game_engine.Lane;
import game_engine.Section;
import game_engine.Station;
import game_engine.Train;
import game_engine.Traveler;
import math.geom2d.Vector2D;

public class MainPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private GameMap gm;
	private int boxSize, boxHeight, boxWidth;
	private Vector2D center;
	private Station choice;

	public MainPanel(GameMap gm) {
		this.gm = gm;
		setBackground(Color.white);
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				double x=(e.getX()-getWidth()/2)/boxWidth;
				double y=(e.getY()-getHeight()/2)/boxHeight;
				Vector2D v= new Vector2D(x, y);
				for(Station s : gm.getStations()){
					Vector2D w=v.minus(s.getPos());
					if(w.norm()<=2){
						choice=s;
					}
				}
			}
		});
	}

	public Station getChoice() {
		return choice;
	}

	private void paintTrains(Graphics g) {
		for (Lane l : gm.getLanes()) {
			for (Train t : l.getTrains()) {
				paintTrain(t, g);
			}
		}
	}

	private void paintTrain(Train t, Graphics g) {
		Station to = t.getTo().getOfStation();
		Station from = (t.isGoingNormalWay()) ? t.getOn().getFrom().getOfStation() : t.getOn().getTo().getOfStation();
		Vector2D v = from.getPos().minus(to.getPos()).normalize();
		Vector2D pos = v.times(t.getDistance())
				.plus(to.getPos().plus(new Vector2D(gm.getMapSize() / 2 + 5, gm.getMapSize() / 2 + 5)));
		Vector2D trainCenter = new Vector2D(0, 0.5).times(boxSize);
		Vector2D result = new Vector2D(pos.getX() * boxWidth, pos.getY() * boxHeight).plus(trainCenter);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(boxSize / 2));
		g2.setColor(t.getTo().getOfLane().getCol());
		g2.drawRect(((int) result.getX()), ((int) result.getY()), boxSize, ((int) boxSize / 2));
		paintTravelers(g, t.getPassengers(), result);
	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		boxWidth = getWidth() / (gm.getMapSize() + 10);
		boxHeight = getHeight() / (gm.getMapSize() + 10);
		boxSize = Math.min(boxWidth, boxHeight);
		center = new Vector2D(getWidth() / 2, getHeight() / 2);
		paintHUDs(g);
		paintNetwork(g);
		paintTrains(g);
		paintStations(g);
	}

	private void paintHUDs(Graphics g) {
		g.setColor(Color.black);
		g.drawString("Points : " + gm.getScore(), 10, 20);
	}

	private void paintNetwork(Graphics g) {

		for (Section s : gm.getSections()) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(s.getFrom().getOfLane().getCol());
			g2.setStroke(new BasicStroke(boxSize / 10));
			g2.draw(new Line2D.Float(
					(int) ((s.getFrom().getOfStation().getPos().getX()) * boxWidth + 0.5 * boxSize + center.getX()),
					(int) ((s.getFrom().getOfStation().getPos().getY()) * boxHeight + 0.5 * boxSize + center.getY()),
					(int) ((s.getTo().getOfStation().getPos().getX()) * boxWidth + 0.5 * boxSize + center.getX()),
					(int) ((s.getTo().getOfStation().getPos().getY()) * boxHeight + 0.5 * boxSize + center.getY())));
		}
	}

	private void paintStations(Graphics g) {
		BufferedImage image = null;
		for (Station s : gm.getStations()) {
			try {
				File f = new File("Shapes/" + s.getType() + ".png");
				image = ImageIO.read(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Vector2D put = new Vector2D(s.getPos().getX() * boxWidth + center.getX(),
					s.getPos().getY() * boxHeight + center.getY());
			g.drawImage(image, (int) put.getX(), (int) put.getY(), boxSize, boxSize, null);
			put = put.plus(new Vector2D(boxSize, 0));
			paintTravelers(g, s.getWaiters(), put);
		}

	}

	public void paintTravelers(Graphics g, List<Traveler> l, Vector2D origin) {
		BufferedImage image = null;
		int i = 0;
		for (Traveler t : l) {
			try {
				File f = new File("Shapes/" + t.getType() + ".png");
				image = ImageIO.read(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(image, (int) origin.getX(), (int) origin.getY(), (int) boxSize / 4, (int) boxSize / 4, null);
			if (i % 2 == 0) {
				origin = origin.plus(new Vector2D(0, boxSize / 3));
			} else {
				origin = origin.plus(new Vector2D(boxSize / 3, -boxSize / 3));
			}
			i++;
		}
	}

	
}
