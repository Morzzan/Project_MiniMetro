package game_engine;

import java.util.ArrayList;
import java.util.List;

public class Lane {
	private GameMap on;
	private List<Train> trains = new ArrayList<Train>();
	private Station first, last;
	private int stationNumber = 0;

	public float quality(boolean onNormalWay) {
		float t;
		if (isCircular()) {
			t = trainsGoingWay(onNormalWay) * 2;
		} else {
			t = trains.size();
		}
		if (t == 0)
			t += 0.0000001;
		return stationNumber / t;
	}

	public void shorten(Station s) {
		Platform sPlat = s.getPlatform(this);
		if (s == first) {
			Platform firstPlat = sPlat.nextStationBy(sPlat.getTo());
			first = firstPlat.getOfStation();
			firstPlat.setFrom(null);
		} else if (s == last) {
			Platform lastPlat = sPlat.nextStationBy(sPlat.getFrom());
			last = lastPlat.getOfStation();
			lastPlat.setTo(null);
		} else {
			Platform f = sPlat.nextStationBy(sPlat.getFrom());
			Platform t = sPlat.nextStationBy(sPlat.getTo());
			new Section(f, t, this);
		}
		s.getPlatforms().remove(sPlat);
	}

	private float trainsGoingWay(boolean normal) {
		float nb = 0;
		for (Train t : trains) {
			if (t.isGoingNormalWay() == normal)
				nb++;
		}
		if (nb == 0)
			nb += 0.0000001;
		return nb;
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
			new Section(firstest, firstplat, this);
		}
		stationNumber++;
		first = s;
		on.networkChange();
	}

	public boolean isCircular() {
		return (first == last);
	}

	public void extendTail(Station s) {
		if (isEmpty())
			first = s;
		else {
			Platform latest = s.getPlatform(this);
			Platform lastplat = last.getPlatform(this);
			new Section(lastplat, latest, this);
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
