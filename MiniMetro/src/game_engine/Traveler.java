package game_engine;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Traveler implements Observer {
	private GameMap on;
	private Shape type;
	private Station dest;
	private static final int TRAIN_CHANGE = 40, MAX_HOPS = 20, SPEED_TOLERANCE = 10;
	private List<Route> routes = new LinkedList<Route>();

	private class Route {
		private Station dest;
		private Section bySection;
		private float speed;

		Route(Station dest, Section bySection, float speed) {
			this.dest = dest;
			this.bySection = bySection;
			this.speed = speed;
		}
	}

	public Traveler(Shape type, Station in, GameMap jeu) {
		on = jeu;
		on.addObserver(this);
		this.type = type;
		in.haveWait(this);
	}

	public void setDest(Station dest) {
		this.dest = dest;
	}

	public boolean GetOnTo(Train t) {
		dest = null;
		boolean gone = false;
		for (Route r : routes) {
			if (t.getOn() == r.bySection) {
				dest = r.dest;
				gone = true;
			}
		}
		if (dest != null) {
			t.load(this);
			routes.clear();
		}
		return gone;
	}

	public void findRoute() {
		routes.clear();
		digRoute(dest, null, null, MAX_HOPS, 0);
		float minSpeed;
		if (!routes.isEmpty()) {
			minSpeed = routes.get(0).speed;
			for (Route r : routes) {
				minSpeed = Math.min(minSpeed, r.speed);
			}
			float acceptable = minSpeed * (100 + SPEED_TOLERANCE) / 100;
			List<Route> temp = routes;
			routes = new LinkedList<Route>();
			for (Route r : temp) {

				if (r.speed <= acceptable) {
					routes.add(r);
					System.out.println(r.speed);
				}
			}
		}
	}

	private void digRoute(Station from, Section origin, Section bySection, int hops, float speed) {
		for (Platform p : from.getPlatforms()) {
			if (p.getFrom() != bySection && p.getTo() != bySection && bySection != null) {
				speed += TRAIN_CHANGE;
			}
			if (p.getFrom() != bySection && p.getFrom() != null) {
				exploreThisSection(p, origin, p.getFrom(), hops, speed);
			}
			if (p.getTo() != bySection && p.getTo() != null) {
				exploreThisSection(p, origin, p.getTo(), hops, speed);
			}
		}
	}

	private void exploreThisSection(Platform from, Section origin, Section bySection, int hops, float speed) {
		Platform platNext = from.nextStationBy(bySection);
		Station stationNext = platNext.getOfStation();
		if (origin == null)
			origin = bySection;
		hops--;
		speed += bySection.weightedLength();
		if (stationNext.getType() == type) {
			routes.add(new Route(stationNext, origin, speed));
		} else {
			if (hops > 0) {
				digRoute(stationNext, origin, bySection, hops, speed);
			}
		}
	}

	public Shape getType() {
		return type;
	}

	public Boolean isArrivedInStation(Station s) {
		boolean arrived = false;
		if (s == dest) {
			arrived = true;
			if (dest.getType() == type) {
				on.scorePoint();
				delete();
				System.out.println("Traveler type " + type + " arrived at " + s.getType());
			} else
				s.haveWait(this);
		}
		return arrived;
	}

	public void delete() {
		on.deleteObserver(this);
	}

	public void update(Observable o, Object arg) {
		// Quand un changement de r�seau se produit, recalcul de l'itin�raire
		findRoute();
	}
}
