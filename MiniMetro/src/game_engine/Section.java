package game_engine;

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

	public Section(Platform from, Platform to, Lane ofLane) {
		from.getOfLane().getOn().getSections().add(this);
		this.ofLane = ofLane;
		this.from = from;
		this.to = to;
		length = from.getOfStation().getDistance(to.getOfStation());
		from.setTo(this);
		;
		to.setFrom(this);
	}

	public double weightedLength(Platform next) {
		boolean onNormalWay = (next == to ? true : false);
		return length * ofLane.quality(onNormalWay);
	}
}
