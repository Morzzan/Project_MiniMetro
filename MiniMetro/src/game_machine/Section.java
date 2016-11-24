package game_machine;

public class Section {
	private Platform from, to;
	private double length;
	private Lane ofLane;
	
	public double getLength() {
		return length;
	}
	public Platform getFrom() {
		return from;
	}
	public Platform getTo() {
		return to;
	}
	public Section(Platform from, Platform to){
		this.from=from;
		this.to=to;
		length=from.getOfStation().getDistance(to.getOfStation());
	}
	public double weightedLength(){
		return length/ofLane.quality();
	}
}
