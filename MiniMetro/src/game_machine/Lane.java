package game_machine;
import java.util.ArrayList;
import java.util.List;

public class Lane{
	private List<Train> trains = new ArrayList<Train>();
	private List<Station> stations = new ArrayList<Station>();
	private boolean circular=true;

	public List<Train> getTrains() {
		return trains;
	}
	public void setTrains(List<Train> trains) {
		this.trains = trains;
	}
	public List<Station> getStations() {
		return stations;
	}
	public void setStations(List<Station> stations) {
		this.stations = stations;
	}
	public void addStation(Station toAdd,int index){
		stations.add(index, toAdd);
	}
	public void addStation(Station toAdd){
		addStation(toAdd, 0);
	}
	public Station nextStation(Station from,boolean way){
		Station next=null;
		int w=way ? 1 :-1;
		int index =stations.lastIndexOf(from);
		if(index!=-1){
			index+=w;
			int fin=stations.size()-1;
			if(index>=0&&index<=fin){
				next=stations.get(index);
			}
			else if(circular){
				if(index>fin)index=0;
				else index=fin;
				next=stations.get(index);
			}
		}		
		return next;
	}
}
