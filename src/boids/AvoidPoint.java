package boids;

import java.util.ArrayList;

public class AvoidPoint extends Behaviour {
	
	private Vec2 point;
	private boolean active;

	public AvoidPoint(int radius, double angle, double weight) {
		super(radius, angle, weight);
		point = new Vec2();
		active = false;
	}

	@Override
	public Vec2 getForce(ArrayList<Boid> nearbyBoids, Boid current) {
		if (!active || null == current) return new Vec2();
		
		Vec2 avoid = current.pos().minus(point);
		double lenSq = avoid.magnitude();
		if (lenSq > radius()) avoid = new Vec2();
		else avoid = avoid.scale(radius() / (lenSq * lenSq));
		//else avoid = avoid.scale(1.0/ (lenSq * lenSq));
		
		return avoid;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public boolean isActive() { return active; }
	
	public void updatePoint(double x, double y) {
		point = new Vec2(x, y);
	}
	
	public Vec2 point() { return point; }

}
