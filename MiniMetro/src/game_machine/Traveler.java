package game_machine;
import java.util.LinkedList;
import java.util.List;

public class Traveler{
	private GameMap on;
	private Shape type;
	private Station dest;
	private static final int TRAIN_CHANGE = 40, MAX_HOPS = 20, SPEED_TOLERANCE = 10;
	private List<Route> routes= new LinkedList<Route>();
	
	private class Route{
		private Station dest;
		private Lane byLane;
		private boolean byWay;
		private float speed;
		
		Route(Station dest, Lane byLane, boolean byWay, float speed){
			this.dest=dest;
			this.byLane=byLane;
			this.byWay=byWay;
			this.speed=speed;
		}
	}
	public Traveler(Shape type, Station in, GameMap jeu){
		on=jeu;
		this.type=type;
		in.haveWait(this);
	}
	
	public boolean GetOnTo(Train t){
		dest=null;
		boolean gone=false;
		for(Route r : routes){
			if((t.onWay()==r.byWay)&&(t.onLane()==r.byLane)){
				dest=r.dest;
				gone=true;
			}
		}
		if(dest!=null){
			t.load(this);
			routes.clear();
		}
		return gone ;
	}
	
	public void findRoute(Station from){
		routes.clear();
		digRoute(from,MAX_HOPS, 0, null, null, null, null, null);
		float minSpeed=routes.get(0).speed;
		for(Route r : routes){
			minSpeed=Math.min(minSpeed, r.speed);
		}
		float acceptable=minSpeed*(100+SPEED_TOLERANCE)/100;
		List<Route> temp=routes;
		routes=new LinkedList<Route>();
		for(Route r : temp){
			if(r.speed<=acceptable){
				routes.add(r);
			}
		}
	}
	
	private void digRoute(Station from, int hops, float speed, Lane byLane,Lane lastLane,Station firstDest,Boolean byWay,Boolean lastWay){
		if(hops>0){
			for(Lane l : on.getLanes()){
				if(byLane==null)byLane=l;
				for(int i = 0; i<2;i++){
					boolean j=(i!=0)?true:false;
					if(byWay==null)byWay=j;
					if((l!=lastLane )|| (lastWay == j)){
						Station next = l.nextStation(from, j);
						if(next!=null){
							if(l==byLane)firstDest=next;
							hops--;
							speed+=next.getDistance(from);
							if(l!= lastLane)speed+=TRAIN_CHANGE;
							lastWay=j;
							lastLane=l;
							if(next.getType()==type){
								routes.add(new Route(firstDest, byLane, byWay,speed));
							}
							else{
								digRoute(next, hops, speed, byLane, lastLane, firstDest, byWay, lastWay);
							}
						}
					}
				}
			}
		}
	}
	public Boolean isArrivedInStation(Station s){
		boolean arrived=false;
		if(s==dest){
			arrived=true;
			if(dest.getType()==type){
				on.scorePoint();
				System.out.println(type);
				System.out.println(s.getType());
				System.out.println(on.getScore());
			}
			else s.haveWait(this);
		}
		return arrived;
	}
}
