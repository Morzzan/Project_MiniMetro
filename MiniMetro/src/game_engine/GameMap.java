package game_engine;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import math.geom2d.Vector2D;

public class GameMap extends Observable implements Observer {
	private List<Station> stations = new LinkedList<Station>();
	private List<Lane> lanes = new ArrayList<Lane>();
	private List<Section> sections = new LinkedList<Section>();
	private Clock cl = new Clock();
	private int points = 0;
	private int[] nbStationType = new int[MyShape.values().length];
	private int mapSize = 10;

	public GameMap() {
		lanes.add(new Lane(this, Color.red));
		lanes.add(new Lane(this, Color.blue));
		lanes.add(new Lane(this, Color.green));
		addStation(MyShape.triangle);
		addStation(MyShape.circle);
		addStation(MyShape.square);
		addStation(MyShape.circle);
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
			v = new Vector2D(r.nextInt(mapSize), r.nextInt(mapSize));
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

	private MyShape randomStation() {

		return null;
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
		if(arg instanceof Integer){
			int i=(int)arg;
			i=i%500;
			if(i==0){
				// fin de semaine
				mapSize++;
			}
		}
	}
}
