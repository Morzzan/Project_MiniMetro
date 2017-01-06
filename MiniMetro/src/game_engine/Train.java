package game_engine;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Train implements Observer {
	private double distance;
	private Platform to;
	private int exchanging = 0;
	private Section on;
	private double velocity = 0.1;
	private List<Traveler> passengers = new LinkedList<Traveler>();
	private int capacity = 6;

	@Override
	public void update(Observable arg0, Object arg1) {
		if (exchanging <= 0) {
			distance = distance - velocity;
			if (distance <= 0) {
				arriveInStation();
			}
		} else {
			exchanging--;
		}
	}

	public double getDistance() {
		return distance;
	}

	public Platform getTo() {
		return to;
	}

	public Section getOn() {
		return on;
	}

	public boolean isGoingNormalWay() {
		boolean way = false;
		if (on != null && to != null) {
			if (on.getTo() == to)
				way = true;
		}
		return way;
	}

	public boolean fromHeadToTail() {
		return (on != null && on.getTo() == to);
	}

	public Train(Lane onLane) {
		onLane.getTrains().add(this);
		to = onLane.getFirst().getPlatform(onLane);
		distance = 0;
		to.getOfLane().getOn().getCl().addObserver(this);
		onLane.getOn().networkChange();
	}

	private void arriveInStation() {
		Platform thisPlat = to;
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
				delayLeaving(1);
			}
		}
	}

	public List<Traveler> getPassengers() {
		return passengers;
	}

	private void delayLeaving(int d) {
		exchanging += d * 5;
	}

	private void changeDest() {
		on = to.nextSection(on);
		to = to.nextStationBy(on);
	}

	private void leaveStation() {
		distance = on.getLength();
	}

	public boolean isFull() {
		return (passengers.size() >= capacity);
	}

	public void load(Traveler t) {
		passengers.add(t);
		delayLeaving(1);
	}
}
