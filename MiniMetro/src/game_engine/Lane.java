package game_engine;

import java.util.ArrayList;
import java.util.List;

public class Lane {
	private GameMap on;
	private List<Train> trains = new ArrayList<Train>();
	private Station first, last;
	private int stationNumber = 0;

	public float quality() {
		return stationNumber / trains.size();
	}

	public Station getFirst() {
		return first;
	}

	public GameMap getOn() {
		return on;
	}

	public void extendHead(Station s) {
		if (isEmpty())
			last = s;
		else {
			Platform firstest = s.getPlatform(this);
			Platform firstplat = first.getPlatform(this);
			Section sec = new Section(firstest, firstplat, this);
			firstest.setTo(sec);
			firstplat.setFrom(sec);
		}
		stationNumber++;
		first = s;
		on.networkChange();
	}

	public void extendTail(Station s) {
		if (isEmpty())
			first = s;
		else {
			Platform latest = s.getPlatform(this);
			Platform lastplat = last.getPlatform(this);
			Section sec = new Section(lastplat, latest, this);
			lastplat.setTo(sec);
			latest.setFrom(sec);
		}
		stationNumber++;
		last = s;
		on.networkChange();
	}

	private boolean isEmpty() {
		return (first == null || last == null || stationNumber == 0);
	}

	public Lane(GameMap on) {
		this.on = on;
	}

	public List<Train> getTrains() {
		return trains;
	}
}
