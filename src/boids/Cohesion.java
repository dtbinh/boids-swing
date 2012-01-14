package boids;

import java.util.ArrayList;

public class Cohesion extends Behaviour {
	
	public Cohesion(int radius, double angle, double weight) {
		super(radius, angle, weight);
	}

	@Override
	public Vec2 getForce(ArrayList<Boid> nearbyBoids, Boid current) {
		if (null == nearbyBoids || null == current) return new Vec2();
		
		Vec2 sum = new Vec2();
		for (Boid boid : nearbyBoids)
			sum = sum.plus(boid.pos().minus(current.pos())); //TODO immutability is probably slower so do it in place?
		
		if (0 != nearbyBoids.size()) sum = sum.scale(1.0 / nearbyBoids.size());
		
		return sum;
	}

}
