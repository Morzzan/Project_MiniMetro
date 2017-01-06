package game_engine;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import math.geom2d.Vector2D;

public class Station implements Observer {
	private MyShape type;
	private Vector2D pos;
	private GameMap on;
	private int nextPop = 50;
	private List<Traveler> waiters = new LinkedList<Traveler>();
	private List<Platform> platforms = new ArrayList<Platform>();
	private int overLoad=0;
	private static final int CAPACITY=10, DELAY=50;

	public Platform getPlatform(Lane l) {
		Platform plat = null;
		for (Platform p : platforms) {
			if (p.getOfLane() == l)
				plat = p;
		}
		if (plat == null)
			plat = createPlatform(l);
		return plat;
	}

	public List<Platform> getPlatforms() {
		return platforms;
	}

	public Station(MyShape type, Vector2D pos, GameMap gm) {
		this.type = type;
		this.pos = pos;
		on = gm;
		on.getCl().addObserver(this);

	}

	public MyShape getType() {
		return type;
	}

	public void setType(MyShape type) {
		this.type = type;
	}

	public Vector2D getPos() {
		return pos;
	}

	public List<Traveler> getWaiters() {
		return waiters;
	}

	public void setPos(Vector2D pos) {
		this.pos = pos;
	}

	public double getDistance(Station s2) {
		Vector2D section = pos.minus(s2.pos);
		return section.norm();
	}

	public void trainArrival(Train train) {
		System.out.println(waiters.size() + " at " + type);
		List<Traveler> temp = waiters;
		waiters = new LinkedList<Traveler>();
		for (Traveler trav : temp) {
			boolean loaded = false;
			if (!train.isFull()) {
				loaded = trav.GetOnTo(train);
			}
			if (!loaded)
				waiters.add(trav);
		}
		System.out.println(waiters.size() + " at " + type);
	}

	public void haveWait(Traveler t) {
		if (t.getType() != type) {
			waiters.add(t);
			t.setDest(this);
			t.findRoute();
		} else
			t.delete();
	}

	public Platform createPlatform(Lane lane) {
		Platform p = new Platform(this, lane);
		platforms.add(p);
		return p;
	}

	public void update(Observable o, Object arg) {
		if (nextPop <= 0) {
			new Traveler(randomType(), this, on);
			Random r = new Random();
			nextPop = 100 + r.nextInt(100);

		} else
			nextPop--;
		if(waiters.size()>CAPACITY){
			overLoad++;
			if (overLoad>=DELAY){
				on.gameOver();
			}
		}
		else if (overLoad>=0){
			overLoad--;
		}
	}

	private MyShape randomType() {
		Random r = new Random();
		int rdi = r.nextInt(on.getStations().size()) + 1;
		int i = 0;
		do {
			rdi -= on.getNbStationType()[i];
			i++;
		} while (rdi > 0);
		return MyShape.values()[i - 1];
	}
}
