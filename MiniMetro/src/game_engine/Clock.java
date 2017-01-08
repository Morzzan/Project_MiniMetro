package game_engine;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public class Clock extends Observable implements Runnable {
	private int count = 0;
	private boolean paused=false;
	private int time=50;
		public void run() {
			for(;;){
				if(paused==false){
					count++;
					setChanged();
					notifyObservers(count);
				}
				try {
					Thread.sleep(time);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	public void stop(){
		paused=true;
	}
	public void play(){
		paused=false;
		time=50;
	}
	public void rush(){
		paused=false;
		time=25;
	}
}
