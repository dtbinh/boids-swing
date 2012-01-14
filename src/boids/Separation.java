package boids;

import java.util.ArrayList;

public class Separation extends Behaviour {
	
	public Separation(int radius, double angle, double weight) {
		super(radius, angle, weight);
	}

	@Override
	public Vec2 getForce(ArrayList<Boid> nearbyBoids, Boid current) {
		if (null == nearbyBoids || null == current) return new Vec2();
		
		Vec2 sum = new Vec2();
		for (Boid boid : nearbyBoids) {
			Vec2 toBoid = current.pos().minus(boid.pos());
			double lenSq = toBoid.magnitude();
			lenSq *= lenSq;
			toBoid.scale(1.0 / lenSq);
			sum = sum.plus(toBoid);
		}
		//sum = sum.scale(1.0 / nearbyBoids.size());
		
		return sum;
	}

}
