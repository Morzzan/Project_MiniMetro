package game_engine;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public class Clock extends Observable {
	private Timer timer;
	private int count=0;
	
	public Clock(){
		timer=new Timer();
		TimerTask task1 = new TimerTask() {
			
			@Override
			public void run() {
				count++;
				setChanged();
				notifyObservers();
				System.out.println("tick");
			}
		};
		timer.schedule(task1, 0, 500);
	}
}
