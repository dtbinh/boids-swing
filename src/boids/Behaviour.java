package boids;

import java.util.ArrayList;

public abstract class Behaviour { //TODO consider an interface
	
	private int radius;
	private double angle, weight;
	
	public Behaviour(int radius, double angle, double weight) {
		if (0.0 > angle) angle = 0.0;
		else if (Math.PI < angle) angle = Math.PI;
		
		this.radius = radius;
		this.angle = angle;
		this.weight = weight;
	}
	
	private boolean isNearby(Boid a, Boid b) {
		//TODO replace with a 2-KD tree
		if ((a.pos().minus(b.pos()).magnitude() < radius) && (b.vel().absAngleBetween(a.pos().minus(b.pos())) < angle))
			return true;
		
		return false;
	}
	
	public ArrayList<Boid> getNearbyBoids(ArrayList<Boid> boids, Boid current) {
		//TODO replace with a 2-KD tree
		ArrayList<Boid> nearbyBoids = new ArrayList<Boid>();
		
		if (null != boids && null != current)
			for (Boid boid : boids)
				if (boid != current && isNearby(boid, current))
					nearbyBoids.add(boid);
		
		return nearbyBoids;
	}
	
	public abstract Vec2 getForce(ArrayList<Boid> nearbyBoids, Boid current);
	
	public void apply(ArrayList<Boid> boids, Boid boid) {
		Vec2 force = getForce(getNearbyBoids(boids, boid), boid);
		boid.applyForce(force.scale(weight));
	}
}
