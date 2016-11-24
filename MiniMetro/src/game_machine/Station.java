package game_machine;
import java.util.ArrayList;
import java.util.LinkedList;
import math.geom2d.*;
import java.util.List;
import java.util.Observable;
import java.util.Vector;

public class Station{
	private Shape type;
	private Vector2D pos;
	private List<Traveler> waiters = new LinkedList<Traveler>();
	private List<Platform> platforms = new ArrayList<Platform>();
	
	public Platform getPlatform(Lane l){
		Platform plat=null;
		for(Platform p : platforms){
			if(p.getOfLane()==l)plat=p;
		}
		return plat;
	}
	public Station(Shape type,Vector2D pos){
		this.type=type;
		this.pos=pos;
	}
	public Shape getType() {
		return type;
	}
	public void setType(Shape type) {
		this.type = type;
	}
	public Vector2D getPos() {
		return pos;
	}
	public List<Traveler> getWaiters() {
		return waiters;
	}
	public void setPos(Vector2D pos) {
		this.pos = pos;
	}
	public double getDistance(Station s2){
		Vector2D section=pos.minus(s2.pos);
		return section.norm();
	}
	public void trainArrival(Train train){
		List<Traveler> temp = waiters;
		waiters=new LinkedList<Traveler>();
		for(Traveler trav : temp){
			boolean loaded=false;
			if (!train.isFull()){
				loaded= trav.GetOnTo(train);
			}
			if(!loaded)waiters.add(trav);
		}
		System.out.println(waiters.size());
	}
	public void haveWait(Traveler t){
		waiters.add(t);
		t.setDest(this);
		t.findRoute();
	}
	public Platform createPlatform(Lane lane) {
		Platform p=new Platform(this,lane);	
		platforms.add(p);
		return p;
	}
}
