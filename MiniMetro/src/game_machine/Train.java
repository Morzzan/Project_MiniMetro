package game_machine;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Train implements Observer{
	private Lane on;
	private boolean way; 
	private double distance;
	private Station to;
	private int velocity;
	private List<Traveler> passengers=new LinkedList<Traveler>();
	private int capacity=6;
	@Override
	public void update(Observable arg0, Object arg1) {
		distance=distance-velocity;
		if(distance<=0){
			System.out.println(to.getType());
			inStation();
		}
	}
	public Lane onLane() {
		return on;
	}
	public boolean onWay() {
		return way;
	}
	public Train(Lane onLane,Station inStation,boolean onWay,Clock c){
		on=onLane;
		to=inStation;
		distance=0;
		velocity=2;
		way=onWay;
		c.addObserver(this);
	}
	private void inStation() {
		arrive();
		to.trainArrival(this);
		toTheNext();
		
	}
	private void arrive() {
		List<Traveler> temp=passengers;
		passengers=new LinkedList<Traveler>();
		for(Traveler t : temp){
			if(!t.isArrivedInStation(to)){
				passengers.add(t);
			}
		}
	}
	private void toTheNext() {
		Station next= on.nextStation(to, way);
		if(next==null){
			way=way ? false : true ;
			next=on.nextStation(to, way);
		}
		distance=next.getDistance(to);
		to=next;
	}
	public double getDistance() {
		return distance;
	}
	public boolean isFull(){
		return (passengers.size()>=capacity);
	}
	public void load(Traveler t){
		passengers.add(t);
	}
}
