package game_engine;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import math.geom2d.Vector2D;

public class GameMap extends Observable implements Observer{
	private List<Station> stations = new LinkedList<Station>();
	private List<Lane> lanes = new ArrayList<Lane>();
	private List<Section> sections = new LinkedList<Section>();
	private Clock cl = new Clock();
	private int points = 0;
	private int[] nbStationType = new int[MyShape.values().length];
	private int mapSize = 10;
	private final static int[] STATION_RATIO = { 50, 25, 15, 1, 1, 1, 1, 1, 1, 1, 3 };
	private final static int WEEK_TIME = 1000;
	
	public void gameOver(){
		cl.stop();
	}

	public GameMap() {
		Thread t = new Thread(cl);
		t.start();
		lanes.add(new Lane(this, Color.red));
		lanes.add(new Lane(this, Color.blue));
		lanes.add(new Lane(this, Color.green));
		addStation(MyShape.triangle);
		addStation(MyShape.circle);
		addStation(MyShape.square);
		cl.addObserver(this);
	}

	public Clock getCl() {
		return cl;
	}

	public List<Section> getSections() {
		return sections;
	}

	public int[] getNbStationType() {
		return nbStationType;
	}

	public int getMapSize() {
		return mapSize;
	}

	public void scorePoint() {
		points++;
	}

	private Vector2D newStationRandomPos() {
		Random r = new Random();
		Vector2D v;
		boolean good;
		do {
			good = true;
			v = new Vector2D(r.nextInt(mapSize) - mapSize / 2, r.nextInt(mapSize) - mapSize / 2);
			for (Station s : stations) {
				if (v.minus(s.getPos()).norm() < 3) {
					good = false;
				}
			}
		} while (!good);
		return v;
	}

	public void addStation(MyShape sh) {
		Station s = new Station(sh, newStationRandomPos(), this);
		stations.add(s);
		nbStationType[s.getType().ordinal()]++;
	}

	private void randomStation() {
		int i = 0;
		boolean done = false;
		while (done == false && i < nbStationType.length) {
			if (nbStationType[i] * 100 / stations.size() < STATION_RATIO[i]) {
				done = true;
			} else {
				i++;
			}
		}
		addStation(MyShape.values()[i]);
	}

	public void networkChange() {
		this.setChanged();
		notifyObservers();
	}

	public List<Station> getStations() {
		return stations;
	}

	public int getScore() {
		return points;
	}

	public List<Lane> getLanes() {
		return lanes;
	}

	public Lane getLane(int index) {
		return lanes.get(index);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof Integer) {
			int count = ((Integer) arg).intValue();
			int i = count % WEEK_TIME;
			if (i == 0) {
				// fin de semaine
				mapSize += 2;
			}
			int j = count % (WEEK_TIME / 3);
			if (j == 0) {
				// ajouter station
				randomStation();
			}
		}
	}
}
