package game_machine;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Train implements Observer{
	private double distance;
	private Platform to;
	private Section on;
	private int velocity;
	private List<Traveler> passengers=new LinkedList<Traveler>();
	private int capacity=6;
	@Override
	public void update(Observable arg0, Object arg1) {
		distance=distance-velocity;
		if(distance<=0){
			System.out.println(to.getOfStation().getType());
			arriveInStation();
		}
	}
	public Train(Lane onLane,Clock c){
		onLane.getTrains().add(this);
		to=onLane.getFirst().getPlatform(onLane);
		distance=0;
		velocity=2;
		c.addObserver(this);
		to.getOfLane().getOn().networkChange();
	}
	private void arriveInStation() {
		unloadTravelers();
		to.getOfStation().trainArrival(this);
		leaveStation();
		
	}
	private void unloadTravelers() {
		List<Traveler> temp=passengers;
		passengers=new LinkedList<Traveler>();
		for(Traveler t : temp){
			if(!t.isArrivedInStation(to.getOfStation())){
				passengers.add(t);
			}
		}
	}
	private void leaveStation() {
		on=to.nextSection(on);
		to=to.nextStationBy(on);
		distance=on.getLength();
	}
	public boolean isFull(){
		return (passengers.size()>=capacity);
	}
	public void load(Traveler t){
		passengers.add(t);
	}
}
