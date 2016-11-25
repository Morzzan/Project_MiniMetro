package game_engine;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Train implements Observer {
	private double distance;
	private Platform to;
	private boolean inStation;
	private Section on;
	private int velocity;
	private List<Traveler> passengers = new LinkedList<Traveler>();
	private int capacity = 6;

	@Override
	public void update(Observable arg0, Object arg1) {
		if (!inStation) {
			distance = distance - velocity;
			if (distance <= 0) {
				arriveInStation();
			}
		}
	}

	public Section getOn() {
		return on;
	}

	public boolean fromHeadToTail() {
		return (on != null && on.getTo() == to);
	}

	public Train(Lane onLane, Clock c) {
		inStation = false;
		onLane.getTrains().add(this);
		to = onLane.getFirst().getPlatform(onLane);
		distance = 0;
		velocity = 2;
		c.addObserver(this);
		onLane.getOn().networkChange();
	}

	private void arriveInStation() {
		Platform thisPlat = to;
		inStation = true;
		unloadTravelers();
		changeDest();
		thisPlat.getOfStation().trainArrival(this);
		leaveStation();

	}

	private void unloadTravelers() {
		List<Traveler> temp = passengers;
		passengers = new LinkedList<Traveler>();
		for (Traveler t : temp) {
			if (!t.isArrivedInStation(to.getOfStation())) {
				passengers.add(t);
			}
		}
	}

	private void changeDest() {
		on = to.nextSection(on);
		to = to.nextStationBy(on);
	}

	private void leaveStation() {
		distance = on.getLength();
		inStation = false;
	}

	public boolean isFull() {
		return (passengers.size() >= capacity);
	}

	public void load(Traveler t) {
		passengers.add(t);
	}
}
