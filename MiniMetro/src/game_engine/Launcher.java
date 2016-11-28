package game_engine;

import java.util.List;

public class Launcher {
	public static void main(String[] Args) {
		Clock cl = new Clock();
		GameMap m = new GameMap();
		List<Station> l = m.getStations();
		m.getLane(0).extendTail(l.get(0));
		m.getLane(0).extendTail(l.get(2));
		// m.getLane(0).extendTail(l.get(1));
		// m.getLane(1).extendTail(l.get(0));
		m.getLane(1).extendTail(l.get(2));
		m.getLane(1).extendTail(l.get(1));
		Station cir = m.getLane(0).getFirst();
		// for (int i = 0; i < 10; i++) {
		// new Traveler(Shape.Square, cir, m);
		// new Traveler(Shape.Circle, cir, m);
		// }
		new Traveler(Shape.Square, cir, m);
		// new Train(m.getLane(0), cl);
		// new Train(m.getLane(0),cir , true, cl);
		new Train(m.getLane(0), cl);
		new Train(m.getLane(1), cl);
	}
}
