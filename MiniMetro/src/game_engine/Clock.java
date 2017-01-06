package game_engine;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public class Clock extends Observable {
	private Timer timer;
	private Integer count = 1;
	TimerTask task1 = new TimerTask() {

		@Override
		public void run() {
			count++;
			setChanged();
			notifyObservers(count);
			System.out.println("Instant " + count);
		}
	};

	public Clock() {
		timer = new Timer();
		play();
	}
	public void stop(){
		timer.cancel();
		timer.purge();
	}
	public void play(){
		timer=new Timer();
		timer.schedule(task1, 0,50);
	}
	public void rush(){
		timer.schedule(task1, 0,20);
	}
}
