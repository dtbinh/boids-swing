package boids;

import java.util.ArrayList;

public class Jitter extends Behaviour {
	
	private double xlim, ylim;

	public Jitter(int radius, double angle, double weight, double xlim, double ylim) {
		super(radius, angle, weight);
		this.xlim = xlim;
		this.ylim = ylim;
	}

	@Override
	public Vec2 getForce(ArrayList<Boid> nearbyBoids, Boid current) {		
		return new Vec2(Math.random() * xlim - xlim / 2, Math.random() * ylim - ylim / 2);
	}
}
