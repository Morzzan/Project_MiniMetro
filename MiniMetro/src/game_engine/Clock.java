package game_engine;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public class Clock extends Observable {
	private Timer timer;
	private Integer count = 1;

	public Clock() {
		timer = new Timer();
		TimerTask task1 = new TimerTask() {

			@Override
			public void run() {
				count++;
				setChanged();
				notifyObservers(count);
				System.out.println("Instant " + count);
			}
		};
		timer.schedule(task1, 0, 50);
	}
}
