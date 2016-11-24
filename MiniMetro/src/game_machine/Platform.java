package game_machine;


public class Platform {
	private Station ofStation;
	private Section from, to;
	private Lane ofLane;
	
	public Platform(Station ofStation, Lane ofLane){
		this.ofLane=ofLane;
		this.ofStation=ofStation;
	}
	public Section nextSection(Section before){
		Section after=null;
		if(before==from){
			if(to!=null)after=to;
			else after=from;
		}
		else{
			if(from!=null)after=from;
			else after=to;
		}
		return after;
	}
	public Platform nextStationBy(Section by){
		Platform s=by.getTo();
		if(s==this)s=by.getFrom();
		return s;
	}
	public Lane getOfLane() {
		return ofLane;
	}
	public Section getFrom() {
		return from;
	}
	public void setFrom(Section from) {
		this.from = from;
	}
	public Section getTo() {
		return to;
	}
	public Station getOfStation() {
		return ofStation;
	}
	public void setTo(Section to) {
		this.to = to;
	}
	

}
