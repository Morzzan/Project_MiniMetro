package game_machine;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import math.geom2d.Vector2D;

public class GameMap {
	private List<Station> stations = new LinkedList<Station>();
	private List<Lane> lanes = new ArrayList<Lane>();
	private int points=0;
	
	
	public GameMap(){
		for (int i =0;i<3;i++){
			lanes.add(new Lane());
		}
		stations.add(new Station(Shape.Triangle, new Vector2D(0, 0)));
		stations.add(new Station(Shape.Square, new Vector2D(0, 10)));
		stations.add(new Station(Shape.Circle, new Vector2D(10,0)));
	}
	public void scorePoint(){
		points++;
	}

	public List<Station> getStations() {
		return stations;
	}
	public int getScore(){
		return points;
	}

	public void setStations(List<Station> stations) {
		this.stations = stations;
	}


	public List<Lane> getLanes() {
		return lanes;
	}
	
	public Lane getLane(int index){
		return lanes.get(index);
	}


	public void setLanes(List<Lane> lanes) {
		this.lanes = lanes;
	}
}
