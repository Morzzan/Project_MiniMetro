package game_machine;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Lane{
	private GameMap on;
	private List<Train> trains = new ArrayList<Train>();
	private Station first, last;
	private int stationNumber=0;
	
	public float quality(){
		return trains.size()/stationNumber;
	}
	private void extend(Station from, Station to){
		Platform platFrom=from.getPlatform(this) , platTo=to.getPlatform(this);
		if(platFrom==null)platFrom=from.createPlatform(this);
		if(platTo==null)platTo=to.createPlatform(this);
		Section s=new Section(platFrom, platTo);
		platFrom.setTo(s);
		platTo.setFrom(s);
		stationNumber++;
	}
	public Station getFirst() {
		return first;
	}
	public GameMap getOn() {
		return on;
	}
	public void extendHead(Station s){
		extend(s,first);
		first=s;
	}
	public void extendTail(Station s){
		extend(last,s);
		last=s;
	}
	
	public Lane(GameMap on){
		
	}
	public List<Train> getTrains() {
		return trains;
	}
}
