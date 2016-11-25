package game_engine;

import java.util.List;

public class Launcher {
	public static void main(String[] Args) {
		GameMap m = new GameMap();
		Clock cl = new Clock();
		List<Station> l = m.getStations();
		for (Station s : l) {
			m.getLane(0).extendTail(s);
		}
		new Train(m.getLane(0), cl);
		Station cir = m.getLane(0).getFirst();
		for (int i = 0; i < 10; i++) {
			new Traveler(Shape.Square, cir, m);
			new Traveler(Shape.Triangle, cir, m);
			new Traveler(Shape.Circle, cir, m);
		}
		// new Traveler(Shape.Square, cir, m);
		// new Train(m.getLane(0), cl);
		// new Train(m.getLane(0),cir , true, cl);
	}
}
